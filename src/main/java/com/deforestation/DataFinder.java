package com.deforestation;

import net.runelite.api.events.MenuOptionClicked;

import static net.runelite.api.AnimationID.*;

public class DataFinder
{
    public static boolean IsWoodcuttingAnimation(int animationId)
    {
        switch (animationId)
        {
            case WOODCUTTING_BRONZE:
            case WOODCUTTING_IRON:
            case WOODCUTTING_STEEL:
            case WOODCUTTING_BLACK:
            case WOODCUTTING_MITHRIL:
            case WOODCUTTING_ADAMANT:
            case WOODCUTTING_RUNE:
            case WOODCUTTING_GILDED:
            case WOODCUTTING_DRAGON:
            case WOODCUTTING_DRAGON_OR:
            case WOODCUTTING_INFERNAL:
            case WOODCUTTING_3A_AXE:
            case WOODCUTTING_CRYSTAL:
            case WOODCUTTING_TRAILBLAZER:
            case WOODCUTTING_2H_BRONZE:
            case WOODCUTTING_2H_IRON:
            case WOODCUTTING_2H_STEEL:
            case WOODCUTTING_2H_BLACK:
            case WOODCUTTING_2H_MITHRIL:
            case WOODCUTTING_2H_ADAMANT:
            case WOODCUTTING_2H_RUNE:
            case WOODCUTTING_2H_DRAGON:
            case WOODCUTTING_2H_CRYSTAL:
            case WOODCUTTING_2H_CRYSTAL_INACTIVE:
            case WOODCUTTING_2H_3A:
                return true;
        }
        return false;
    }

    public static int GetTreeID(int gameObjectId)
    {
        switch (gameObjectId)
        {
            // Normal Logs
            case 1276: // Tree 1/2
            case 1278: // Tree 2/2
            case 1277: // Young tree 1/2
            case 1280: // Young tree 2/2
            case 3879: // Tirannwn tree 1/4
            case 3881: // Tirannwn tree 2/4
            case 3882: // Tirannwn tree 3/4
            case 3883: // Tirannwn tree 4/4
            case 36677: // Prifddinas tree 1/2
            case 36679: // Prifddinas tree 2/2
            case 36672: // Crystal tree 1/2
            case 36674: // Crystal tree 2/2
            case 40750: // Isle of Souls tree 1/2
            case 40752: // Isle of Souls tree 2/2
            case 1330: // Snow tree 1/3
            case 1331: // Snow tree 2/3
            case 1332: // Snow tree 3/3
            case 1279: // Great Kourend tree
            case 42393: // Shayzien tree
            case 42832: // The Node tree
            case 1286: // Dead tree 1/4
            case 1289: // Dead tree 2/4
            case 1282: // Dead tree 3/4 - BIG 1/2
            case 1283: // Dead tree 3/4 - BIG 2/2
            case 1284: // Dead tree 4/4
            case 1290: // Dead sandy tree
            case 1285: // Dead weathered tree
            case 1365: // Dead swampy tree
            case 1383: // Dead old tree
            case 1291: // Dead snowy tree
            case 3648: // Dying tree
            case 1318: // Evergreen tree 1/4
            case 1319: // Evergreen tree 2/4
            case 2091: // Evergreen tree 3/4
            case 2092: // Evergreen tree 4/4
            case 4818: // Jungle tree 1/2
            case 4820: // Jungle tree 2/2
            case 2887: // Kharazi Jungle tree

            //Other logs
            case 2023: // Achey tree
            case 10820: // Oak tree
            case 42395: // Shayzien Oak tree
            case 42831: // The Node Oak tree
            case 10819: // Willow tree 1/4
            case 10829: // Willow tree 2/4
            case 10831: // Willow tree 3/4
            case 10833: // Willow tree 4/4
            case 9036: // Teak tree
            case 36686: // Prifddinas Teak tree
            case 40758: // Isle of Souls Teak tree
            case 27499: // Mature Juniper tree
            case 10832: // Maple tree
            case 36681: // Gwenith Maple tree
            case 40754: // Isle of Souls Maple tree
            case 10821: // Hollow tree 1/2
            case 10830: // Hollow tree 2/2
            case 9034: // Mahogany tree
            case 36688: // Prifddinas Mahogany tree
            case 40760: // Isle of Souls Mahogany tree
            case 3037: // Arctic Pine tree
            case 10822: // Yew tree
            case 36683: // Prifddinas Yew tree
            case 40756: // Isle of Souls Yew tree
            case 42391: // Shayzien Yew tree
            case 10834: // Magic tree

            // Redwood
            case 29668: // Choppable Part 1/2
            case 29670: // Choppable Part 2/2

            // Non-log trees
            case 30854: // Burnt tree 1/2
            case 30852: // Burnt tree 2/2
            case 1384: // Dead tree
            case 30012: // Meat tree
            case 29763: // Sapling
            case 15970: // Scrapey Tree
            case 30602: // Sulliuscep

            // Quest trees
            case 37989: // Blisterwood
            case 1292: // Dramen
            case 16604: // Dream
            case 18137: // Windswept

            // Other
            case 9731: // Tutorial Island tree 1/4
            case 9730: // Tutorial Island tree 2/4
            case 9733: // Tutorial Island tree 3/4
            case 9732: // Tutorial Island tree 4/4
            case 9734: // Tutorial Island Oak 1/2
            case 37969: // Tutorial Island Oak 2/2
                return gameObjectId;
        }
        return -1;
    }

