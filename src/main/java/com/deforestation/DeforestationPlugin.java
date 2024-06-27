package com.deforestation;

import javax.inject.Inject;

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

import java.util.HashMap;
import java.util.Map;

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

	private Map<WorldPoint, Integer> choppedMap = new HashMap<WorldPoint, Integer>();
	private TileObject selectedTree;
	private boolean WasCutting;

	@Provides
	DeforestationConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(DeforestationConfig.class);
	}

	@Override
	protected void startUp()
	{
		if (config.deleteSavedData())
		{
			DeforestationSaveFile.DeleteData();
		}

		if (config.useSavedData() && config.deleteSavedData() == false)
		{
			choppedMap.clear();
			choppedMap = DeforestationSaveFile.Load();
		}
	}

	@Override
	protected void shutDown()
	{
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
			HideCut();
		}
	}

	@Subscribe
	public  void onConfigChanged(ConfigChanged configChanged)
	{
		if (config.useSavedData())
		{
			choppedMap.clear();
			choppedMap = DeforestationSaveFile.Load();
		}
		if (config.deleteSavedData())
		{
			choppedMap.clear();
			DeforestationSaveFile.DeleteData();
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "[Deforestation] Deleted saved file!", null);
		}
		HideCut();
	}

	@Subscribe
	public void onAnimationChanged(AnimationChanged event)
	{
		Player localPlayer = client.getLocalPlayer();
		if (localPlayer != event.getActor()) { return; }

		int animation = localPlayer.getAnimation();
		if (DeforestationDataLookups.IsWoodcuttingAnimation(animation) == false) { return; }

		WasCutting = true;
	}

	@Subscribe
	public void onGameObjectSpawned(final GameObjectSpawned event)
	{
		GameObject gameObject = event.getGameObject();
		if (DeforestationDataLookups.GetTreeID(gameObject.getId()) != -1)
		{
			OnTreeSpawn();
		}
	}

	@Subscribe
	public void onGameObjectDespawned(final GameObjectDespawned event)
	{
		GameObject gameObject = event.getGameObject();
		if (DeforestationDataLookups.GetTreeID(gameObject.getId()) != -1)
		{
			OnTreeCut(gameObject);
		}
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked menuOptionClicked)
	{
		switch (menuOptionClicked.getMenuAction())
		{
			case WIDGET_TARGET_ON_GAME_OBJECT:
			case GAME_OBJECT_FIRST_OPTION:
			case GAME_OBJECT_SECOND_OPTION:
			case GAME_OBJECT_THIRD_OPTION:
			case GAME_OBJECT_FOURTH_OPTION:
			case GAME_OBJECT_FIFTH_OPTION:
			{
				selectedTree = GetTreeObject(menuOptionClicked.getParam0(), menuOptionClicked.getParam1(), menuOptionClicked.getId());
				break;
			}
			case WALK:
			case WIDGET_TARGET_ON_WIDGET:
			case WIDGET_TARGET_ON_GROUND_ITEM:
			case WIDGET_TARGET_ON_PLAYER:
			case GROUND_ITEM_FIRST_OPTION:
			case GROUND_ITEM_SECOND_OPTION:
			case GROUND_ITEM_THIRD_OPTION:
			case GROUND_ITEM_FOURTH_OPTION:
			case GROUND_ITEM_FIFTH_OPTION:
			{
				selectedTree = null;
				break;
			}
		}
	}

	private void OnTreeSpawn()
	{
		HideCut();
	}

	private void OnTreeCut(GameObject gameObject)
	{
		if (WasCutting == false || selectedTree == null) { return; } // We were not cutting
		if (IsSameWorldPos(selectedTree.getWorldLocation(), gameObject.getWorldLocation()) == false) { return; } // This isn't our tree

		if (choppedMap.containsKey((gameObject.getWorldLocation())) == false)
		{
			choppedMap.put(gameObject.getWorldLocation(), gameObject.getId());
			if (config.useSavedData())
			{
				DeforestationSaveFile.Save(choppedMap);
			}
		}

		WasCutting = false;
		selectedTree = null;
	}

	private void HideCut()
	{
		if (choppedMap.isEmpty()) { return; }

		Scene scene = client.getScene();
		Tile[][] tiles = scene.getTiles()[0];
		for (int x = 0; x < Constants.SCENE_SIZE; x++)
		{
			for (int y = 0; y < Constants.SCENE_SIZE; y++)
			{
				Tile tile = tiles[x][y];
				if (tile == null) { continue; }

				for (GameObject gameObject : tile.getGameObjects())
				{
					if (gameObject == null) { continue; }

					if (choppedMap.containsKey(gameObject.getWorldLocation()))
					{
						scene.removeGameObject(gameObject);
					}
				}
			}
		}
	}

	private TileObject GetTreeObject(int x, int y, int id)
	{
		if (DeforestationDataLookups.GetTreeID(id) == -1) { return null; }

		Scene scene = client.getScene();
		Tile[][][] tiles = scene.getTiles();
		Tile tile = tiles[client.getPlane()][x][y];
		if (tile == null) { return null; }

		for (GameObject gameObject : tile.getGameObjects())
		{
			if (gameObject != null && gameObject.getId() == id)
			{
				return gameObject;
			}
		}

		// Redwoods might count as a wall object?
		WallObject wallObject = tile.getWallObject();
		if (wallObject != null && wallObject.getId() == id)
		{
			return wallObject;
		}

		return null;
	}

	private boolean IsSameWorldPos(WorldPoint point1, WorldPoint point2)
	{
		if (point1.getRegionX() != point2.getRegionX()) { return false; }
		else if (point1.getRegionY() != point2.getRegionY()) { return false; }
		else if (point1.getPlane() != point2.getPlane()) { return false; }

		return true;
	}
}
