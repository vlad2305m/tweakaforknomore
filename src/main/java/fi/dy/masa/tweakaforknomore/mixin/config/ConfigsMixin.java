package fi.dy.masa.tweakaforknomore.mixin.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.util.restrictions.UsageRestriction.ListType;
import fi.dy.masa.tweakaforknomore.tweaks.PlacementTweaks;
import fi.dy.masa.tweakaforknomore.tweaks.RenderTweaks;
import fi.dy.masa.tweakeroo.config.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static fi.dy.masa.tweakaforknomore.config.Configs.*;
@Mixin(Configs.class)
public class ConfigsMixin
{

    @Mixin(Configs.Generic.class)
    public static class GenericMixin
    {
        @Inject(method = "<init>", at = @At("RETURN"))
        private static void init(CallbackInfo ci){
            setOPTIONS(ImmutableList.of(
                    Configs.Generic.CARPET_ACCURATE_PLACEMENT_PROTOCOL,
                    Configs.Generic.CLIENT_PLACEMENT_ROTATION,
                    Configs.Generic.FAST_LEFT_CLICK_ALLOW_TOOLS,
                    Configs.Generic.FAST_PLACEMENT_REMEMBER_ALWAYS,
                    Configs.Generic.FREE_CAMERA_PLAYER_INPUTS,
                    Configs.Generic.FREE_CAMERA_PLAYER_MOVEMENT,
                    Configs.Generic.HAND_RESTOCK_PRE,
                    Configs.Generic.HANGABLE_ENTITY_BYPASS_INVERSE,
                    Configs.Generic.ITEM_USE_PACKET_CHECK_BYPASS,
                    Configs.Generic.PERMANENT_SNEAK_ALLOW_IN_GUIS,
                    Configs.Generic.PLACEMENT_RESTRICTION_TIED_TO_FAST,
                    Configs.Generic.POTION_WARNING_BENEFICIAL_ONLY,
                    Configs.Generic.REMEMBER_FLEXIBLE,
                    Configs.Generic.SHULKER_DISPLAY_BACKGROUND_COLOR,
                    Configs.Generic.SHULKER_DISPLAY_REQUIRE_SHIFT,
                    Configs.Generic.SLOT_SYNC_WORKAROUND,
                    Configs.Generic.SLOT_SYNC_WORKAROUND_ALWAYS,
                    Configs.Generic.SNAP_AIM_INDICATOR,
                    Configs.Generic.SNAP_AIM_ONLY_CLOSE_TO_ANGLE,
                    Configs.Generic.SNAP_AIM_PITCH_OVERSHOOT,
                    Configs.Generic.ZOOM_ADJUST_MOUSE_SENSITIVITY,

                    Generic.AREA_SELECTION_USE_ALL,
                    Generic.RESTRICTION_LAYER_HEIGHT,
                    Configs.Generic.BLOCK_TYPE_BREAK_RESTRICTION_WARN,
                    Configs.Generic.BREAKING_RESTRICTION_MODE,
                    Configs.Generic.ELYTRA_CAMERA_INDICATOR,
                    Configs.Generic.ENTITY_TYPE_ATTACK_RESTRICTION_WARN,
                    Configs.Generic.PLACEMENT_RESTRICTION_MODE,
                    Configs.Generic.HOTBAR_SWAP_OVERLAY_ALIGNMENT,
                    Generic.SELECTIVE_BLOCKS_TRACK_PISTONS,
                    Generic.SELECTIVE_BLOCKS_HIDE_PARTICLES,
                    Generic.SELECTIVE_BLOCKS_HIDE_ENTITIES,
                    Generic.SELECTIVE_BLOCKS_NO_HIT,
                    Configs.Generic.SNAP_AIM_MODE,

                    Configs.Generic.CHAT_TIME_FORMAT,
                    Configs.Generic.CHAT_BACKGROUND_COLOR,
                    Configs.Generic.FLEXIBLE_PLACEMENT_OVERLAY_COLOR,
                    Configs.Generic.SNAP_AIM_INDICATOR_COLOR,

                    Configs.Generic.AFTER_CLICKER_CLICK_COUNT,
                    Configs.Generic.BLOCK_REACH_DISTANCE,
                    Configs.Generic.BREAKING_GRID_SIZE,
                    Configs.Generic.FAST_BLOCK_PLACEMENT_COUNT,
                    Configs.Generic.FAST_LEFT_CLICK_COUNT,
                    Configs.Generic.FAST_RIGHT_CLICK_COUNT,
                    Configs.Generic.FILL_CLONE_LIMIT,
                    Configs.Generic.FLY_DECELERATION_FACTOR,
                    Configs.Generic.FLY_SPEED_PRESET_1,
                    Configs.Generic.FLY_SPEED_PRESET_2,
                    Configs.Generic.FLY_SPEED_PRESET_3,
                    Configs.Generic.FLY_SPEED_PRESET_4,
                    Configs.Generic.GAMMA_OVERRIDE_VALUE,
                    Configs.Generic.HAND_RESTOCK_PRE_THRESHOLD,
                    Configs.Generic.HOTBAR_SLOT_CYCLE_MAX,
                    Configs.Generic.HOTBAR_SLOT_RANDOMIZER_MAX,
                    Configs.Generic.HOTBAR_SWAP_OVERLAY_OFFSET_X,
                    Configs.Generic.HOTBAR_SWAP_OVERLAY_OFFSET_Y,
                    Configs.Generic.ITEM_SWAP_DURABILITY_THRESHOLD,
                    Configs.Generic.MAP_PREVIEW_SIZE,
                    Configs.Generic.PERIODIC_ATTACK_INTERVAL,
                    Configs.Generic.PERIODIC_USE_INTERVAL,
                    Configs.Generic.PERIODIC_HOLD_ATTACK_DURATION,
                    Configs.Generic.PERIODIC_HOLD_ATTACK_INTERVAL,
                    Configs.Generic.PERIODIC_HOLD_USE_DURATION,
                    Configs.Generic.PERIODIC_HOLD_USE_INTERVAL,
                    Configs.Generic.PLACEMENT_GRID_SIZE,
                    Configs.Generic.PLACEMENT_LIMIT,
                    Configs.Generic.POTION_WARNING_THRESHOLD,
                    Configs.Generic.RENDER_LIMIT_ITEM,
                    Configs.Generic.RENDER_LIMIT_XP_ORB,
                    Configs.Generic.SCULK_SENSOR_PULSE_LENGTH,
                    Configs.Generic.SNAP_AIM_PITCH_STEP,
                    Configs.Generic.SNAP_AIM_THRESHOLD_PITCH,
                    Configs.Generic.SNAP_AIM_THRESHOLD_YAW,
                    Configs.Generic.SNAP_AIM_YAW_STEP,
                    Configs.Generic.STRUCTURE_BLOCK_MAX_SIZE,
                    Generic.DAY_CYCLE_OVERRIDE_TIME,
                    Generic.WEATHER_OVERRIDE_OPTION,
                    Generic.SCAFFOLD_PLACE_DISTANCE,
                    Generic.SCAFFOLD_PLACE_VANILLA,
                    Generic.CONTAINER_SCAN_MIN_ITEMS,
                    Generic.CONTAINER_SCAN_MIN_TYPES,
                    Generic.AFK_TIMEOUT,
                    Generic.AFK_ACTION,
                    Configs.Generic.TOOL_SWITCHABLE_SLOTS,
                    Generic.NOTE_EDIT_LETTERS,
                    Configs.Generic.TOOL_SWITCH_IGNORED_SLOTS,
                    Configs.Generic.ZOOM_FOV
            ));
        }

