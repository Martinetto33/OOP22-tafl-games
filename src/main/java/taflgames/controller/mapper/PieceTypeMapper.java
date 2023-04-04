package taflgames.controller.mapper;

import taflgames.common.Player;
import taflgames.controller.entitystate.PieceState;
import taflgames.view.scenes.PieceImageInfo;

public class PieceTypeMapper implements PieceImageMapper {

    @Override
    public PieceImageInfo mapToImage(PieceState state) {
        final String name = state.getName();
        final Player player = state.getPlayer();
        switch(name) {
            case "ARCHER":
                if (player.equals(Player.ATTACKER)) {
                    return new PieceImageInfo("ARCHER_ATTACKER", player);
                }
                return new PieceImageInfo("ARCHER_DEFENDER", player);
            case "BASIC_PIECE":
                if (player.equals(Player.ATTACKER)) {
                    return new PieceImageInfo("BASIC_PIECE_ATTACKER", player);
                }
                return new PieceImageInfo("BASIC_PIECE_DEFENDER", player);
            case "KING":
                return new PieceImageInfo("KING", player);
            case "QUEEN":
                if (player.equals(Player.ATTACKER)) {
                    return new PieceImageInfo("QUEEN_ATTACKER", player);
                }
                return new PieceImageInfo("QUEEN_DEFENDER", player);
            case "SHIELD":
                if (player.equals(Player.ATTACKER)) {
                    return new PieceImageInfo("SHIELD_ATTACKER", player);
                }
                return new PieceImageInfo("SHIELD_DEFENDER", player);
            case "SWAPPER":
                if (player.equals(Player.ATTACKER)) {
                    return new PieceImageInfo("SWAPPER_ATTACKER", player);
                }
                return new PieceImageInfo("SWAPPER_DEFENDER", player);
            default:
                return null;
        }
    }

}
