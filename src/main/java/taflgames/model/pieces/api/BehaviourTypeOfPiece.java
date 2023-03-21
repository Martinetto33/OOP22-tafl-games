package taflgames.model.pieces.api;

import java.util.Set;

import taflgames.common.api.FactoryHitbox;
import taflgames.common.api.FactoryMoveSet;
import taflgames.common.api.Vector;
import taflgames.common.code.Position;
/**
 * this interface rappersents the type and thus
 * the behaviour of the piece, which include:
 * -total number of lives 
 * -hitbox
 * -moveset
 * -name of the piece's type
 * -conditions of piece's death (particularly relevant
 *  for the king and shield).
 */
public interface BehaviourTypeOfPiece {
    /**
     * this method creates the hit-box of this piece,
     * indipendently from the current position of said piece
     * (the default position is (0,0)).
     * @return hitbox of piece.
     */

    Set<Position> generateHitbox();
    /**
     * this method creates the move-set of this piece, 
     * indipendently from the current position of said piece
     * (the default position is (0,0)).
     * @return moveSet of piece.
     */
    Set<Vector> generateMoveSet();
    /**
     * this method is used to initialized the behaviour of a piece
     * and it must be used right after creating the piece on a different line.
     */
    void generate();
    /**
     * this method is used to checks that numbLives is correct and
     * and then change the total number of lives. 
     * 
     * @param numbLives
     */
    void setTotNumbOfLives(int numbLives);
    /**
     * this method is used to set the
     * name(QUEEN, KING, etc...), also known by the type of piece
     * rappresented its behaviour.
     * @param name
     */
    void setNameTypeOfPiece(String name);
    /**
     * this method is used to set the 
     * move-set used by the piece's behaviour.
     * @param moveSet ,which should be the output of generateMoveSet.
     */
    void setMoveSet(Set<Vector> moveSet);
    /**
     * this method is used to set the 
     * hit-box used by the piece's behaviour.
     * @param hitbox ,which should be the output of generateHitbox.
     */
    void setHitbox(Set<Position> hitbox);
    /**
     * 
     * @return move-set.
     */
    Set<Vector> getMoveSet();
    /**
     * 
     * @return hit-box.
     */
    Set<Position> getHitbox();

    /**
     * returns the type of this piece.
     * @return type of this piece.
     */
    String getTypeOfPiece();

    /**
     * returns the total amount of lives that this type of
     * can have.
     * @return number of lives.
     */
    int getTotalNumbOfLives();
    /**
     * this method is used to check if there are the conditions
     * for the piece to get hit. These conditions may change depending
     * on the type of piece, thus they have to be determined by the
     *  implementations.
     * @param enemies 
     * @param lastEnemyMoved
     * @return true if it was hit, false otherwise.
     */
    boolean wasHit(Set<Piece> enemies, Position lastEnemyMoved);
    /**
    * @return this piece's factoryHitbox
    */
    FactoryHitbox getFacHitbox();
    /**
    * @return this piece's factoryMoveSet
    */
    FactoryMoveSet getFacMoveSet();
}