    public static int GetStumpID (int gameObjectId)
    {
        // 27061 is Mac's tree stump which needs to be climbed over
        // 5905 is Mountain Daughter tree stump which needs to be climbed over
        switch (gameObjectId)
        {
            case 1342: // Tree
            case 1355: // Evergreen
            case 36673: // Crystal tree 1/2
            case 36675: // Crystal tree 2/2
            case 1351: // Dead tree 1/4
            case 1353: // Dead tree 2/4
            case 1347: // Dead tree 3/4
            case 6212: // Dead tree 4/4
            case 1358: // Dead old tree
            case 1354: // Dead sandy tree
            case 23054: // Dead snowy tree
            case 1352: // Dead swampy tree
            case 1349: // Dead weathered tree
            case 2310: // Dead hollow tree 1/2
            case 4061: // Dead hollow tree 2/2
            case 40751: // Isle of Souls tree 1/2
            case 40753: // Isle of Souls tree 2/2
            case 2891: // Jungle tree
            case 9035: // Mahogany tree
            case 36689: // Mahogany Prifddinas tree
            case 40761: // Mahogany Isle of Souls tree
            case 1356: // Oak tree
            case 42396: // Oak Shayzien tree
            case 36678: // Prifddinas 1/2
            case 36680: // Prifddinas 2/2
            case 42394: // Shayzien
            case 9037: // Teak tree
            case 36687: // Teak Prifddinas tree
            case 40759: // Teak Isle of Souls tree
            case 3880: // Tirannwn tree 1/2
            case 3884: // Tirannwn tree 2/2
            case 9714: // Yew tree
            case 36684: // Yew Prifddinas tree
            case 40757: // Yew Isle of Souls tree
            case 42392: // Yew Shayzien tree
            case 1343: // Young tree
            case 21274: // Arctic Pine tree
            case 9713: // Magic tree
            case 9712: // Maple tree
            case 36682: // Maple Gwenith tree
            case 40755: // Maple Isle of Souls tree
            case 9471: // Willow tree 1/2
            case 9711: // Willow tree 2/2
            case 30856: // Burnt tree
            case 3649: // Dying tree
            case 27500: // Juniper tree
            case 3371: // Achey tree
            case 4819: // Jungle tree 1/2
            case 4821: // Jungle tree 2/2
                return gameObjectId;
        }
        return -1;
    }

