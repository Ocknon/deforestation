package com.deforestation;

import com.google.gson.*;
import net.runelite.api.coords.WorldPoint;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.HashMap;

public class SaveFileManager
{
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static String fileName = "DeforestationData.json";

    public static void Save(HashMap<WorldPoint, TreeStumpData> data)
    {
        String filePath = Paths.get(System.getProperty("user.dir"), fileName).toString();

        SerializableTreeData[] serializables = new SerializableTreeData[data.size()];
        for (int i = 0; i < data.size(); i++)
        {
            WorldPoint point = (WorldPoint) data.keySet().toArray()[i];
            serializables[i] = CastData(point, data.get(point));
        }

        try (FileWriter writer = new FileWriter(filePath, false))
        {
            gson.toJson(serializables, writer);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static HashMap<WorldPoint, TreeStumpData> Load()
    {
        String filePath = Paths.get(System.getProperty("user.dir"), fileName).toString();

        File file = new File(filePath);
        if (!file.exists()) { return new HashMap<WorldPoint, TreeStumpData>(); }

        SerializableTreeData[] data = new SerializableTreeData[0];
        HashMap<WorldPoint, TreeStumpData> loadedTrees = new HashMap<>();
        try (FileReader reader = new FileReader(filePath))
        {
            Type type = data.getClass();
            data = gson.fromJson(reader, type);
            for (SerializableTreeData datum : data)
            {
                Pair<WorldPoint, TreeStumpData> treeData = DeserializeData(datum);
                loadedTrees.put(treeData.getLeft(), treeData.getRight());
            }
        }
        catch (IOException e)
        {
            return new HashMap<WorldPoint, TreeStumpData>();
        }

        return loadedTrees;
    }

    public static void DeleteData()
    {
        String filePath = Paths.get(System.getProperty("user.dir"), fileName).toString();
        File file = new File(filePath);
        if (file.exists())
        {
            file.delete();
        }
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