        @Accessor
        private static void setOPTIONS(ImmutableList<IConfigBase> o){};
    }

    @Mixin(Configs.Fixes.class)
    public static class FixesMixin
    {
        @Inject(method = "<init>", at = @At("RETURN"))
        private static void init(CallbackInfo ci){
            setOPTIONS(ImmutableList.of(
                    Configs.Fixes.ELYTRA_FIX,
                    Configs.Fixes.MAC_HORIZONTAL_SCROLL,
                    Configs.Fixes.RAVAGER_CLIENT_BLOCK_BREAK_FIX,
                    Fixes.CHEST_MIRROR_FIX,
                    Fixes.LAVA_DESTROY_FIX
            ));
        }

        @Accessor
        private static void setOPTIONS(ImmutableList<IConfigBase> o){};
    }

    @Mixin(Configs.Lists.class)
    public static class ListsMixin
    {
        @Inject(method = "<init>", at = @At("RETURN"))
        private static void init(CallbackInfo ci){
            setOPTIONS(ImmutableList.of(
                    Configs.Lists.BLOCK_TYPE_BREAK_RESTRICTION_LIST_TYPE,
                    Configs.Lists.BLOCK_TYPE_BREAK_RESTRICTION_BLACKLIST,
                    Configs.Lists.BLOCK_TYPE_BREAK_RESTRICTION_WHITELIST,
                    Configs.Lists.CREATIVE_EXTRA_ITEMS,
                    Configs.Lists.ENTITY_TYPE_ATTACK_RESTRICTION_LIST_TYPE,
                    Configs.Lists.ENTITY_TYPE_ATTACK_RESTRICTION_BLACKLIST,
                    Configs.Lists.ENTITY_TYPE_ATTACK_RESTRICTION_WHITELIST,
                    Configs.Lists.ENTITY_WEAPON_MAPPING,
                    Configs.Lists.FAST_PLACEMENT_ITEM_LIST_TYPE,
                    Configs.Lists.FAST_RIGHT_CLICK_BLOCK_LIST_TYPE,
                    Configs.Lists.FAST_RIGHT_CLICK_ITEM_LIST_TYPE,
                    Configs.Lists.POTION_WARNING_LIST_TYPE,
                    Configs.Lists.FAST_PLACEMENT_ITEM_BLACKLIST,
                    Configs.Lists.FAST_PLACEMENT_ITEM_WHITELIST,
                    Configs.Lists.FAST_RIGHT_CLICK_BLOCK_BLACKLIST,
                    Configs.Lists.FAST_RIGHT_CLICK_BLOCK_WHITELIST,
                    Configs.Lists.FAST_RIGHT_CLICK_ITEM_BLACKLIST,
                    Configs.Lists.FAST_RIGHT_CLICK_ITEM_WHITELIST,
                    Lists.BLOCK_TYPE_RCLICK_RESTRICTION_MODE,
                    Lists.BLOCK_TYPE_RCLICK_WHITELIST,
                    Lists.BLOCK_TYPE_RCLICK_BLACKLIST,
                    Configs.Lists.FLAT_WORLD_PRESETS,
                    Configs.Lists.HAND_RESTOCK_LIST_TYPE,
                    Configs.Lists.HAND_RESTOCK_BLACKLIST,
                    Configs.Lists.HAND_RESTOCK_WHITELIST,
                    Configs.Lists.POTION_WARNING_BLACKLIST,
                    Configs.Lists.POTION_WARNING_WHITELIST,
                    Configs.Lists.REPAIR_MODE_SLOTS,
                    Configs.Lists.UNSTACKING_ITEMS,
                    Lists.SELECTIVE_BLOCKS_LIST_TYPE,
                    Lists.SELECTIVE_BLOCKS_WHITELIST,
                    Lists.SELECTIVE_BLOCKS_BLACKLIST
            ));
        }

