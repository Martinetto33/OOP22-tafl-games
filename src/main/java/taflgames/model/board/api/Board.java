package taflgames.model.board.api;

import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.model.cell.api.Cell;
import taflgames.model.memento.api.BoardMemento;
import taflgames.model.pieces.api.Piece;

import java.util.Map;
import java.util.Optional;

import taflgames.common.Player;

/**
 * This interface models the board of the game
 * and has methods that allow to control the movement of a piece, 
 * the death of one and that in general control the execution of the game.
 */
public interface Board {

    /**
     * Verify if a certain position is allowed for a certain player to start it's movement.
     * @param start the starting position that must be controlled
     * @param player the player of wich starting position is controlled
     * @return true if the starting position is allowed, false if it's not allowed
     */
    boolean isStartingPointValid(Position start, Player player);

    /**
     * Verify if a a movement form a starting position to a final position is allowed for a specific player.
     * It checks if the player has a piece of it's team in the starting position, 
     * if he does then it checks if the destination is free, 
     * if it is the method check that on path between the starting position 
     * and the final position there are no obstacles. 
     * If the final postion is not free the method check if the piece is a swapper 
     * and if it can swap with the piece that postion.
     * @param start the starting position.
     * @param dest the final destination to reach.
     * @param player the player whose movement is being checked.
     * @return true if the movement is allowed, false otherwise.
     */
    boolean isDestinationValid(Position start, Position dest, Player player);

    /**
     * Update the Position of a piece thanks to method {@link #movePlaceholder(Position, Position, Player)}
     * and notify the cell the piece moved to and the ones adjacent to it that the piece was moved.
     * @param oldPos the old Position that must be updated.
     * @param newPos the new Position to which the old one is updated.
     * @param currentPlayer the player in turn.
     */
    void updatePiecePos(Position oldPos, Position newPos, Player currentPlayer);

    /**
     * Update the Position of a piece.
     * @param oldPos the old Position that must be updated.
     * @param newPos the new Position to which the old one is updated.
     * @param currentPlayer the player in turn.
     */
    void movePlaceholder(Position oldPos, Position newPos, Player currentPlayer);

    /**
     * Calculate the furthest postion that can be reached from a stating position on a certain direction. 
     * @param startPos the starting position from which calculate the furthest reacheable position.
     * @param direction the direction along which to find the furthest reacheable position.
     * @return the furthest reacheable position.
     */
    Position getFurthestReachablePos(Position startPos, Vector direction);

    /**
     * This method must be called at the beginning of each turn.
     * Check if there's a tie between the two players.
     * @param playerInTurn the player that is playing in that specific turn.
     * @return true if it is a draw, false otherwise.
     */
    boolean isDraw(Player playerInTurn);

    /**
     * This method must must be called by Match before method {@link #isDraw(Player)}.
     * Check if the game is over and retunr the winning Player.
     * @return an Optional of the Player winning or an empty Optional 
     * if the game is still on and none of the Player has won yet.
     */
    Optional<Player> checkForWinningPlayer();

    /**
     * This method must be called by Match after method {@link #updatePiecePos}.
     * Verify the eating of a piece.
     */
    void eat();

    /**
     * Signals the resattable entities of the game (e.g. sliders) 
     * that the turn of the current player has ended.
     * @param turn the number of the turn.
     */
    void notifyTurnHasEnded(int turn);

    /**
     * Return the map of Position and Cell that that associate 
     * to each Position of the Board the type of Cell that is placed there.
     * @return the Map of Position and Cell. 
     */
    Map<Position, Cell> getMapCells();

    /**
     * Return the Map that associate to each Player it's own map of Position and Piece
     * in which each of the Player's Piece is associated to it's own Position.
     * @return the Map that associate to each Player a Map of Position and Piece.
     */
    Map<Player, Map<Position, Piece>> getMapPieces();

    BoardMemento save();

}
