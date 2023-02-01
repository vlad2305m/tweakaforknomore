package fi.dy.masa.tweakaforknomore.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.util.restrictions.UsageRestriction.ListType;
import fi.dy.masa.tweakaforknomore.util.WeatherOverrideMode;

public class Configs
{

    public static class Generic
    {
        public static final ConfigInteger       RESTRICTION_LAYER_HEIGHT            = new ConfigInteger     ("restrictionLayerHeight", 1, -1000, 1000, "The layer height for the layer restriction mode.\nTo quickly adjust the value, scroll while\nholding down the tweak toggle keybind.");
        public static final ConfigBoolean       AREA_SELECTION_USE_ALL              = new ConfigBoolean     ("areaSelectionUseAll", false, "Whether or not to include air in selection");
        public static final ConfigBoolean       SELECTIVE_BLOCKS_TRACK_PISTONS      = new ConfigBoolean     ("selectiveBlocksTrackPistons", false, "Whether or not to track piston movements for selective block rendering");
        public static final ConfigBoolean       SELECTIVE_BLOCKS_HIDE_PARTICLES     = new ConfigBoolean     ("selectiveBlocksHideParticles", true, "Whether or not to hide particles for selective block rendering");
        public static final ConfigBoolean       SELECTIVE_BLOCKS_HIDE_ENTITIES      = new ConfigBoolean     ("selectiveBlocksHideEntities", false, "Whether or not to hide entities for selective block rendering");
        public static final ConfigBoolean       SELECTIVE_BLOCKS_NO_HIT             = new ConfigBoolean     ("selectiveBlocksNoHit", true, "Whether or not to disable targeting hidden blocks");
        public static final ConfigInteger       DAY_CYCLE_OVERRIDE_TIME             = new ConfigInteger     ("dayCycleOverrideTime", 0, 0, 24000, "The day time to use when overriding the daylight cycle time");
        public static final ConfigOptionList    WEATHER_OVERRIDE_OPTION             = new ConfigOptionList  ("weatherOverrideOption", WeatherOverrideMode.CLEAR, "The weather to use when overriding the client weather");
        public static final ConfigInteger       SCAFFOLD_PLACE_DISTANCE             = new ConfigInteger     ("scaffoldPlaceDistance", 5, 1, 20, "Scaffold place max distance");
        public static final ConfigBoolean       SCAFFOLD_PLACE_VANILLA              = new ConfigBoolean     ("scaffoldPlaceVanilla", false, "When enabled, extend direction is only set by player direction");
        public static final ConfigInteger       CONTAINER_SCAN_MIN_ITEMS            = new ConfigInteger     ("containerScanMinItems", 1, 1, 100000, "Minimum items a container needs to have to be displayed");
        public static final ConfigInteger       CONTAINER_SCAN_MIN_TYPES            = new ConfigInteger     ("containerScanMinTypes", 1, 1, 100000, "Minimum item types a container needs to have to be displayed");
        public static final ConfigInteger       AFK_TIMEOUT                         = new ConfigInteger     ("afkTimeout", 2400, 200, 200000, "Number of ticks for AFK timeout");
        public static final ConfigString        AFK_ACTION                          = new ConfigString      ("afkAction", "/disconnect", "The action to perform on AFK timeout. /disconnect is default.");
        public static final ConfigBoolean       NOTE_EDIT_LETTERS                   = new ConfigBoolean     ("noteEditLetters", false, "When enabled, can use letter keys to set noteblock notes");

    }

    public static class Fixes
    {
        public static final ConfigBoolean CHEST_MIRROR_FIX                  = new ConfigBoolean("chestMirrorFix", false, "Fixes chest mirroring (eg, with litematica)");
        public static final ConfigBoolean LAVA_DESTROY_FIX                  = new ConfigBoolean("lavaDestroyFix", false, "Fixes lava destroying entities on the client when it shouldn't");

    }

    public static class Lists
    {
        public static final ConfigOptionList BLOCK_TYPE_RCLICK_RESTRICTION_MODE = new ConfigOptionList("blockTypeRClickRestrictionMode", ListType.NONE, "Restrict blocks you can right click on.");
        public static final ConfigStringList BLOCK_TYPE_RCLICK_WHITELIST        = new ConfigStringList("blockTypeRClickWhiteList", ImmutableList.of(), "The blocks that can be broken with type break restriction");
        public static final ConfigStringList BLOCK_TYPE_RCLICK_BLACKLIST        = new ConfigStringList("blockTypeRClickBlackList", ImmutableList.of(), "The blocks that can NOT be broken with type break restriction");
        public static final ConfigOptionList SELECTIVE_BLOCKS_LIST_TYPE         = new ConfigOptionList("selectiveBlocksListType", ListType.NONE, "The list type for selective blocks tweak");
        public static final ConfigString SELECTIVE_BLOCKS_WHITELIST             = new ConfigString("selectiveBlocksWhitelist", "", "The block positions you want to whitelist");
        public static final ConfigString SELECTIVE_BLOCKS_BLACKLIST             = new ConfigString("selectiveBlocksBlacklist", "", "The block positions you want to blacklist");

    }

    public static class Disable
    {
        public static final ConfigBooleanHotkeyed       DISABLE_CLIENT_BLOCK_EVENTS     = new ConfigBooleanHotkeyed("disableClientBlockEvents",           false, "", "Disables block event rendering (eg pistons animations)");
        public static final ConfigBooleanHotkeyed       DISABLE_CONTAINER_SCAN_OUTLINES = new ConfigBooleanHotkeyed("disableContainerScanOutlines",         false, "", "Disables rendering container scan outlines");
        public static final ConfigBooleanHotkeyed       DISABLE_NAMETAGS                = new ConfigBooleanHotkeyed("disableNametags",                      false, "", "Disables rendering name tags");
        public static final ConfigBooleanHotkeyed       DISABLE_STRUCTURE_RENDERING     = new ConfigBooleanHotkeyed("disableStructureRendering",            false, "", "Disables structure block rendering");

    }
}
