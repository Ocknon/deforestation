package com.deforestation;

import com.google.gson.*;
import joptsimple.internal.Strings;
import net.runelite.api.coords.WorldPoint;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import java.lang.reflect.Type;
import java.util.HashMap;
import net.runelite.client.config.ConfigManager;

public class SaveFileManager
{
    private static String CONFIG_GROUP = "deforestation";
    private static String TREE_DATA = "data_";

    private static ConfigManager manager;
    private static Gson gson;

    public static void Init(Gson g, ConfigManager configManager)
    {
        manager = configManager;
        gson = g;
    }

    public static void Save(HashMap<WorldPoint, TreeStumpData> data)
    {
        SerializableTreeData[] serializables = new SerializableTreeData[data.size()];
        for (int i = 0; i < data.size(); i++)
        {
            WorldPoint point = (WorldPoint) data.keySet().toArray()[i];
            serializables[i] = CastData(point, data.get(point));
        }
        String json = gson.toJson(serializables);
        manager.setConfiguration(CONFIG_GROUP, TREE_DATA, json);
    }

    public static HashMap<WorldPoint, TreeStumpData> Load()
    {
        String json = manager.getConfiguration(CONFIG_GROUP, TREE_DATA);
        HashMap<WorldPoint, TreeStumpData> loadedTrees = new HashMap<WorldPoint, TreeStumpData>();

        if (Strings.isNullOrEmpty(json)) { return loadedTrees; }

        SerializableTreeData[] data = new SerializableTreeData[0];
        Type type = data.getClass();
        data = gson.fromJson(json, type);
        for (SerializableTreeData datum : data)
        {
            Pair<WorldPoint, TreeStumpData> treeData = DeserializeData(datum);
            loadedTrees.put(treeData.getLeft(), treeData.getRight());
        }

        return loadedTrees;
    }

    public static void DeleteData()
    {
        manager.unsetConfiguration(CONFIG_GROUP, TREE_DATA);
    }

    private static SerializableTreeData CastData(WorldPoint point, TreeStumpData treeData)
    {
        var data = new SerializableTreeData();
        data.x = point.getX();
        data.y = point.getY();
        data.plane = point.getPlane();
        data.treeId = treeData.treeId;
        data.stumpId = treeData.stumpId;
        data.orientation = treeData.orientation;
        return data;
    }

    private static Pair<WorldPoint, TreeStumpData> DeserializeData(SerializableTreeData serializable)
    {
        WorldPoint point = new WorldPoint(serializable.x, serializable.y, serializable.plane);
        TreeStumpData pair = new TreeStumpData(serializable.treeId, serializable.stumpId, serializable.orientation);
        return new ImmutablePair<>(point, pair);
    }
}
