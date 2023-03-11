package taflgames.model.pieces.api;

import java.util.Set;

import taflgames.common.api.Vector;
import taflgames.common.code.Position;

/**
 * This interface will model the behavior of a generic piece 
 * that can move on the board, indipendently from the specific type
 * of the piece
 */

public interface Piece {

    /**
     * this method informs whoever called it whether
     * this piece can switch positions with other pieces on the 
     * board
     * @return true if this piece 
     * can switch positions, false otherwise
     */
    boolean canSwitch();


    /**
     * this method returns the current position 
     * of the piece on the board
     * @return position of this piece
     */
    Position getCurrentPosition();

    /**
     * this method changes the position of the piece on the board
     * @param newPosition the position where the piece has to go
     */
    void setCurrentPosition(Position newPosition);

    /**
     * this method returns the move-set of this piece
     * @return the move-set (depending on specific implementations of piece) may 
     * contain two types of information: 
     * 
     *    1) the DIRECTIONS in which the piece can move from its starting position, 
     *    indipendently from the current state, the size or the shape of the board. 
     *    This means that the piece isn't aware of any obstacles in its way; this is why
     *    these "directions" must be further modified in order not to cause any bugs
     *    or 'illegal' moves
     * 
     *    2) VECTORS which point to SPECIFIC POSITIONS on the board. These
     *    vectors don't require to be modified in order for the piece to function properly.
     *    The piece called Swapper, for example, will contain the current positions of
     *    enemy pieces.
     */
    Set<Vector> whereToMove();

    /**
     * returns the hit-box of this piece: it's an area in which enemy piece can be killed
     * @return the hit-box relative to its current positions
     */
    Set<Position> whereToHit();
    /**
     * this method tells us the current number of lives of this piece
     * @return current number of lives
     */
    int getCurrNumbOfLives();

    /**
     * checks if the piece is alive or not
     * @return true means that the current number of lives is greater than 0, false otherwise
     */
    boolean isAlive();

    /**
     *  this method replaces the previous number of lives whith a new one
     * @param newNumOfLives
     */
    void setCurrNumbOfLives(int newNumOfLives);

    /**
     * decreases the current number of lives by 1 only if it's
     * greater than 0
     */
    void decrementCurrNumbOfLives();
    /**
     * this method is used when an enemy piece starts to eat other pieces and tells whether this piece was killed 
     * by that enemy piece
     * TO DO: per spiegare meglio riguarda gli appunti sulla mangiata di pedine
     * @return
     */
    boolean wasKilled();
    /**
     * saves the current state of the piece
     * @return the inner class PieceMemento
     */
    PieceMemento save();
    /**
     * restores the previous state of the piece
     * @param pm the inner class PieceMemento
     */
    void restore(PieceMemento pm);

    /**
     * creates a string that rappresents the piece as a whole
     * @return string that rappresents the piece
     */
    @Override
    String toString();

    
    String sendSignalMove();



}
