package fi.dy.masa.tweakaforknomore.config;

import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;

public class Hotkeys
{
    public static final ConfigHotkey FLEXIBLE_BLOCK_PLACEMENT_HOLD      = new ConfigHotkey("flexibleBlockPlacementHold",        "", KeybindSettings.RELEASE_ALLOW_EXTRA, "Hold flexible block placement offset & rotation");
    public static final ConfigHotkey AREA_SELECTION_OFFSET              = new ConfigHotkey("areaSelectionOffset",     "LEFT_SHIFT",     KeybindSettings.PRESS_ALLOWEXTRA, "The key to offset selection pos");
    public static final ConfigHotkey AREA_SELECTION_ADD_TO_LIST         = new ConfigHotkey("areaSelectionAddToList",            "",     KeybindSettings.PRESS_ALLOWEXTRA, "Add selected blocks to list");
    public static final ConfigHotkey AREA_SELECTION_REMOVE_FROM_LIST    = new ConfigHotkey("areaSelectionRemoveFromList",       "",     KeybindSettings.PRESS_ALLOWEXTRA, "remove selected blocks from list");
    public static final ConfigHotkey OPEN_ITEM_LIST                     = new ConfigHotkey("openItemList",                      "",     "Opens the item list gui");
    public static final ConfigHotkey CONTAINER_SCANNER_CLEAR_CACHE      = new ConfigHotkey("containerScannerClearCache",        "",     "Clears the container scanner cache");

}
