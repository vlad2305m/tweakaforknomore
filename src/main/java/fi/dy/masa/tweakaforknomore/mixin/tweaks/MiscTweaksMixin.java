package fi.dy.masa.tweakaforknomore.mixin.tweaks;

import fi.dy.masa.tweakaforknomore.tweaks.RenderTweaks;
import fi.dy.masa.tweakeroo.tweaks.MiscTweaks;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static fi.dy.masa.tweakaforknomore.tweaks.MiscTweaks.*;

@Mixin(MiscTweaks.class)
public class MiscTweaksMixin
{
    @Inject(method = "Lfi/dy/masa/tweakeroo/tweaks/MiscTweaks;onTick(Lnet/minecraft/client/MinecraftClient;)V", remap = false, at = @At("HEAD"))
    private static void onTick(MinecraftClient mc, CallbackInfo ci)
    {
        if (mc.player == null)
        {
            performedAfkAction = false;
            ticksSinceAfk = 0;
            return;
        }

        checkAfk(mc);
    }

    @Inject(method = "Lfi/dy/masa/tweakeroo/tweaks/MiscTweaks;onGameLoop(Lnet/minecraft/client/MinecraftClient;)V", remap = false,
            at = @At(value = "INVOKE", target = "Lfi/dy/masa/tweakeroo/tweaks/PlacementTweaks;onTick(Lnet/minecraft/client/MinecraftClient;)V", remap = false,
                    shift = At.Shift.AFTER))
    private static void onGameLoop(MinecraftClient mc, CallbackInfo ci)
    {
        RenderTweaks.onTick();
    }
}
