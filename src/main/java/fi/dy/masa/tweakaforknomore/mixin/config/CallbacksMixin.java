package fi.dy.masa.tweakaforknomore.mixin.config;

import fi.dy.masa.tweakaforknomore.config.Configs;
import fi.dy.masa.tweakaforknomore.config.FeatureToggleI;
import fi.dy.masa.tweakaforknomore.config.Hotkeys;
import fi.dy.masa.tweakeroo.config.Callbacks;
import net.minecraft.client.MinecraftClient;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.util.InfoUtils;
import fi.dy.masa.tweakaforknomore.gui.GuiItemList;
import fi.dy.masa.tweakaforknomore.tweaks.PlacementTweaks;
import fi.dy.masa.tweakaforknomore.tweaks.RenderTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Callbacks.class)
public class CallbacksMixin
{
    @Inject(method = "Lfi/dy/masa/tweakeroo/config/Callbacks;init(Lnet/minecraft/client/MinecraftClient;)V", remap = false,
            at = @At(value = "INVOKE_ASSIGN", target = "Lfi/dy/masa/tweakeroo/config/Callbacks$KeyCallbackHotkeyWithMessage;<init>(Lnet/minecraft/client/MinecraftClient;)V", remap = false),
            locals = LocalCapture.PRINT)
    private static void init(MinecraftClient mc, CallbackInfo ci)
    {
        Hotkeys.FLEXIBLE_BLOCK_PLACEMENT_HOLD.getKeybind().setCallback(null); // TODO callbackGeneric
        Hotkeys.AREA_SELECTION_ADD_TO_LIST.getKeybind().setCallback(null);
        Hotkeys.AREA_SELECTION_REMOVE_FROM_LIST.getKeybind().setCallback(null);
        Hotkeys.OPEN_ITEM_LIST.getKeybind().setCallback(null);
        Hotkeys.CONTAINER_SCANNER_CLEAR_CACHE.getKeybind().setCallback(null);

        Configs.Lists.SELECTIVE_BLOCKS_BLACKLIST.setValueChangeCallback((cfg) -> RenderTweaks.rebuildLists());
        Configs.Lists.SELECTIVE_BLOCKS_WHITELIST.setValueChangeCallback((cfg) -> RenderTweaks.rebuildLists());
        Configs.Lists.SELECTIVE_BLOCKS_LIST_TYPE.setValueChangeCallback((cfg) -> RenderTweaks.rebuildLists());
        FeatureToggleI.TWEAK_SELECTIVE_BLOCKS_RENDERING().setValueChangeCallback((cfg) -> RenderTweaks.rebuildLists());
        FeatureToggleI.TWEAK_CONTAINER_SCAN().setValueChangeCallback((cfg) -> RenderTweaks.containerScanTweakUpdate());
    }

    @Mixin(targets = "fi/dy/masa/tweakeroo/config/Callbacks$KeyCallbackHotkeysGeneric", remap = false)
    public static class KeyCallbackHotkeysGenericMixin{
        @Inject(method = "Lfi/dy/masa/tweakeroo/config/Callbacks$KeyCallbackHotkeysGeneric;onKeyAction(Lfi/dy/masa/malilib/hotkeys/KeyAction;Lfi/dy/masa/malilib/hotkeys/IKeybind;)Z", remap = false,
                at = @At("HEAD"), cancellable = true)
        public void onKeyAction(KeyAction action, IKeybind key, CallbackInfoReturnable<Boolean> cir) {
            if (key == Hotkeys.CONTAINER_SCANNER_CLEAR_CACHE.getKeybind()) {
                RenderTweaks.clearContainerScanCache();

                InfoUtils.printActionbarMessage("tweakaforknomore.message.cache_cleared");
                cir.setReturnValue(true);
            } else if (key == Hotkeys.OPEN_ITEM_LIST.getKeybind()) {
                GuiBase.openGui(GuiItemList.INSTANCE);
                cir.setReturnValue(true);
            } else if (key == Hotkeys.AREA_SELECTION_ADD_TO_LIST.getKeybind()) {
                RenderTweaks.addSelectionToList();
                cir.setReturnValue(true);
            } else if (key == Hotkeys.AREA_SELECTION_REMOVE_FROM_LIST.getKeybind()) {
                RenderTweaks.removeSelectionFromList();
                cir.setReturnValue(true);
            } else if (key == Hotkeys.FLEXIBLE_BLOCK_PLACEMENT_HOLD.getKeybind()) {
                PlacementTweaks.holdSettings();
                cir.setReturnValue(true);
            }
        }
    }
}
