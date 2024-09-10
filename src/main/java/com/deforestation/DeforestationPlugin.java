package com.deforestation;

import javax.inject.Inject;
import com.google.gson.Gson;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import java.util.*;

@Slf4j
@PluginDescriptor(
	name = "Deforestation"
)
public class DeforestationPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private DeforestationConfig config;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ConfigManager configManager;

	@Inject
	private Gson gson;

	private ExplosionEffectManager explosionManager;
	private StumpManager stumpManager;

	private HashMap<WorldPoint, TreeStumpData> choppedMap = new HashMap<>();
	private TileObject selectedTree;
	private boolean WasCutting;

	@Provides
	DeforestationConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(DeforestationConfig.class);
	}

	@Override
	protected void startUp()
	{
		explosionManager = new ExplosionEffectManager(client, config, clientThread);
		stumpManager = new StumpManager(client, config, clientThread);
		SaveFileManager.Init(gson, configManager);

		if (config.deleteSavedData())
		{
			SaveFileManager.DeleteData();
		}

		if (config.useSavedData() && config.deleteSavedData() == false)
		{
			choppedMap.clear();
			choppedMap = SaveFileManager.Load();
		}
	}

	@Override
	protected void shutDown()
	{
		explosionManager.OnShutDown();
		stumpManager.OnShutDown();

		choppedMap.clear();
		clientThread.invoke(() ->
		{
			if (client.getGameState() == GameState.LOGGED_IN)
			{
				client.setGameState(GameState.LOADING);
			}
		});
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			if (config.showStumps())
			{
				stumpManager.PruneStumpsOutOfScene();
			}
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getKey().equals("saveData") && config.useSavedData())
		{
			if (config.useSavedData())
			{
				choppedMap.clear();
				choppedMap = SaveFileManager.Load();
			}
			HideCutTrees();
		}

		if (event.getKey().equals("deleteSave") && config.deleteSavedData())
		{
			choppedMap.clear();
			SaveFileManager.DeleteData();
			HideCutTrees();
		}

		if (event.getKey().equals("showStumps"))
		{
			if (config.showStumps())
			{
				clientThread.invoke(() ->
				{
					stumpManager.GenerateMultipleStumps(choppedMap);
					HideCutTrees();
				});
			}
			else if (config.showStumps() == false)
			{
				stumpManager.Dispose();
				clientThread.invoke(() ->
				{
					if (client.getGameState() == GameState.LOGGED_IN)
					{
						client.setGameState(GameState.LOADING);
					}
					HideCutTrees();
				});
			}
		}
	}

	@Subscribe
	public void onAnimationChanged(AnimationChanged event)
	{
		Player localPlayer = client.getLocalPlayer();
		if (localPlayer != event.getActor()) { return; }

		int animation = localPlayer.getAnimation();
		if (DataFinder.IsWoodcuttingAnimation(animation) == false) { return; }

		WasCutting = true;
	}

	@Subscribe
	public void onGameObjectSpawned(final GameObjectSpawned event)
	{
		GameObject gameObject = event.getGameObject();
		if (DataFinder.GetTreeID(gameObject.getId()) != -1 && choppedMap.containsKey(gameObject.getWorldLocation()))
		{
			// Tree spawned
			HideCutTrees();
		}
		else if (WasCutting && DataFinder.GetStumpID(gameObject.getId()) != -1 && choppedMap.containsKey(gameObject.getWorldLocation()))
		{
			// Stump spawned
			if (config.devLogs())
			{
				log.info("[Deforestation] Stump spawned at: {} with id: {}", gameObject.getWorldLocation(), gameObject.getId());
			}

			TreeStumpData data = choppedMap.get(gameObject.getWorldLocation());
			data.stumpId = gameObject.getId();

			SaveFileManager.Save(choppedMap);

			WasCutting = false;
			selectedTree = null;
		}
	}

	@Subscribe
	public void onGameObjectDespawned(final GameObjectDespawned event)
	{
		GameObject gameObject = event.getGameObject();
		if (DataFinder.GetTreeID(gameObject.getId()) != -1)
		{
			OnTreeCut(gameObject);
		}
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked menuOptionClicked)
	{
		if (DataFinder.IsInteractionClick(menuOptionClicked))
		{
			selectedTree = WorldHelpers.GetTreeTileObject(client, menuOptionClicked.getParam0(), menuOptionClicked.getParam1(), menuOptionClicked.getId());
		}
		else if (DataFinder.IsResetClick(menuOptionClicked))
		{
			selectedTree = null;
		}
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		explosionManager.OnGameTick();
	}

	private void OnTreeCut(GameObject gameObject)
	{
		if (WasCutting == false || selectedTree == null) { return; } // We were not cutting
		if (WorldHelpers.IsSameWorldPos(selectedTree.getWorldLocation(), gameObject.getWorldLocation()) == false) { return; } // This isn't our tree

		if (choppedMap.containsKey((gameObject.getWorldLocation())) == false)
		{
			choppedMap.put(gameObject.getWorldLocation(), new TreeStumpData(gameObject.getId(), 0, gameObject.getOrientation()));
			if (config.useSavedData())
			{
				HideCutTrees();
				explosionManager.OnTreeCut(gameObject.getLocalLocation(), gameObject.getPlane());
			}
			if (config.devLogs())
			{
				log.info("[Deforestation] Tree cut at: {} with id: {}", gameObject.getWorldLocation(), gameObject.getId());
			}
		}
	}

	private void HideCutTrees()
	{
		if (choppedMap.isEmpty()) { return; }

		if (config.showStumps())
		{
			stumpManager.PruneStumpsOutOfScene();
		}

		List<GameObject> foundTrees = WorldHelpers.GetObjectsFromScene(client, choppedMap.keySet());
		if (foundTrees.isEmpty()) { return; }

		for (GameObject gameObject : foundTrees)
		{
			if (gameObject == null) { continue; }

			if (choppedMap.containsKey(gameObject.getWorldLocation()))
			{
				if (DataFinder.GetTreeID(gameObject.getId()) != -1)
				{
					WorldHelpers.GetScene(client).removeGameObject(gameObject);
				}
			}
		}

		if (config.showStumps())
		{
			stumpManager.Dispose();
			stumpManager.GenerateMultipleStumps(choppedMap);
		}
	}
}
