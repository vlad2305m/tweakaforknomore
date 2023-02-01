package fi.dy.masa.tweakaforknomore.mixin.tweaks;

import fi.dy.masa.malilib.util.BlockUtils;
import fi.dy.masa.malilib.util.GuiUtils;
import fi.dy.masa.malilib.util.InfoUtils;
import fi.dy.masa.malilib.util.PositionUtils;
import fi.dy.masa.malilib.util.PositionUtils.HitPart;
import fi.dy.masa.tweakaforknomore.config.Configs;
import static fi.dy.masa.tweakaforknomore.config.Hotkeys.FLEXIBLE_BLOCK_PLACEMENT_HOLD;

import fi.dy.masa.tweakeroo.config.FeatureToggle;
import fi.dy.masa.tweakeroo.config.Hotkeys;
import fi.dy.masa.tweakaforknomore.config.FeatureToggleI;
import fi.dy.masa.tweakeroo.tweaks.PlacementTweaks;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static fi.dy.masa.tweakaforknomore.tweaks.PlacementTweaks.*;

@Mixin(PlacementTweaks.class)
public class PlacementTweaksMixin
{
    @Shadow private static HitPart hitPartFirst = null;
    @Shadow private static Hand handFirst = Hand.MAIN_HAND;

    private static final Class<? extends Block>[] ACCURATE_AFTERCLICKER_BLOCKS = (Class<? extends Block>[]) new Class[] {
            RepeaterBlock.class, ComparatorBlock.class
    };
    
    private static final int ACCURATE_RADIX = 16 * 2; // Multiplied by 2

    @Inject(method = "Lfi/dy/masa/tweakeroo/tweaks/PlacementTweaks;onTick(Lnet/minecraft/client/MinecraftClient;)V", remap = false, at = @At("HEAD"))
    private static void onTick(MinecraftClient mc, CallbackInfo ci) {
        if (!Hotkeys.FLEXIBLE_BLOCK_PLACEMENT_ROTATION.getKeybind().isKeybindHeld() &&
                !Hotkeys.FLEXIBLE_BLOCK_PLACEMENT_OFFSET.getKeybind().isKeybindHeld() &&
                !Hotkeys.FLEXIBLE_BLOCK_PLACEMENT_ADJACENT.getKeybind().isKeybindHeld() &&
                !FLEXIBLE_BLOCK_PLACEMENT_HOLD.getKeybind().isKeybindHeld()) {
            tempDirection = null;
            offsetPos = null;
        }
    }

