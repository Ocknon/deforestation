package com.deforestation;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("deforestation")
public interface DeforestationConfig extends Config
{
    @ConfigItem(
            keyName = "saveData",
            name = "Use saved data",
            description = "Will enable saving and loading data when toggled on.",
            position = 1
    )
    default boolean useSavedData() { return true; }

    @ConfigItem(
            keyName = "showStumps",
            name = "Show Stumps",
            description = "Show the stumps instead of nothing",
            position = 2
    )
    default boolean showStumps() { return false; }

    @ConfigItem(
            keyName = "deleteSave",
            name = "Delete saved data",
            description = "Delete saved file.",
            position = 3
    )
    default boolean deleteSavedData() { return false; }

    @ConfigItem(
            keyName = "falloutMode",
            name = "Fallout Mode",
            description = "Fallout Mode",
            position = 4
    )
    default boolean falloutMode() { return false; }

    @ConfigItem(
            keyName = "debugLogs",
            name = "Log Debugs",
            description = "Dev - logs into console",
            position = 4
    )
    default boolean devLogs() { return false; }
}
