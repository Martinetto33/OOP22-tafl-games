package taflgames.model.pieces.code;

import java.util.List;
import java.util.Set;

import taflgames.common.api.FactoryHitbox;
import taflgames.common.api.FactoryMoveSet;
import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.model.pieces.api.BehaviourTypeOfPiece;
import taflgames.model.pieces.api.Piece;

public abstract class AbstractBehaviourTypeOfPiece implements BehaviourTypeOfPiece{

    protected String typeName;
    protected int totalNumbOfLives;
    protected Set<Vector> moveSet;
    protected Set<Position> hitbox;

    public final FactoryHitbox factoryHitbox= new ImplFactoryHitbox();
    public final FactoryMoveSet factoryMoveSet= new ImplFactoryMoveset();

    
    public void setMoveSet(Set<Vector> moveSet) {
        this.moveSet = moveSet;
    }

    public void setHitbox(Set<Position> hitbox) {
        this.hitbox = hitbox;
    }

    @Override
    public abstract void generate();

    @Override
    public abstract Set<Position> generateHitbox();

    @Override
    public abstract Set<Vector> generateMoveSet();


    @Override
    public Set<Vector> getMoveSet() {
        if(this.moveSet.equals(null)) {
            this.moveSet = generateMoveSet();
        }
        return this.moveSet;
    }

    @Override
    public Set<Position> getHitbox() {
       if(this.hitbox.equals(null)) {
        this.hitbox=generateHitbox();
       }
       return this.hitbox;
    }

    @Override
    public String getTypeOfPiece() {
       return this.typeName;
    }

    @Override
    public int getTotalNumbOfLives() {
        return this.totalNumbOfLives;
    }

    @Override
    public abstract boolean wasHit(List<Piece> enemies, Position lastEnemyMoved);

    @Override
    public void setTotNumbOfLives(int numbLives) {
        this.totalNumbOfLives=numbLives;
    }

    @Override
    public void setNameTypeOfPiece(String name) {
        this.typeName=name;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractBehaviourTypeOfPiece other = (AbstractBehaviourTypeOfPiece) obj;
        if (typeName == null) {
            if (other.typeName != null)
                return false;
        } else if (!typeName.equals(other.typeName))
            return false;
        if (totalNumbOfLives != other.totalNumbOfLives)
            return false;
        if (moveSet == null) {
            if (other.moveSet != null)
                return false;
        } else if (!moveSet.equals(other.moveSet))
            return false;
        if (hitbox == null) {
            if (other.hitbox != null)
                return false;
        } else if (!hitbox.equals(other.hitbox))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AbstractBehaviourTypeOfPiece [typeName=" + typeName + ", totalNumbOfLives=" + totalNumbOfLives
                + ", moveSet=" + moveSet + ", hitbox=" + hitbox + "]";
    }

    
    
}
