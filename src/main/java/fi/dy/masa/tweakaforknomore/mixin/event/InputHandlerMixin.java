package fi.dy.masa.tweakaforknomore.mixin.event;

import fi.dy.masa.malilib.util.KeyCodes;
import fi.dy.masa.tweakaforknomore.config.Configs;
import fi.dy.masa.tweakaforknomore.config.FeatureToggleI;
import fi.dy.masa.tweakeroo.event.InputHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.NoteBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InputHandler.class)
public class InputHandlerMixin
{
    private static final int[] NOTEMAP = new int[] { 3, 5, 6, 8, 10, 11, 1 };
    @Inject(method = "Lfi/dy/masa/tweakeroo/event/InputHandler;onKeyInput(IIIZ)Z", remap = false, at = @At(value = "TAIL"), cancellable = true)
    public void onKeyInput(int keyCode, int scanCode, int modifiers, boolean eventKeyState, CallbackInfoReturnable<Boolean> cir)
    {
        MinecraftClient mc = MinecraftClient.getInstance();

        if (eventKeyState && FeatureToggleI.TWEAK_NOTEBLOCK_EDIT().getBooleanValue()) {
            if (mc.world != null && mc.crosshairTarget != null && mc.crosshairTarget.getType() == HitResult.Type.BLOCK) {
                BlockHitResult hit = (BlockHitResult)mc.crosshairTarget;
                BlockState state = mc.world.getBlockState(hit.getBlockPos());
                if (state.getBlock() instanceof NoteBlock) {
                    int currentNote = state.get(NoteBlock.NOTE);
                    int maxNote = 25;
                    int offset = 0;
                    if (keyCode >= KeyCodes.KEY_0 && keyCode <= KeyCodes.KEY_9)
                    {
                        offset = MathHelper.clamp(keyCode - KeyCodes.KEY_0, 0, 9);
                        if (offset == 0) {
                            offset = (maxNote - currentNote) % maxNote;
                        } else
                        if (offset == 1) {
                            offset = 10;
                        }
                    } else if (keyCode == KeyCodes.KEY_MINUS) {
                        offset = maxNote - 1;
                    } else if (keyCode == KeyCodes.KEY_EQUAL) {
                        offset = 1;
                    } else if (keyCode == KeyCodes.KEY_TAB) {
                        offset = 12;
                        if (offset + currentNote >= 25) {
                            offset += 1;
                        }
                    } else if (Configs.Generic.NOTE_EDIT_LETTERS.getBooleanValue() && keyCode >= KeyCodes.KEY_A && keyCode <= KeyCodes.KEY_G) {
                        int target = NOTEMAP[MathHelper.clamp(keyCode - KeyCodes.KEY_A, 0, 6)];
                        
                        if (target >= currentNote) {
                            offset = target - currentNote;
                        } else {
                            offset = target + (maxNote - currentNote);
                        }
                    } else {
                        cir.setReturnValue(false);
                    }

                    for (int i = 0; i < offset; i++)
                    {
                        BlockHitResult context = new BlockHitResult(new Vec3d(hit.getBlockPos().getX(), hit.getBlockPos().getY(), hit.getBlockPos().getZ()),Direction.NORTH, hit.getBlockPos(), false);
                        mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, context);
                    }
                    cir.setReturnValue(true);
                }
            }
        }

    }

}
