package taflgames.model.pieces.code;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.model.pieces.api.BehaviourTypeOfPiece;
import taflgames.model.pieces.api.FactoryBehaviourTypeOfPiece;
import taflgames.model.pieces.api.Piece;

public class ImplFactoryBehaviourTypeOfPiece implements FactoryBehaviourTypeOfPiece {
    final private int basicNumbOfLives=1;
    final private int shieldNumbOfLives=2;
    Set<Position> temp = new HashSet<>();
    @Override
    public BehaviourTypeOfPiece createQueenBehaviour() {
        /*DA RICONTROLLARE */
        return new AbstractBehaviourTypeOfPiece() {

            @Override
            public Set<Position> generateHitbox() {
               return this.factoryHitbox.createBasicHitbox();
            }

            @Override
            public Set<Vector> generateMoveSet() {
                return this.factoryMoveSet.createBasicMoveSet();
            }

            @Override
            public boolean wasHit(List<Piece> enemies, Position lastEnemyMoved) {
                if(enemies.size()<2) {
                    return false;
                }
                for (Piece p : enemies) {
                    if(p.getCurrentPosition().getX()==lastEnemyMoved.getX() ^ p.getCurrentPosition().getY()==lastEnemyMoved.getY()){
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void generate() {
                this.setHitbox(this.generateHitbox());
                this.setMoveSet(this.generateMoveSet());
                this.setNameTypeOfPiece("QUEEN");
                this.setTotNumbOfLives(basicNumbOfLives);
            }

            
        };
    }

    @Override
    public BehaviourTypeOfPiece createArcherBehaviour() {
        return new AbstractBehaviourTypeOfPiece() {
            
            @Override
            public Set<Position> generateHitbox() {
                // TODO Auto-generated method stub
                final int maxRange = 3;
                return this.factoryHitbox.createArcherHitbox(maxRange);
            }

            @Override
            public Set<Vector> generateMoveSet() {
                return this.factoryMoveSet.createBasicMoveSet();
            }

            @Override
            public boolean wasHit(List<Piece> enemies, Position lastEnemyMoved) {
                if(enemies.size()<2) {
                    return false;
                }
                for (Piece p : enemies) {
                    if(p.getCurrentPosition().getX()==lastEnemyMoved.getX() ^ p.getCurrentPosition().getY()==lastEnemyMoved.getY()){
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void generate() {
                this.setHitbox(this.generateHitbox());
                this.setMoveSet(this.generateMoveSet());
                this.setNameTypeOfPiece("ARCHER");
                this.setTotNumbOfLives(basicNumbOfLives);
            }
            
        };
    }

    @Override
    public BehaviourTypeOfPiece createKingBehaviour() {
        return new AbstractBehaviourTypeOfPiece() {

            @Override
            public Set<Position> generateHitbox() {
                return this.factoryHitbox.createBasicHitbox();
            }

            @Override
            public Set<Vector> generateMoveSet() {
                return this.factoryMoveSet.createBasicMoveSet();
            }

            @Override
            public boolean wasHit(List<Piece> enemies, Position lastEnemyMoved) {
                if(enemies.size()<4) {
                    return false;
                }
                return false;
            }

            @Override
            public void generate() {
                this.setHitbox(this.generateHitbox());
                this.setMoveSet(this.generateMoveSet());
                this.setNameTypeOfPiece("KING");
                this.setTotNumbOfLives(basicNumbOfLives);
            }

        };
    }

    @Override
    public BehaviourTypeOfPiece createShieldBehaviour() {
       return new AbstractBehaviourTypeOfPiece() {

        @Override
        public Set<Position> generateHitbox() {
            return this.factoryHitbox.createBasicHitbox();
        }

        @Override
        public Set<Vector> generateMoveSet() {
            return this.factoryMoveSet.createBasicMoveSet();
        }

        @Override
        public boolean wasHit(List<Piece> enemies, Position lastEnemyMoved) {
            if(enemies.size()<2) {
                return false;
            }
            for (Piece p : enemies) {
                if(p.getCurrentPosition().getX()==lastEnemyMoved.getX() ^ p.getCurrentPosition().getY()==lastEnemyMoved.getY()){
                    return true;
                }
            }
            return false;
        }

        @Override
        public void generate() {
            this.setHitbox(this.generateHitbox());
            this.setMoveSet(this.generateMoveSet());
            this.setNameTypeOfPiece("SHIELD");
            this.setTotNumbOfLives(shieldNumbOfLives);
        }
        
       };
    }

    @Override
    public BehaviourTypeOfPiece createSwapperBehaviour() {
       return new AbstractBehaviourTypeOfPiece() {

        @Override
        public Set<Position> generateHitbox() {
            return this.factoryHitbox.createBasicHitbox();
        }

        @Override
        public Set<Vector> generateMoveSet() {
            return this.factoryMoveSet.createSwapperMoveSet(temp);
        }

        @Override
        public boolean wasHit(List<Piece> enemies, Position lastEnemyMoved) {
            if(enemies.size()<2) {
                return false;
            }
            for (Piece p : enemies) {
                if(p.getCurrentPosition().getX()==lastEnemyMoved.getX() ^ p.getCurrentPosition().getY()==lastEnemyMoved.getY()){
                    return true;
                }
            }
            return false;
        }

        @Override
        public void generate() {
            this.setHitbox(this.generateHitbox());
            this.setMoveSet(this.generateMoveSet());
            this.setNameTypeOfPiece("SWAPPER");
            this.setTotNumbOfLives(basicNumbOfLives);
        }
        
       };
    }

    @Override
    public BehaviourTypeOfPiece createBasicPieceBehaviour() {
        return new AbstractBehaviourTypeOfPiece() {

            @Override
            public Set<Position> generateHitbox() {
                return this.factoryHitbox.createBasicHitbox();
            }

            @Override
            public Set<Vector> generateMoveSet() {
                return this.factoryMoveSet.createBasicMoveSet();
            }

            @Override
            public boolean wasHit(List<Piece> enemies, Position lastEnemyMoved) {
                if(enemies.size()<2) {
                    return false;
                }
                for (Piece p : enemies) {
                    if(p.getCurrentPosition().getX()==lastEnemyMoved.getX() ^ p.getCurrentPosition().getY()==lastEnemyMoved.getY()){
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void generate() {
                this.setHitbox(this.generateHitbox());
                this.setMoveSet(this.generateMoveSet());
                this.setNameTypeOfPiece("BASIC_PIECE");
                this.setTotNumbOfLives(basicNumbOfLives);
            }
            
        };
    }

}
