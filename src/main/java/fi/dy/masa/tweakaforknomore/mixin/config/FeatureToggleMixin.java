package fi.dy.masa.tweakaforknomore.mixin.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.util.StringUtils;
import fi.dy.masa.tweakaforknomore.config.FeatureToggleI;
import fi.dy.masa.tweakeroo.config.FeatureToggle;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(FeatureToggle.class)
@Unique
public abstract class FeatureToggleMixin
{
    @Shadow
    @Final
    @Mutable
    private static FeatureToggle[] $VALUES;

    @Accessor
    private static void  setVALUES(ImmutableList<FeatureToggle> list){};

    @Invoker("<init>")
    public static FeatureToggle featureToggleExpansion$invokeInit(String internalName, int internalId, String name, boolean defaultValue, boolean singlePlayer, String defaultHotkey, KeybindSettings settings, String comment, String prettyName) {
        throw new AssertionError();
    }

    private static FeatureToggle featureToggleExpansion$addVariant(String internalName, String name, boolean defaultValue, boolean singlePlayer, String defaultHotkey, KeybindSettings settings, String comment, String prettyName) {
        ArrayList<FeatureToggle> values = new ArrayList<>(Arrays.asList(FeatureToggleMixin.$VALUES));
        FeatureToggle featureToggle = featureToggleExpansion$invokeInit(internalName, values.get(values.size() - 1).ordinal() + 1, name, defaultValue, singlePlayer, defaultHotkey, settings, comment, prettyName);
        values.add(featureToggle);
        FeatureToggleMixin.$VALUES = values.toArray(new FeatureToggle[0]);
        return featureToggle;
    }

    private static FeatureToggle addFeatureToggle(String internalName, String name, boolean defaultValue, String defaultHotkey, String comment) {
        return featureToggleExpansion$addVariant(internalName, name, defaultValue, false, defaultHotkey, KeybindSettings.DEFAULT, comment, StringUtils.splitCamelCase(name.substring(5)));
    }

    private static boolean init(){
        FeatureToggleI.STACK_FLEXIBLE = addFeatureToggle("STACK_FLEXIBLE", "stackFlexible",                       false, "",    "If enabled, then flexible block placement rotation\nand offsets can be set independantly\ndespite holding both hotkeys");
        FeatureToggleI.TWEAK_RENDER_ALL_ENTITIES = addFeatureToggle("TWEAK_RENDER_ALL_ENTITIES", "tweakRenderAllEntities",              false, "",    "When enabled, all entities are rendered.");
        FeatureToggleI.TWEAK_SELECTIVE_BLOCKS_RENDERING = addFeatureToggle("TWEAK_SELECTIVE_BLOCKS_RENDERING", "tweakSelectiveBlocksRendering",      false, "",    "Enables selectively visible blocks rendering");
        FeatureToggleI.TWEAK_SELECTIVE_BLOCKS_RENDER_OUTLINE = addFeatureToggle("TWEAK_SELECTIVE_BLOCKS_RENDER_OUTLINE", "tweakSelectiveBlocksRenderOutline", false, "",    "Renders an outline over listed blocks");
        FeatureToggleI.TWEAK_AREA_SELECTOR = addFeatureToggle("TWEAK_AREA_SELECTOR", "tweakAreaSelector",                   false, "",    "Enables the area selector");
        FeatureToggleI.TWEAK_DAY_CYCLE_OVERRIDE = addFeatureToggle("TWEAK_DAY_CYCLE_OVERRIDE", "tweakDayCycleOverride",               false, "",    "Overrides the time of day on the client only");
        FeatureToggleI.TWEAK_WEATHER_OVERRIDE = addFeatureToggle("TWEAK_WEATHER_OVERRIDE", "tweakWeatherOverride",                false, "",    "Overrides the weather on the client only");
        FeatureToggleI.TWEAK_NO_SNEAK_SLOWDOWN = addFeatureToggle("TWEAK_NO_SNEAK_SLOWDOWN", "tweakNoSneakSlowdown",                     false, "",    "Disables slowdown when sneaking");
        FeatureToggleI.TWEAK_SCAFFOLD_PLACE = addFeatureToggle("TWEAK_SCAFFOLD_PLACE", "tweakScaffoldPlace",                  false, "",    "Place blocks as if they were scaffolding");
        FeatureToggleI.TWEAK_CONTAINER_SCAN = addFeatureToggle("TWEAK_CONTAINER_SCAN", "tweakContainerScan",                  false, "",    "Scans containers");
        FeatureToggleI.TWEAK_CONTAINER_SCAN_COUNTS = addFeatureToggle("TWEAK_CONTAINER_SCAN_COUNTS", "tweakContainerScanCounts",            false, "",    "Enables/disables container scan counts rendering");
        FeatureToggleI.TWEAK_AFK_TIMEOUT = addFeatureToggle("TWEAK_AFK_TIMEOUT", "tweakAfkTimeout",                     false, "",    "When enabled, will perform an action after afking for a while");
        FeatureToggleI.TWEAK_NOTEBLOCK_EDIT = addFeatureToggle("TWEAK_NOTEBLOCK_EDIT", "tweakNoteblockEdit",                  false, "",    "Turns on noteblock editing mode");
        FeatureToggleI.TWEAK_STANDARD_ASPECT_RATIO = addFeatureToggle("TWEAK_STANDARD_ASPECT_RATIO", "tweakStandardAspectRatio",            false, "",    "Forces 16:9 aspect ratio for the game.\n#macbook-gang.");
        setVALUES(ImmutableList.copyOf(FeatureToggleMixin.$VALUES));
        return true;
    }

    private static final boolean __done_ = init();

















}
