package taflgames.view.scenecontrollers;

import java.util.Map;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.view.scenes.CellImageInfo;
import taflgames.view.scenes.PieceImageInfo;

/**
 * This interface extends a generic {@link BasicSceneController}
 * to add specific functions for a {@link taflgames.view.scenes.MatchScene}.
 */
public interface MatchSceneController extends BasicSceneController {

    /**
     * @return the mapping of each position to the type of cell
     * (and the cells components if present) at that position.
     */
    Map<Position, CellImageInfo> getCellsMapping();

    /**
     * @return the mapping of the positions 
     */
    Map<Position, PieceImageInfo> getPiecesMapping();

    /**
     * Updates the view with the information it needs to redraw correctly the
     * current state of the match.
     */
    void updateView();

    /**
     * @param pos the position selected as source
     * @return if the selected source is valid or not
     */
    boolean isSourceSelectionValid(Position pos);

    /**
     * Tells the controller to make the move from {@code source} to {@code destination}
     * if the move is legal.
     * @param source the coordinates of the selected source cell
     * @param destination the coordinates of the selected destination cell
     * @return true if the move is legal and then performed, false otherwise
     */
    boolean moveIfLegal(Position source, Position destination);

    /**
     * Asks to the controller of the application if the match is over.
     * @return true if the match is over, false otherwise
     */
    boolean isMatchOver();

    /**
     * Undoes the last move, if any.
     */
    void undo();

    /**
     * Used to confirm that the player in turn is sure
     * about his move and doesn't want to undo it.
     * @return true if the turn was correctly passed, false
     * if the current player still needs to make a move
     * before being able to pass the turn.
     */
    boolean passTurn();

    /**
     * Returns the {@link taflgames.common.Player} in turn.
     * @return the Player in turn.
     */
    Player getPlayerInTurn();
}
