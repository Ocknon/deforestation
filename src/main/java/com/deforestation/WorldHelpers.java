package com.deforestation;

import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WorldHelpers
{
    public static Scene GetScene(Client client)
    {
        return client.getTopLevelWorldView().getScene();
    }

    public static boolean IsSameWorldPos(WorldPoint point1, WorldPoint point2)
    {
        if (point1.getRegionX() != point2.getRegionX()) { return false; }
        else if (point1.getRegionY() != point2.getRegionY()) { return false; }
        else return point1.getPlane() == point2.getPlane();
    }

    public static List<GameObject> GetObjectsFromScene(Client client, Set<WorldPoint> objectsToFind)
    {
        List<GameObject> foundObjects = new ArrayList<>();
        if (objectsToFind.isEmpty()) { return foundObjects; }

        Tile[][] tiles = GetScene(client).getTiles()[0];
        for (int x = 0; x < Constants.SCENE_SIZE; x++)
        {
            for (int y = 0; y < Constants.SCENE_SIZE; y++)
            {
                Tile tile = tiles[x][y];
                if (tile == null) { continue; }

                for (GameObject gameObject : tile.getGameObjects())
                {
                    if (gameObject == null) { continue; }

                    if (objectsToFind.contains(gameObject.getWorldLocation()) && DataFinder.GetTreeID(gameObject.getId()) != -1)
                    {
                        foundObjects.add(gameObject);
                    }
                }
            }
        }
        return foundObjects;
    }

    public static TileObject GetTreeTileObject(Client client, int x, int y, int id)
    {
        if (DataFinder.GetTreeID(id) == -1) { return null; }

        Tile[][][] tiles = GetScene(client).getTiles();
        Tile tile = tiles[client.getTopLevelWorldView().getPlane()][x][y];
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
}
