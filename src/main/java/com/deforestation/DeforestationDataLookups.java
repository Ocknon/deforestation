package com.deforestation;

import net.runelite.api.ObjectID;

import static net.runelite.api.AnimationID.*;

public class DeforestationDataLookups
{
    public static int GetTreeID(int gameObjectID)
    {
        switch (gameObjectID)
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
                return gameObjectID;
        }
        return -1;
    }

    public static boolean IsWoodcuttingAnimation(int animationID)
    {
        switch (animationID)
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
}
