package taflgames.model.pieces.api;

import java.util.Set;

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
 *  for the king and shield)
 */

public interface BehaviourTypeOfPiece {
    /**
     * this method creates the hit-box of this piece,
     * indipendently from the current position of said piece
     * (the default position is (0,0))
     * @return hitbox of piece
     */

    Set<Position> generateHitbox();
    /**
     * this method creates the move-set of this piece, 
     * indipendently from the current position of said piece
     * (the default position is (0,0))
     * @return moveSet of piece
     */
    Set<Vector> generateMoveSet();

    void generate();

    void setTotNumbOfLives(int numbLives);

    void setNameTypeOfPiece(String name);

    void setMoveSet(Set<Vector> moveSet);

    void setHitbox(Set<Position> hitbox);

    Set<Vector> getMoveSet();

    Set<Position> getHitbox();

    /**
     * returns the type of this piece
     * @return type of this piece
     */
    String getTypeOfPiece();

    /**
     * returns the total amount of lives that this type of
     * can have
     * @return number of lives
     */
    int getTotalNumbOfLives();
    /**
     * this method is used to check if there are the conditions
     * for the piece to get hit. These conditions may change depending
     * on the type of piece, thus they have to be determined by the implementations
     * @return true if it was hit, false otherwise
     */
    boolean wasHit(Set<Piece> enemies, Position lastEnemyMoved) throws IllegalArgumentException;

}
