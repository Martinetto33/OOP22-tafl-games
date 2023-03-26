package taflgames.model.memento.api;

import taflgames.common.code.Position;

/**
 * An interface modelling the behaviour that the Inner Class of the Piece implementation
 * should provide in order to save a snapshot of the current state of a Piece. Said Inner
 * Class should implement this interface.
 * <br>This interface is part of the pattern Memento.
 */
public interface PieceMemento {
    /**
     * This method should allow each instance of the Inner Classes reference their Outer
     * classes and call their "restore()" method, by passing themselves as a parameter [i.e. by passing "this"].
     * <br>This way, the Inner Classes themselves will be in charge of finding the Piece instance
     * that had generated them, simplifying the duty of the Caretaker and the other external
     * classes which shouldn't bother with these associations.
     */
    void restore();

    /**
     * Gets the saved number of lives.
     * @return the number of lives.
     */
    int getBackupCurrNumbOfLives();
    
    /**
     * Returns the backup {@link taflgames.common.code.Position}
     * @return the previously saved Position of this Piece.
     */
    Position getBackupPosition();

    /**
     * Returns wether this Piece was alive at the moment of the save.
     * @return true if the Piece was alive, false otherwise.
     */
    boolean backupIsAlive();
}
