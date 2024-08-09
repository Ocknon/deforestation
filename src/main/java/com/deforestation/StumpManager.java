package com.deforestation;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.RuneLite;
import net.runelite.client.callback.ClientThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class StumpManager
{
    private final HashMap<WorldPoint, RuneLiteObject> stumpMap = new HashMap<>();
    private final Client client;
    private final DeforestationConfig config;
    private final ClientThread thread;

    public StumpManager (Client mainClient, DeforestationConfig mainConfig, ClientThread mainThread)
    {
        client = mainClient;
        config = mainConfig;
        thread = mainThread;
    }

    public void OnShutDown()
    {
        Dispose();
    }

    public void GenerateMultipleStumps(HashMap<WorldPoint, TreeStumpData> choppedMap)
    {
        WorldView view = client.getTopLevelWorldView();

        for (int i = 0; i < stumpMap.size(); i++)
        {
            WorldPoint key = (WorldPoint) stumpMap.keySet().toArray()[i];
            stumpMap.get(key).setActive(false);
        }
        stumpMap.clear();

        for (int i = 0; i < choppedMap.size(); i++)
        {
            WorldPoint point = (WorldPoint) choppedMap.keySet().toArray()[i];
            LocalPoint local = LocalPoint.fromWorld(view, point);

            // Offset big trees
            if (DataFinder.IsLargeStump(choppedMap.get(point).stumpId))
            {
                local = new LocalPoint(local.getX() + 64, local.getY() + 64, view);
            }

            if (WorldPoint.isInScene(view, point.getX(), point.getY()))
            {
                GenerateStumpObject(point, local, choppedMap.get(point).stumpId, choppedMap.get(point).orientation);
            }
        }
    }

    private void GenerateStumpObject(WorldPoint treeWorldPoint, LocalPoint treeLocalPoint, int stumpId, int orientation)
    {
        int modelId = DataFinder.GetModelIdFromStumpId(stumpId);
        if (modelId == -1) { return; }

        thread.invoke(() ->
        {
            RuneLiteObject obj = client.createRuneLiteObject();
            obj.setModel(client.loadModel(modelId));
            obj.setLocation(treeLocalPoint, treeWorldPoint.getPlane());
            obj.setOrientation(orientation);
            obj.setDrawFrontTilesFirst(true);
            obj.setActive(true);
            stumpMap.put(treeWorldPoint, obj);
            if (config.devLogs())
            {
                log.info("[Deforestation] Stump object created at: {} with id: {} and model id: {}", treeWorldPoint, stumpId, modelId);
            }
        });
    }

    public void PruneStumpsOutOfScene()
    {
        if (stumpMap.isEmpty()) { return; }

        WorldView view = client.getTopLevelWorldView();
        List<WorldPoint> pruned = new ArrayList<>();
        List<RuneLiteObject> tempStumps = new ArrayList<>();

        for (int i = 0; i < stumpMap.size(); i++)
        {
            WorldPoint point = (WorldPoint)stumpMap.keySet().toArray()[i];
            if (WorldPoint.isInScene(view, point.getX(), point.getY()) == false)
            {
                pruned.add(point);
            }
        }

        for (WorldPoint point : pruned)
        {
            tempStumps.add(stumpMap.get(point));
            stumpMap.remove(point);
        }

        if (config.devLogs())
        {
            log.info("[Deforestation] Stumps pruned: {}", pruned.size());
        }

        pruned.clear();
        thread.invoke(() ->
        {
            for (RuneLiteObject removedStump : tempStumps)
            {
                removedStump.setActive(false);
            }
        });
    }

    public void Dispose()
    {
        if (stumpMap.isEmpty()) { return; }

        thread.invoke(() ->
        {
            for (int i = 0; i < stumpMap.size(); i++)
            {
                WorldPoint key = (WorldPoint) stumpMap.keySet().toArray()[i];
                stumpMap.get(key).setActive(false);
            }

            stumpMap.clear();

            if (config.devLogs())
            {
                log.info("[Deforestation] Stumps disposed.");
            }
        });
    }
}
