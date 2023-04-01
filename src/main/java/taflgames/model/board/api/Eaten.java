package taflgames.model.board.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.cell.api.Cell;
import taflgames.model.pieces.api.Piece;

/**
 * This interface handles the eating of pieces during the game.
 * The methods of this inteface allows to determine the hitbox of a piece,
 * the enemy or enemies that a given piece threat, 
 * the allies that threaten those same enemies
 * and then verify if the enemies were eaten and notify them of their death.
 */
public interface Eaten {
    /**
     * Delete from the Set of Position that represent the hitbox of the given pice 
     * all the positions that aren't on the board, all the position that are occupied 
     * by pices of the same player of the piece that wants to eat
     * and all the positions that come after that.
     * @param currentPiece the Piece of whose hitbox is being modified.
     * @param pieces the Map that associate to each Player it's own Map of Piece and Position.
     * @param cells the Map of Position and Cell that that associate
     * to each Position of the Board the type of Cell that is placed there.
     * @param size the size of the board.
     * @return the modified hitbox.
     */
    Set<Position> trimHitbox(
        Piece currentPiece,
        Map<Player, Map<Position, Piece>> pieces,
        Map<Position, Cell> cells, 
        int size
    );

    /**
     * Finds the enemies that are in the hitbox of the piece that wants to eat.
     * @param hitbox the hitbox of the given piece that wants to eat.
     * @param pieces the Map that associate to each Player it's own map of Piece and Position.
     * @param piece the Piece that wants to eat.
     * @return a List of Piece that represent the enemies that are in the hitbox of the Piece that wants to eat.
     */
    List<Piece> getThreatenedPos(
        Set<Position> hitbox,
        Map<Player, Map<Position, Piece>> pieces,
        Piece piece
    );

    /**
     * This method will create a map that associates each menaced enemy to all the allies
     * that are threatening it at the moment, so as to call "wasKilled" method on each
     * of them.
     * @param enemies the pieces that represent the enemies that are in the hitbox of the piece that wants to eat
     * @param pieces the Map that associate to each Player it's own map of Piece and Position.
     * @param lastMovedPiece the last moved Piece, that is the one trying to eat.
     * @param cells
     * @param size
     * @return a Map that associates each menaced enemy to all the allies that are threatening it.
     */
    Map<Piece, Set<Piece>> checkAllies(
        List<Piece> enemies,
        Map<Player, Map<Position, Piece>> pieces,
        Piece lastMovedPiece, 
        Map<Position, Cell> cells,
        int size
    );

    /**
     * Get all the enemies that have two or more pieces threatening them, then
     * notify these enemies that the other player's pieces are trying to eat them.
     * @param enemiesAndAllies the Map that associates each menaced enemy to all the allies that are threatening it
     * @param lastMovedPiece the last moved Piece, that is the one trying to eat 
     * @param cells the Map of Position and Cell that that associate
     * to each Position of the Board the type of Cell that is placed there.
     * @param pieces the Map that associate to each Player it's own map of Piece and Position.
     * @param doTombsSpawn true if Tombs can spawn, false otherwise.
     */
    void notifyAllThreatened(
        Map<Piece, Set<Piece>> enemiesAndAllies,
        Piece lastMovedPiece,
        Map<Position, Cell> cells,
        Map<Player, Map<Position, Piece>> pieces,
        boolean doTombsSpawn
    );

}
