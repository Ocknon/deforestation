package com.deforestation;

import net.runelite.api.ObjectID;

import static net.runelite.api.AnimationID.*;

public class DeforestationDataLookups
{
    public static int GetTreeID(int gameObjectID)
    {
        // I hope this is every non-farming, cut-able tree
        switch (gameObjectID)
        {
            case ObjectID.TREE:
            case ObjectID.TREE_1278:
            case ObjectID.TREE_1277:
            case ObjectID.TREE_1280:
            case ObjectID.TREE_3879:
            case ObjectID.TREE_3881:
            case ObjectID.TREE_3882:
            case ObjectID.TREE_3883:
            case ObjectID.TREE_36677:
            case ObjectID.TREE_36679:
            case ObjectID.TREE_36672:
            case ObjectID.TREE_36674:
            case ObjectID.TREE_40750:
            case ObjectID.TREE_40752:
            case ObjectID.TREE_1330:
            case ObjectID.TREE_1331:
            case ObjectID.TREE_1332:
            case ObjectID.TREE_1279:
            case ObjectID.TREE_42393:
            case ObjectID.TREE_42832:
            case 1290:
            case 1285:
            case 1286:
            case 1289:
            case 1282:
            case 1284:
            case 1365:
            case 1383:
            case 1291:
            case 3648:
            case 1318:
            case 1319:
            case 2091:
            case 2092:
            case 4818:
            case 4820:
            case 2023:
            case 10820:
            case 42395:
            case 42831:
            case 10819:
            case 10829:
            case 10831:
            case 10833:
            case 9036:
            case 36686:
            case 40758:
            case 27499:
            case 10832:
            case 36681:
            case 40754:
            case 10821:
            case 10830:
            case 9034:
            case 36688:
            case 40760:
            case 3037:
            case 10822:
            case 36683:
            case 40756:
            case 42391:
            case 10834:
            case 9731:
            case 9730:
            case 9733:
            case 9732:
            case 9734:
            case 37969:
            case 29668: // redwood
            case 29670:
            case 29669:
            case 29671:
            case 29681:
            case 29682:
            case 30854: // non-logs
            case 30852:
            case 1384:
            case 37989: // quests
            case 1292:
            case 16604:
            case 18137:
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
