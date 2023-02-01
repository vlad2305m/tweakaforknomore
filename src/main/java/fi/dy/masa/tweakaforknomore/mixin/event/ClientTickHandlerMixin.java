package fi.dy.masa.tweakaforknomore.mixin.event;

import net.minecraft.client.MinecraftClient;
import fi.dy.masa.tweakaforknomore.tweaks.RenderTweaks;
import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(fi.dy.masa.tweakeroo.event.ClientTickHandler.class)
public class ClientTickHandlerMixin
{
    @Inject(method = "Lfi/dy/masa/tweakeroo/event/ClientTickHandler;onClientTick(Lnet/minecraft/client/MinecraftClient;)V", remap = false,
            at=@At(value = "INVOKE_ASSIGN", target = "Lfi/dy/masa/tweakeroo/tweaks/MiscTweaks;onTick(Lnet/minecraft/client/MinecraftClient;)V", remap = false))
    public void onClientTick(MinecraftClient mc, CallbackInfo ci)
    {
        RenderTweaks.onTick();
    }
}