        @Accessor
        private static void setOPTIONS(ImmutableList<IConfigBase> o){};
    }

    @Mixin(Configs.Disable.class)
    public static class DisableMixin
    {
        @Inject(method = "<init>", at = @At("RETURN"))
        private static void init(CallbackInfo ci){
            setOPTIONS(ImmutableList.of(
                    Configs.Disable.DISABLE_ARMOR_STAND_RENDERING,
                    Configs.Disable.DISABLE_AXE_STRIPPING,
                    Configs.Disable.DISABLE_BAT_SPAWNING,
                    Configs.Disable.DISABLE_BEACON_BEAM_RENDERING,
                    Configs.Disable.DISABLE_BLOCK_BREAK_PARTICLES,
                    Disable.DISABLE_CLIENT_BLOCK_EVENTS,
                    Configs.Disable.DISABLE_DOUBLE_TAP_SPRINT,
                    Configs.Disable.DISABLE_BOSS_BAR,
                    Configs.Disable.DISABLE_BOSS_FOG,
                    Configs.Disable.DISABLE_CHUNK_RENDERING,
                    Configs.Disable.DISABLE_CLIENT_ENTITY_UPDATES,
                    Configs.Disable.DISABLE_CLIENT_LIGHT_UPDATES,
                    Configs.Disable.DISABLE_CONSTANT_CHUNK_SAVING,
                    Configs.Disable.DISABLE_CREATIVE_INFESTED_BLOCKS,
                    Configs.Disable.DISABLE_DEAD_MOB_RENDERING,
                    Configs.Disable.DISABLE_DEAD_MOB_TARGETING,
                    Configs.Disable.DISABLE_ENTITY_RENDERING,
                    Configs.Disable.DISABLE_ENTITY_TICKING,
                    Configs.Disable.DISABLE_FALLING_BLOCK_RENDER,
                    Configs.Disable.DISABLE_FP_EFFECT_PARTICLES,
                    Configs.Disable.DISABLE_INVENTORY_EFFECTS,
                    Configs.Disable.DISABLE_ITEM_SWITCH_COOLDOWN,
                    Configs.Disable.DISABLE_MOB_SPAWNER_MOB_RENDER,
                    Configs.Disable.DISABLE_NAUSEA_EFFECT,
                    Configs.Disable.DISABLE_NETHER_FOG,
                    Configs.Disable.DISABLE_NETHER_PORTAL_SOUND,
                    Configs.Disable.DISABLE_OBSERVER,
                    Configs.Disable.DISABLE_OFFHAND_RENDERING,
                    Configs.Disable.DISABLE_PARTICLES,
                    Configs.Disable.DISABLE_PORTAL_GUI_CLOSING,
                    Configs.Disable.DISABLE_RAIN_EFFECTS,
                    Configs.Disable.DISABLE_RENDERING_SCAFFOLDING,
                    Configs.Disable.DISABLE_RENDER_DISTANCE_FOG,
                    Configs.Disable.DISABLE_SCOREBOARD_RENDERING,
                    Configs.Disable.DISABLE_SHULKER_BOX_TOOLTIP,
                    Configs.Disable.DISABLE_SHOVEL_PATHING,
                    Configs.Disable.DISABLE_SIGN_GUI,
                    Configs.Disable.DISABLE_SLIME_BLOCK_SLOWDOWN,
                    Configs.Disable.DISABLE_STATUS_EFFECT_HUD,
                    Configs.Disable.DISABLE_TILE_ENTITY_RENDERING,
                    Configs.Disable.DISABLE_TILE_ENTITY_TICKING,
                    Configs.Disable.DISABLE_VILLAGER_TRADE_LOCKING,
                    Configs.Disable.DISABLE_WALL_UNSPRINT,
                    Configs.Disable.DISABLE_WORLD_VIEW_BOB,
                    Disable.DISABLE_CONTAINER_SCAN_OUTLINES,
                    Disable.DISABLE_NAMETAGS,
                    Disable.DISABLE_STRUCTURE_RENDERING
            ));
        }

