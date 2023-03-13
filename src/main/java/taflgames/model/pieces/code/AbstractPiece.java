package taflgames.model.pieces.code;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import taflgames.common.Player;
import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.common.code.VectorImpl;
import taflgames.model.pieces.api.BehaviourTypeOfPiece;
import taflgames.model.pieces.api.FactoryBehaviourTypeOfPiece;
import taflgames.model.pieces.api.Piece;
import taflgames.model.pieces.api.PieceMemento;
import taflgames.model.pieces.api.Team;


public abstract class AbstractPiece implements Piece{
    protected Position currentPosition;
    protected int currentNumbOfLives;
    protected BehaviourTypeOfPiece myType;
    protected Team myTeam;
    /**la factory sarà usata dal costruttore delle implementazioni per creare la type*/
    final protected FactoryBehaviourTypeOfPiece factory = new ImplFactoryBehaviourTypeOfPiece();

    public FactoryBehaviourTypeOfPiece getFactory() {
        return this.factory;
    }
    @Override
    public void setPlayer(Player p, Optional<String> s) {
    }

    /**
     * TO DO: mettere la documentazione di Alìn
     * per il resto ok (controlla poi gli errori)
     */
    public class PieceMementoImpl implements PieceMemento{

        private Position backupPosition;
        private int backupCurrentNumbOfLives;

        public PieceMementoImpl(){
            this.backupCurrentNumbOfLives=AbstractPiece.this.currentNumbOfLives;
            this.backupPosition=AbstractPiece.this.currentPosition;
        }

        @Override
        public void restore() {
            /**
             * da riguardare perchè forse causa problemi 
             * nelle implementazioni per il fatto
             * che è astratta 
             */
            AbstractPiece.this.restore(this);
        }

        public int getBackupCurrNumbOfLives() {
            return this.backupCurrentNumbOfLives;
        }
        public Position getBackupPosition() {
            return this.backupPosition;
        }
        public boolean backupIsAlive() {
            return this.backupCurrentNumbOfLives > 0;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canSwap() {
        return this.myType.getTypeOfPiece().equals("SWAPPER");
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Position getCurrentPosition() {
        return this.currentPosition;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setCurrentPosition(final Position newPosition) {
        this.currentPosition = newPosition;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Vector> whereToMove() { /**da rifare con la modifica di Alìn (so che non va bene)*/
        return new HashSet<>(this.myType.getMoveSet().stream()
                                        .map(v->new VectorImpl(this.currentPosition,v.getEndPos()))
                                        .toList());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Position> whereToHit() {
       
        return new HashSet<>(this.myType.getHitbox().stream()
                                .map(p->new Position(p.getX()+this.currentPosition.getX(),p.getY()+this.currentPosition.getY()))
                                .toList());

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrNumbOfLives() {
       return this.currentNumbOfLives;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() {
        return this.currentNumbOfLives > 0;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setCurrNumbOfLives(final int newNumOfLives) {
        this.currentNumbOfLives = newNumOfLives;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void decrementCurrNumbOfLives() {
       if(this.currentNumbOfLives > 0) {
        this.currentNumbOfLives=this.currentNumbOfLives-1;
       }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public PieceMemento save() {
        return this.new PieceMementoImpl();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void restore(final PieceMementoImpl pm) {
        this.currentNumbOfLives = pm.getBackupCurrNumbOfLives();
        this.currentPosition = pm.getBackupPosition();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String sendSignalMove() {
        return new StringBuilder().append(this.myType.getTypeOfPiece())
                                    .append("_MOVE")
                                    .toString();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Player getPlayer() {
       return this.myTeam.getPlayerTeam();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getNameOfPlayer() {
        return (!this.myTeam.getNameOfUserPlayer().isPresent()) ? "" : this.myTeam.getNameOfUserPlayer().get();
    }

    @Override
    public String toString() {
        return new StringBuilder().append(this.myType.getTypeOfPiece())
                                    .append(" in ")
                                    .append(currentPosition)
                                    .append(" with ")
                                    .append(currentNumbOfLives)
                                    .append("/")
                                    .append(this.myType.getTotalNumbOfLives())
                                    .append(" lives ")
                                    .append(this.getPlayer())
                                    .append(" name:")
                                    .append(this.getNameOfPlayer())
                                    .append(" hitbox: ")
                                    .append(this.myType.getHitbox())
                                    .append(" ")
                                    .append("moveset: ")
                                    .append(this.myType.getMoveSet())
                                    .toString();
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((currentPosition == null) ? 0 : currentPosition.hashCode());
        result = prime * result + currentNumbOfLives;
        result = prime * result + ((myType == null) ? 0 : myType.hashCode());
        result = prime * result + ((myTeam == null) ? 0 : myTeam.hashCode());
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
        AbstractPiece other = (AbstractPiece) obj;
        if (currentPosition == null) {
            if (other.currentPosition != null)
                return false;
        } else if (!currentPosition.equals(other.currentPosition))
            return false;
        if (currentNumbOfLives != other.currentNumbOfLives)
            return false;
        if (myType == null) {
            if (other.myType != null)
                return false;
        } else if (!myType.equals(other.myType))
            return false;
        if (myTeam == null) {
            if (other.myTeam != null)
                return false;
        } else if (!myTeam.equals(other.myTeam))
            return false;
        return true;
    }
    @Override
    public boolean wasKilled(List<Piece> enemies, Position lastEnemyMoved) {
        if(this.myType.wasHit(enemies,lastEnemyMoved)) {
            this.decrementCurrNumbOfLives();
            return this.isAlive();
        }

        return false;
        
    }

    
    
}