    public static int GetModelIdFromStumpId (int stumpId)
    {
        // https://www.osrsbox.com/tools/model-search/
        switch (stumpId)
        {
            case 1342: // Tree
            case 42394: // Shayzien
            case 1343: // Young tree
            case 2891: // Jungle tree
                return 1695;
            case 36673: // Crystal tree 1/2
                return 37881;
            case 36675: // Crystal tree 2/2
                return 37920;
            case 1351: // Dead tree 1/4
            case 1353: // Dead tree 2/4
            case 1352: // Dead swampy tree
                return 1685;
            case 1347: // Dead tree 3/4
            case 6212: // Dead tree 4/4
            case 1349: // Dead weathered tree
                return 1629;
            case 1358: // Dead old tree
                return 1596;
            case 1354: // Dead sandy tree
                return 1635;
            case 23054: // Dead snowy tree
                return 23909;
            case 2310: // Dead hollow tree 1/2
                return 4147;
            case 4061: // Dead hollow tree 2/2
                return 4145;
            case 40751: // Isle of Souls tree 1/2
                return 41427;
            case 40753: // Isle of Souls tree 2/2
                return 41434;
            case 9035: // Mahogany tree
                return 8901;
            case 36689: // Mahogany Prifddinas tree
            case 40761: // Mahogany Isle of Souls tree
                return 37904;
            case 1356: // Oak tree
            case 42396: // Oak Shayzien tree
                return 8039;
            case 36678: // Prifddinas 1/2
                return 37890;
            case 36680: // Prifddinas 2/2
                return 37921;
            case 9037: // Teak tree
                return 8903;
            case 36687: // Teak Prifddinas tree
            case 40759: // Teak Isle of Souls tree
                return 37903;
            case 3880: // Tirannwn tree 1/2
            case 3884: // Tirannwn tree 2/2
                return 3945;
            case 9714: // Yew tree
            case 42392: // Yew Shayzien tree
                return 8162;
            case 36684: // Yew Prifddinas tree
            case 40757: // Yew Isle of Souls tree
                return 37876;
            case 21274: // Arctic Pine tree
                return 16292;
            case 9713: // Magic tree
                return 32357;
            case 9712: // Maple tree
                return 8025;
            case 36682: // Maple Gwenith tree
                return 37834;
            case 40755: // Maple Isle of Souls tree
                return 41431;
            case 9471: // Willow tree 1/2
                return 5529;
            case 9711: // Willow tree 2/2
                return 8140;
            case 30856: // Burnt tree
                return 33655;
            case 3649: // Dying tree
                return 25026;
            case 27500: // Juniper tree
                return 30093;
            case 3371: // Achey tree
                return 3451;
            case 4819: // Jungle tree 1/2
            case 4821: // Jungle tree 2/2
                return 4837;
            case 1355: // Evergreen tree
                return 1652;
        }
        return -1;
    }

    // Large is a 2x2 tile stump, needs to be centered on the 4 tiles
    public static boolean IsLargeStump (int stumpId)
    {
        switch (stumpId)
        {
            case 36675: // Huge crystal
            case 1347: // Big dead 1/2
            case 6212: // Big dead 2/2
            case 1342: // Average tree with weird sticks
            case 40753: // Isle of Souls
            case 36680: // Prifddinas
            case 30855: // Dead tree
                return true;
        }
        return false;
    }

    public static boolean IsInteractionClick (MenuOptionClicked menuOptionClicked)
    {
        switch (menuOptionClicked.getMenuAction())
        {
            case WIDGET_TARGET_ON_GAME_OBJECT:
            case GAME_OBJECT_FIRST_OPTION:
            case GAME_OBJECT_SECOND_OPTION:
            case GAME_OBJECT_THIRD_OPTION:
            case GAME_OBJECT_FOURTH_OPTION:
            case GAME_OBJECT_FIFTH_OPTION:
                return true;
        }
        return false;
    }

    public static boolean IsResetClick (MenuOptionClicked menuOptionClicked)
    {
        switch (menuOptionClicked.getMenuAction())
        {
            case WALK:
            case WIDGET_TARGET_ON_WIDGET:
            case WIDGET_TARGET_ON_GROUND_ITEM:
            case WIDGET_TARGET_ON_PLAYER:
            case GROUND_ITEM_FIRST_OPTION:
            case GROUND_ITEM_SECOND_OPTION:
            case GROUND_ITEM_THIRD_OPTION:
            case GROUND_ITEM_FOURTH_OPTION:
            case GROUND_ITEM_FIFTH_OPTION:
                return true;
        }
        return false;
    }
}