    @Redirect(method = "Lfi/dy/masa/tweakeroo/tweaks/PlacementTweaks;onTick(Lnet/minecraft/client/MinecraftClient;)V", remap = false,
            at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/util/GuiUtils;getCurrentScreen()Lnet/minecraft/client/gui/screen/Screen;", remap = false))
    private static Screen onTick2() {
        if (FeatureToggleI.TWEAK_AREA_SELECTOR().getBooleanValue()) return new MessageScreen(null);
        return GuiUtils.getCurrentScreen();
    }

    private static boolean cancelOnUsingTick = false;
    @ModifyVariable(method = "Lfi/dy/masa/tweakeroo/tweaks/PlacementTweaks;onUsingTick()V", remap = false,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/hit/BlockHitResult;getSide()Lnet/minecraft/util/math/Direction;"))
    private static BlockHitResult onUsingTick(BlockHitResult bhr)
    {
        if (FeatureToggleI.TWEAK_SCAFFOLD_PLACE().getBooleanValue()) {
            MinecraftClient mc = MinecraftClient.getInstance();
            ClientPlayerEntity player = mc.player;
            Direction side = bhr.getSide();
            BlockPos pos = bhr.getBlockPos();
            side = getScaffoldPlaceDirection(side, hitPartFirst, player);
            ItemStack stack = player.getStackInHand(handFirst);
            pos = getScaffoldPlacePosition(pos, side, player.getEntityWorld(), stack, player);
            if (pos == null) {
                cancelOnUsingTick = true;
                return bhr;
            }
            pos = pos.offset(side.getOpposite());
            return new BlockHitResult(bhr.getPos(), side, pos, false);
        }
        return bhr;
    }
    @Inject(method = "Lfi/dy/masa/tweakeroo/tweaks/PlacementTweaks;onUsingTick()V", remap = false,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/hit/BlockHitResult;getBlockPos()Lnet/minecraft/util/math/BlockPos;"), cancellable = true)
    private static void onUsingTick(CallbackInfo ci)
    {
        if (cancelOnUsingTick) {
            cancelOnUsingTick = false;
            ci.cancel();
        }
    }

    @Inject(method = "Lfi/dy/masa/tweakeroo/tweaks/PlacementTweaks;onProcessRightClickBlock(Lnet/minecraft/client/network/ClientPlayerInteractionManager;Lnet/minecraft/client/network/ClientPlayerEntity;Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/util/Hand;Lnet/minecraft/util/hit/BlockHitResult;)Lnet/minecraft/util/ActionResult;", remap = false,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getYaw()F"), cancellable = true)
    private static void onProcessRightClickBlock(ClientPlayerInteractionManager controller, ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        if (!BLOCK_TYPE_RCLICK_RESTRICTION.isAllowed(world.getBlockState(hitResult.getBlockPos()).getBlock())) cir.setReturnValue(ActionResult.PASS);
    }

    private static boolean onProcessRightClickBlockModifyVariables = false;
    private static Direction onProcessRightClickBlockSideIn = null;
    private static Vec3d onProcessRightClickBlockHitVec = null;
    private static BlockPos onProcessRightClickBlockPodIn = null;
    @Inject(method = "Lfi/dy/masa/tweakeroo/tweaks/PlacementTweaks;onProcessRightClickBlock(Lnet/minecraft/client/network/ClientPlayerInteractionManager;Lnet/minecraft/client/network/ClientPlayerEntity;Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/util/Hand;Lnet/minecraft/util/hit/BlockHitResult;)Lnet/minecraft/util/ActionResult;", remap = false,
            at = @At(value = "INVOKE", target = "Lfi/dy/masa/tweakeroo/tweaks/PlacementTweaks;tryPlaceBlock(Lnet/minecraft/client/network/ClientPlayerInteractionManager;Lnet/minecraft/client/network/ClientPlayerEntity;Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;Lnet/minecraft/util/math/Direction;FLnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/Hand;Lfi/dy/masa/malilib/util/PositionUtils$HitPart;Z)Lnet/minecraft/util/ActionResult;", remap = false))
    private static void onProcessRightClickBlock2(
            ClientPlayerInteractionManager controller, ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {

        boolean flexible = FeatureToggle.TWEAK_FLEXIBLE_BLOCK_PLACEMENT.getBooleanValue();
        boolean rotation = Hotkeys.FLEXIBLE_BLOCK_PLACEMENT_ROTATION.getKeybind().isKeybindHeld();
        boolean offset = Hotkeys.FLEXIBLE_BLOCK_PLACEMENT_OFFSET.getKeybind().isKeybindHeld();
        boolean adjacent = Hotkeys.FLEXIBLE_BLOCK_PLACEMENT_ADJACENT.getKeybind().isKeybindHeld();
        boolean hold = FLEXIBLE_BLOCK_PLACEMENT_HOLD.getKeybind().isKeybindHeld();

        if (FeatureToggleI.TWEAK_SCAFFOLD_PLACE().getBooleanValue() && (!flexible || (!rotation && !offset && !adjacent && !hold))) {
            Direction sideIn = hitResult.getSide();
            BlockPos posIn = hitResult.getBlockPos();
            Vec3d hitVec = hitResult.getPos();
            Direction playerFacingH = player.getHorizontalFacing();
            PositionUtils.HitPart hitPart = PositionUtils.getHitPart(sideIn, playerFacingH, posIn, hitVec);
            ItemStack stack = player.getStackInHand(hand);
            Direction extendDirection = getScaffoldPlaceDirection(sideIn, hitPart, player);
            BlockPos newPos = getScaffoldPlacePosition(posIn, extendDirection, world, stack, player);
            if (newPos == null) {
                cir.setReturnValue(ActionResult.PASS);
            }

            newPos = newPos.offset(extendDirection.getOpposite());
            onProcessRightClickBlockSideIn = extendDirection;
            onProcessRightClickBlockHitVec = hitVec.subtract(posIn.getX(), posIn.getY(), posIn.getZ()).add(newPos.getX(),newPos.getY(),newPos.getZ());
            onProcessRightClickBlockPodIn = newPos;

        }
    }
    @ModifyVariable(method = "Lfi/dy/masa/tweakeroo/tweaks/PlacementTweaks;onProcessRightClickBlock(Lnet/minecraft/client/network/ClientPlayerInteractionManager;Lnet/minecraft/client/network/ClientPlayerEntity;Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/util/Hand;Lnet/minecraft/util/hit/BlockHitResult;)Lnet/minecraft/util/ActionResult;", remap = false,
            at = @At(value = "INVOKE", target = "Lfi/dy/masa/tweakeroo/tweaks/PlacementTweaks;tryPlaceBlock(Lnet/minecraft/client/network/ClientPlayerInteractionManager;Lnet/minecraft/client/network/ClientPlayerEntity;Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;Lnet/minecraft/util/math/Direction;FLnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/Hand;Lfi/dy/masa/malilib/util/PositionUtils$HitPart;Z)Lnet/minecraft/util/ActionResult;", remap = false), print = true)
    private static Direction onProcessRightClickBlock(Direction sideIn) {
        return onProcessRightClickBlockModifyVariables ? onProcessRightClickBlockSideIn : sideIn;
    }
    // .........


    @Shadow private static boolean firstWasRotation;
    @Shadow private static boolean firstWasOffset;
    @Shadow private static BlockPos getPlacementPositionForTargetedPosition(World world, BlockPos pos, Direction side, ItemPlacementContext useContext) {return null;}
    @Shadow private static ActionResult handleFlexibleBlockPlacement(ClientPlayerInteractionManager controller, ClientPlayerEntity player, ClientWorld world, BlockPos pos, Direction side, float playerYaw, Vec3d hitVec, Hand hand, @Nullable PositionUtils.HitPart hitPart) {return null;}
    @Shadow private static ActionResult processRightClickBlockWrapper(ClientPlayerInteractionManager controller, ClientPlayerEntity player, ClientWorld world, BlockPos posIn, Direction sideIn, Vec3d hitVecIn, Hand hand) {return  null;}
    @Shadow private static boolean canPlaceBlockIntoPosition(World world, BlockPos pos, ItemPlacementContext useContext) {return false;}
    @Shadow private static boolean isFacingValidFor(Direction facing, ItemStack stack) {return false;}
        /**
         * @author vlad2305m
         * @reason IDK how to modify ' ? : ' conditions
         */
    @Overwrite
    private static ActionResult tryPlaceBlock(
            ClientPlayerInteractionManager controller,
            ClientPlayerEntity player,
            ClientWorld world,
            BlockPos posIn,
            Direction sideIn,
            Direction sideRotatedIn,
            float playerYaw,
            Vec3d hitVec,
            Hand hand,
            HitPart hitPart,
            boolean isFirstClick)
    {
        Direction side = sideIn;
        boolean handleFlexible = false;
        BlockPos posNew = null;
        boolean flexible = FeatureToggle.TWEAK_FLEXIBLE_BLOCK_PLACEMENT.getBooleanValue();
        boolean rotationHeld = Hotkeys.FLEXIBLE_BLOCK_PLACEMENT_ROTATION.getKeybind().isKeybindHeld();
        boolean offsetHeld = Hotkeys.FLEXIBLE_BLOCK_PLACEMENT_OFFSET.getKeybind().isKeybindHeld();
        boolean adjacent = Hotkeys.FLEXIBLE_BLOCK_PLACEMENT_ADJACENT.getKeybind().isKeybindHeld();
        boolean rememberFlexible = fi.dy.masa.tweakeroo.config.Configs.Generic.REMEMBER_FLEXIBLE.getBooleanValue();
        boolean rotation = rotationHeld || (rememberFlexible && firstWasRotation);
        boolean offset = offsetHeld || (rememberFlexible && firstWasOffset);
        ItemStack stack = player.getStackInHand(hand);
       
    

        if (flexible)
        {
            BlockHitResult hitResult = new BlockHitResult(hitVec, sideIn, posIn, false);
            ItemPlacementContext ctx = new ItemPlacementContext(new ItemUsageContext(player, hand, hitResult));
            posNew = isFirstClick && (rotation || offset || adjacent || offsetHold != null || directionHold != null) ? getPlacementPositionForTargetedPosition(world, posIn, sideIn, ctx) : posIn;
            offsetPos = new BlockPos(0,0,0);

            if ((rotationHeld && (offsetHeld || adjacent)) && tempDirection == null) {
                tempDirection = sideRotatedIn;
                if (FeatureToggleI.STACK_FLEXIBLE().getBooleanValue()) {
                    InfoUtils.printActionbarMessage(tempDirection.asString() + " rotation set");
                    return ActionResult.PASS;
                }
            }

           
            // Place the block into the adjacent position
            if (adjacent && hitPart != null && hitPart != HitPart.CENTER)
            {
                offsetPos = offsetPos.offset(sideRotatedIn.getOpposite());
                handleFlexible = true;
            }

            // Place the block facing/against the adjacent block (= just rotated from normal)
            if (rotation && !offset && !adjacent)
            {
                side = sideRotatedIn;
                handleFlexible = true;
                tempDirection = sideRotatedIn;
            }
            else
            {
                if (tempDirection != null || directionHold != null) {
                    side = (tempDirection != null) ? tempDirection : directionHold;
                    handleFlexible = true;
                    rotation = true;
                    // System.out.println(side.asString());
                } else {
                    // Don't rotate the player facing in handleFlexibleBlockPlacement()
                    hitPart = null;
                }
            }

            // Place the block into the diagonal position
            if (offset)
            {
                offsetPos = offsetPos.offset(sideRotatedIn.getOpposite()).offset(sideIn);
                handleFlexible = true;
            }

            if (offsetPos.getX() != 0 || offsetPos.getY() != 0 || offsetPos.getZ() != 0) {
                posNew = posNew.add(offsetPos).offset(sideIn.getOpposite());
            } else if (offsetHold != null) {
                posNew = posNew.add(offsetHold).offset(sideIn.getOpposite());
                offsetPos = offsetHold;
                handleFlexible = true;
            } else {
                offsetPos = null;
            }
            
        }

        if (FLEXIBLE_BLOCK_PLACEMENT_HOLD.getKeybind().isKeybindHeld()) {
            if (handleFlexible) {
                InfoUtils.printActionbarMessage("Flexible set");   
            }

            return ActionResult.PASS;
        }

        boolean simpleOffset = false;

        if (handleFlexible == false &&
            FeatureToggle.TWEAK_FAKE_SNEAK_PLACEMENT.getBooleanValue() &&
            stack.getItem() instanceof BlockItem)
        {
            BlockHitResult hitResult = new BlockHitResult(hitVec, sideIn, posIn, false);
            ItemPlacementContext ctx = new ItemPlacementContext(new ItemUsageContext(player, hand, hitResult));
            posNew = getPlacementPositionForTargetedPosition(world, posIn, sideIn, ctx);
            simpleOffset = true;
        }

        boolean accurate = FeatureToggle.TWEAK_ACCURATE_BLOCK_PLACEMENT.getBooleanValue();
        boolean accurateIn = Hotkeys.ACCURATE_BLOCK_PLACEMENT_IN.getKeybind().isKeybindHeld();
        boolean accurateReverse = Hotkeys.ACCURATE_BLOCK_PLACEMENT_REVERSE.getKeybind().isKeybindHeld();
        //boolean afterClicker = FeatureToggle.TWEAK_AFTER_CLICKER.getBooleanValue();
        boolean shouldUseAccurateAfterClick = FeatureToggle.TWEAK_AFTER_CLICKER.getBooleanValue() && canUseCarpetProtocolForAfterclicker(stack);

       
        if (!accurateIn) accurateIn = intoHold;
        if (!accurateReverse) accurateReverse = reverseHold;
        if (accurate && (accurateIn || accurateReverse || shouldUseAccurateAfterClick))
        {
            Direction facing = side;
            boolean handleAccurate = false;

            if (posNew == null)
            {
                if (flexible == false || isFirstClick == false)
                {
                    posNew = posIn;
                }
                else
                {
                    BlockHitResult hitResult = new BlockHitResult(hitVec, side, posIn, false);
                    ItemPlacementContext ctx = new ItemPlacementContext(new ItemUsageContext(player, hand, hitResult));
                    posNew = getPlacementPositionForTargetedPosition(world, posIn, side, ctx);
                }
            }

            if (accurateIn)
            {
                facing = sideIn;
                hitPart = null;
                handleAccurate = true;

                // Pistons, Droppers, Dispensers should face into the block, but Observers should point their back/output
                // side into the block when the Accurate Placement In hotkey is used
                if ((stack.getItem() instanceof BlockItem) == false || ((BlockItem) stack.getItem()).getBlock() != Blocks.OBSERVER)
                {
                    facing = facing.getOpposite();
                }
                //System.out.printf("accurate - IN - facing: %s\n", facing);
            }
            else if (flexible == false || rotation == false)
            {
                if (stack.getItem() instanceof BlockItem)
                {

                    BlockHitResult hitResult = new BlockHitResult(hitVec, sideIn, posNew, false);
                    ItemPlacementContext ctx = new ItemPlacementContext(new ItemUsageContext(player, hand, hitResult));

                    BlockPos posPlacement = getPlacementPositionForTargetedPosition(world, posNew, sideIn, ctx);

                    hitResult = new BlockHitResult(hitVec, sideIn, posPlacement, false);
                    ctx = new ItemPlacementContext(new ItemUsageContext(player, hand, hitResult));

                    BlockItem item = (BlockItem) stack.getItem();
                    BlockState state = item.getBlock().getPlacementState(ctx);

                    // getStateForPlacement can return null in 1.13+
                    if (state == null)
                    {
                        return ActionResult.PASS;
                    }

                    Direction facingTmp = BlockUtils.getFirstPropertyFacingValue(state);
                    //System.out.printf("accurate - sideIn: %s, state: %s, hit: %s, f: %s, posNew: %s\n", sideIn, state, hitVec, EnumFacing.getDirectionFromEntityLiving(posIn, player), posNew);

                    if (facingTmp != null)
                    {
                        facing = facingTmp;
                    }
                }
                else
                {
                    facing = player.getHorizontalFacing();
                }
            }

            if (accurateReverse)
            {
                //System.out.printf("accurate - REVERSE - facingOrig: %s, facingNew: %s\n", facing, facing.getOpposite());
                if (accurateIn || flexible == false || rotation == false)
                {
                    facing = facing.getOpposite();
                }

                hitPart = null;
                handleAccurate = true;
            }

            if ((handleAccurate || shouldUseAccurateAfterClick) && fi.dy.masa.tweakeroo.config.Configs.Generic.CARPET_ACCURATE_PLACEMENT_PROTOCOL.getBooleanValue())
            {
                // Carpet-Extra mod accurate block placement protocol support
                double relX = hitVec.x - posNew.getX();
                double x = hitVec.x;
                int afterClickerClickCount = MathHelper.clamp(fi.dy.masa.tweakeroo.config.Configs.Generic.AFTER_CLICKER_CLICK_COUNT.getIntegerValue(), 0, 32);

                if (handleAccurate && isFacingValidFor(facing, stack))
                {
                    x = posNew.getX() + relX + 2 + (facing.getId() * 2);
                } else if (shouldUseAccurateAfterClick) {
                    x = posNew.getX() + relX + 2;
                }

                if (shouldUseAccurateAfterClick)
                {
                    x += afterClickerClickCount * ACCURATE_RADIX;
                }

                //System.out.printf("accurate - pre hitVec: %s\n", hitVec);
                //System.out.printf("processRightClickBlockWrapper facing: %s, x: %.3f, pos: %s, side: %s\n", facing, x, pos, side);
                hitVec = new Vec3d(x, hitVec.y, hitVec.z);
                //System.out.printf("accurate - post hitVec: %s\n", hitVec);
            }

            //System.out.printf("accurate - facing: %s, side: %s, posNew: %s, hit: %s\n", facing, side, posNew, hitVec);
            return processRightClickBlockWrapper(controller, player, world, posNew, side, hitVec, hand);
        }

        if (handleFlexible)
        {
            BlockHitResult hitResult = new BlockHitResult(hitVec, side, posNew, false);
            ItemPlacementContext ctx = new ItemPlacementContext(new ItemUsageContext(player, hand, hitResult));

            if (canPlaceBlockIntoPosition(world, posNew, ctx))
            {
                //System.out.printf("tryPlaceBlock() pos: %s, side: %s, part: %s, hitVec: %s\n", posNew, side, hitPart, hitVec);
                return handleFlexibleBlockPlacement(controller, player, world, posNew, side, playerYaw, hitVec, hand, hitPart);
            }
            else
            {
                return ActionResult.PASS;
            }
        }

        if (isFirstClick == false && fi.dy.masa.tweakeroo.config.Configs.Generic.FAST_PLACEMENT_REMEMBER_ALWAYS.getBooleanValue())
        {
            return handleFlexibleBlockPlacement(controller, player, world, posIn, sideIn, playerYaw, hitVec, hand, null);
        }

        return processRightClickBlockWrapper(controller, player, world, simpleOffset ? posNew : posIn, sideIn, hitVec, hand);
    }

    @Overwrite
    private static boolean isNewPositionValidForLayerMode(BlockPos posNew, BlockPos posFirst, Direction sideFirst)
    {
        int height = Configs.Generic.RESTRICTION_LAYER_HEIGHT.getIntegerValue();
        if (height > 0) {
            int diff = posNew.getY() - posFirst.getY() + 1;
        
            return diff > 0 && diff <= height;
        } else if (height < 0) {
            int diff = posFirst.getY() - posNew.getY() + 1;
        
            return diff > 0 && diff <= -height;
        }
        return true;
    }

}
