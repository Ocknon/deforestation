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
import java.util.Map;

public class DeforestationSaveFile
{
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static String fileName = "DeforestationData.json";

    public static void Save(Map<WorldPoint, Integer> data)
    {
        String filePath = Paths.get(System.getProperty("user.dir"), fileName).toString();

        DeforestationSerializable[] serializables = new DeforestationSerializable[data.size()];
        for (int i = 0; i < data.size(); i++)
        {
            serializables[i] = CastData((WorldPoint) data.keySet().toArray()[i], (Integer) data.values().toArray()[i]);
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

    public static Map<WorldPoint, Integer> Load()
    {
        String filePath = Paths.get(System.getProperty("user.dir"), fileName).toString();

        File file = new File(filePath);
        if (!file.exists())
        {
            return new HashMap<>();
        }

        DeforestationSerializable[] data = new DeforestationSerializable[0];
        Map<WorldPoint, Integer> loadedTrees = new HashMap<>();
        try (FileReader reader = new FileReader(filePath))
        {
            Type type = data.getClass();
            data = gson.fromJson(reader, type);
            for (DeforestationSerializable datum : data)
            {
                Pair<WorldPoint, Integer> pair = DeserializeData(datum);
                loadedTrees.put(pair.getLeft(), pair.getRight());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
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

    private static DeforestationSerializable CastData(WorldPoint point, Integer id)
    {
        var data = new DeforestationSerializable();
        data.x = point.getX();
        data.y = point.getY();
        data.plane = point.getPlane();
        data.id = id;
        return data;
    }

    private static Pair<WorldPoint, Integer> DeserializeData(DeforestationSerializable serializable)
    {
        WorldPoint point = new WorldPoint(serializable.x, serializable.y, serializable.plane);
        Integer id = serializable.id;
        return new ImmutablePair<>(point, id);
    }
}
