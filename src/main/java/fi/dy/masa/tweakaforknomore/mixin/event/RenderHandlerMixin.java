package fi.dy.masa.tweakaforknomore.mixin.event;

import fi.dy.masa.tweakaforknomore.tweaks.RenderTweaks;
import fi.dy.masa.tweakeroo.event.RenderHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderHandler.class)
public class RenderHandlerMixin
{
    @Inject(method = "Lfi/dy/masa/tweakeroo/event/RenderHandler;onRenderWorldLast(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;)V", remap = false, at = @At("HEAD"))
    public void onRenderWorldLast(MatrixStack matrixStack, Matrix4f projMatrix, CallbackInfo ci)
    {
        if (MinecraftClient.getInstance().player != null)
        {
            RenderTweaks.render(matrixStack);
        }
    }

}
