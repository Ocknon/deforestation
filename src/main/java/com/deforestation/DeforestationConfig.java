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
            keyName = "deleteSave",
            name = "Delete saved data",
            description = "Delete saved file.",
            position = 2
    )
    default boolean deleteSavedData() { return false; }
}
