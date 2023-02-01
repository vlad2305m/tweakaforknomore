package fi.dy.masa.tweakaforknomore.mixin.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.tweakeroo.config.Hotkeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static fi.dy.masa.tweakaforknomore.config.Hotkeys.*;

import java.util.List;

@Mixin(Hotkeys.class)
public class HotkeysMixin
{
    @Inject(method = "<init>", at = @At("RETURN"))
    private static void init(CallbackInfo ci){
        setHOTKEY_LIST(ImmutableList.of(
                Hotkeys.ACCURATE_BLOCK_PLACEMENT_IN,
                Hotkeys.ACCURATE_BLOCK_PLACEMENT_REVERSE,
                Hotkeys.BREAKING_RESTRICTION_MODE_COLUMN,
                Hotkeys.BREAKING_RESTRICTION_MODE_DIAGONAL,
                Hotkeys.BREAKING_RESTRICTION_MODE_FACE,
                Hotkeys.BREAKING_RESTRICTION_MODE_LAYER,
                Hotkeys.BREAKING_RESTRICTION_MODE_LINE,
                Hotkeys.BREAKING_RESTRICTION_MODE_PLANE,
                Hotkeys.COPY_SIGN_TEXT,
                Hotkeys.ELYTRA_CAMERA,
                Hotkeys.FLEXIBLE_BLOCK_PLACEMENT_ADJACENT,
                Hotkeys.FLEXIBLE_BLOCK_PLACEMENT_OFFSET,
                Hotkeys.FLEXIBLE_BLOCK_PLACEMENT_ROTATION,
                FLEXIBLE_BLOCK_PLACEMENT_HOLD,
                Hotkeys.FLY_PRESET_1,
                Hotkeys.FLY_PRESET_2,
                Hotkeys.FLY_PRESET_3,
                Hotkeys.FLY_PRESET_4,
                Hotkeys.FREE_CAMERA_PLAYER_INPUTS,
                Hotkeys.FREE_CAMERA_PLAYER_MOVEMENT,
                Hotkeys.HOTBAR_SCROLL,
                Hotkeys.HOTBAR_SWAP_BASE,
                Hotkeys.HOTBAR_SWAP_1,
                Hotkeys.HOTBAR_SWAP_2,
                Hotkeys.HOTBAR_SWAP_3,
                Hotkeys.INVENTORY_PREVIEW,
                Hotkeys.OPEN_CONFIG_GUI,
                Hotkeys.PLACEMENT_Y_MIRROR,
                Hotkeys.PLAYER_INVENTORY_PEEK,
                Hotkeys.PLACEMENT_RESTRICTION_MODE_COLUMN,
                Hotkeys.PLACEMENT_RESTRICTION_MODE_DIAGONAL,
                Hotkeys.PLACEMENT_RESTRICTION_MODE_FACE,
                Hotkeys.PLACEMENT_RESTRICTION_MODE_LAYER,
                Hotkeys.PLACEMENT_RESTRICTION_MODE_LINE,
                Hotkeys.PLACEMENT_RESTRICTION_MODE_PLANE,
                Hotkeys.SIT_DOWN_NEARBY_PETS,
                Hotkeys.SKIP_ALL_RENDERING,
                Hotkeys.SKIP_WORLD_RENDERING,
                Hotkeys.STAND_UP_NEARBY_PETS,
                Hotkeys.SWAP_ELYTRA_CHESTPLATE,
                Hotkeys.TOGGLE_CARPET_AP_PROTOCOL,
                Hotkeys.TOGGLE_GRAB_CURSOR,
                Hotkeys.TOOL_PICK,
                Hotkeys.ZOOM_ACTIVATE,
                AREA_SELECTION_OFFSET,
                AREA_SELECTION_ADD_TO_LIST,
                AREA_SELECTION_REMOVE_FROM_LIST,
                OPEN_ITEM_LIST,
                CONTAINER_SCANNER_CLEAR_CACHE,

                Hotkeys.WRITE_MAPS_AS_IMAGES,
                Hotkeys.ZOOM_ACTIVATE
        ));
    }

    @Accessor
    private static void setHOTKEY_LIST(List<ConfigHotkey> o){};

}
