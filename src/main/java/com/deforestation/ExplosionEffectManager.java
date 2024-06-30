package com.deforestation;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.RuneLiteObject;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.callback.ClientThread;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExplosionEffectManager
{
    private final Client client;
    private final DeforestationConfig config;
    private final ClientThread thread;
    private final List<RuneLiteObject> cachedObjects = new ArrayList<>();
    private final List<Integer> cachedTimes = new ArrayList<>();

    public ExplosionEffectManager(Client mainClient, DeforestationConfig mainConfig, ClientThread mainThread)
    {
        client = mainClient;
        config = mainConfig;
        thread = mainThread;
    }

    public void OnShutDown()
    {
        Dispose();
    }

    public void OnGameTick()
    {
        int liveTicks = 5;

        for (int i = 0; i < cachedObjects.size(); i++)
        {
            if (cachedTimes.get(i) > liveTicks)
            {
                cachedObjects.get(i).setActive(false);
                cachedObjects.remove(i);
                cachedTimes.remove(i);

                if (config.devLogs())
                {
                    log.info("[Deforestation] Explosion object removed.");
                }

                break;
            }
            else
            {
                int val = cachedTimes.get(i);
                cachedTimes.set(i, val + 1);
            }
        }
    }

    public void OnTreeCut(LocalPoint point, int plane)
    {
        thread.invoke(() ->
        {
            RuneLiteObject obj = client.createRuneLiteObject();
            obj.setModel(client.loadModel(3076));
            obj.setLocation(point, plane);
            obj.setAnimation(client.loadAnimation(654));
            obj.setShouldLoop(false);
            obj.setActive(true);

            cachedObjects.add(obj);
            cachedTimes.add(0);

            if (config.devLogs())
            {
                log.info("[Deforestation] Explosion object created at: {}", point);
            }
        });
    }

    private void Dispose()
    {
        for (RuneLiteObject cachedObject : cachedObjects)
        {
            cachedObject.setActive(false);
        }
        cachedObjects.clear();
        cachedTimes.clear();

        if (config.devLogs())
        {
            log.info("[Deforestation] Explosion objects disposed.");
        }
    }
}