        @Accessor
        private static void setOPTIONS(ImmutableList<IConfigBase> o){};
    }

    @Inject(method = "Lfi/dy/masa/tweakeroo/config/Configs;loadFromFile()V", remap = false,
            at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/config/options/ConfigOptionList;getOptionListValue()Lfi/dy/masa/malilib/config/IConfigOptionListEntry;", remap = false,
                    ordinal = 2))
    private static void loadFromFile(CallbackInfo ci) {
        PlacementTweaks.BLOCK_TYPE_RCLICK_RESTRICTION.setListType((ListType) Lists.BLOCK_TYPE_RCLICK_RESTRICTION_MODE.getOptionListValue());
        PlacementTweaks.BLOCK_TYPE_RCLICK_RESTRICTION.setListContents(
                Lists.BLOCK_TYPE_RCLICK_BLACKLIST.getStrings(),
                Lists.BLOCK_TYPE_RCLICK_WHITELIST.getStrings());

        RenderTweaks.rebuildLists(); // TODO
    }

    @Inject(method = "Lfi/dy/masa/tweakeroo/config/Configs;loadFromFile()V", remap = false,
            at = @At("TAIL"))
    private static void loadFromFile2(CallbackInfo ci) {
        RenderTweaks.rebuildLists();
    }
}
