package taflgames.model.pieces.code;

import java.util.HashSet;
import java.util.Set;

import taflgames.common.api.FactoryHitbox;
import taflgames.common.api.FactoryMoveSet;
import taflgames.common.api.Vector;
import taflgames.common.code.ImplFactoryHitbox;
import taflgames.common.code.ImplFactoryMoveset;
import taflgames.common.code.Position;
import taflgames.model.pieces.api.BehaviourTypeOfPiece;
import taflgames.model.pieces.api.Piece;
/**
* {@inheritDoc}.
*/
public abstract class AbstractBehaviourTypeOfPiece implements BehaviourTypeOfPiece {
    private String typeName;
    private int totalNumbOfLives;
    private Set<Vector> moveSet = new HashSet<>();
    private Set<Position> hitbox = new HashSet<>();
    private final FactoryHitbox factoryHitbox = new ImplFactoryHitbox();
    private final FactoryMoveSet factoryMoveSet = new ImplFactoryMoveset();
    /**
     * {@inheritDoc}.
     */
    public void setMoveSet(final Set<Vector> moveSet) {
        this.moveSet = moveSet;
    }
    /**
     * {@inheritDoc}.
     */
    public void setHitbox(final Set<Position> hitbox) {
        this.hitbox = hitbox;
    }
    /**
     * {@inheritDoc}.
     */
    @Override
    public abstract void generate();
    /**
     * {@inheritDoc}.
     */
    @Override
    public abstract Set<Position> generateHitbox();
    /**
     * {@inheritDoc}.
     */
    @Override
    public abstract Set<Vector> generateMoveSet();
    /**
     * {@inheritDoc}.
     */
    @Override
    public Set<Vector> getMoveSet() {
        if (this.moveSet.isEmpty()) {
            setMoveSet(this.generateMoveSet());
        }
        return this.moveSet;
    }
    /**
     * {@inheritDoc}.
     */
    @Override
    public Set<Position> getHitbox() {
       if (this.hitbox.isEmpty()) {
        setHitbox(this.generateHitbox());
       }
       return this.hitbox;
    }
    /**
     * {@inheritDoc}.
     */
    @Override
    public String getTypeOfPiece() {
       return this.typeName;
    }
    /**
     * {@inheritDoc}.
     */
    @Override
    public int getTotalNumbOfLives() {
        return this.totalNumbOfLives;
    }
    /**
     * {@inheritDoc}.
     */
    @Override
    public abstract boolean wasHit(Set<Piece> enemies, Position lastEnemyMoved) throws IllegalArgumentException;
    /**
     * {@inheritDoc}.
     */
    @Override
    public void setTotNumbOfLives(final int numbLives) {
        this.totalNumbOfLives = numbLives;
    }
    /**
     * {@inheritDoc}.
     */
    @Override
    public void setNameTypeOfPiece(final String name) {
        this.typeName = name;
    }
    /**
     * {@inheritDoc}.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((typeName == null) ? 0 : typeName.hashCode());
        result = prime * result + totalNumbOfLives;
        result = prime * result + ((moveSet == null) ? 0 : moveSet.hashCode());
        result = prime * result + ((hitbox == null) ? 0 : hitbox.hashCode());
        return result;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractBehaviourTypeOfPiece other = (AbstractBehaviourTypeOfPiece) obj;
        if (typeName == null) {
            if (other.typeName != null) {
                return false;
            }
        } else if (!typeName.equals(other.typeName)) {
            return false;
        }
        if (totalNumbOfLives != other.totalNumbOfLives) {
            return false;
        }
        if (moveSet == null) {
            if (other.moveSet != null) {
                return false;
            }
        } else if (!moveSet.equals(other.moveSet)) {
            return false;
        }
        if (hitbox == null) {
            if (other.hitbox != null) {
                return false;
            }
        } else if (!hitbox.equals(other.hitbox)) {
            return false;
        }
        return true;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "AbstractBehaviourTypeOfPiece [typeName=" + typeName + ", totalNumbOfLives=" + totalNumbOfLives
                + ", moveSet=" + moveSet + ", hitbox=" + hitbox + "]";
    }
     /**
     * {@inheritDoc}
     */
    @Override
    public FactoryHitbox getFacHitbox() {
        return this.factoryHitbox;
    }
     /**
     * {@inheritDoc}
     */
    @Override
    public FactoryMoveSet getFacMoveSet() {
        return this.factoryMoveSet;
    }
}
