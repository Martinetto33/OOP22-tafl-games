package taflgames.model.pieces.code;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.model.pieces.api.BehaviourTypeOfPiece;
import taflgames.model.pieces.api.FactoryBehaviourTypeOfPiece;
import taflgames.model.pieces.api.Piece;

/**
* {@inheritDoc}.
*/
public class ImplFactoryBehaviourTypeOfPiece implements FactoryBehaviourTypeOfPiece {
    private final int basicNumbOfLives = 1;
    private final int shieldNumbOfLives = 2;
    private Set<Position> temp = new HashSet<>();
    /**
     * This method (used by many types of pieces) tells whether this piece 
     * is about to get hit. In order to get hit it must be surrounded by at least two enemy pieces,
     * which are rappresented by the parameter {@link enemies}. Among these {@link enemies} there must be 
     * two pieces with the same coordinates on the board either on the x-axis or on the y-axis.
     * @param enemies rappresents the set of enemy pieces that are threatening to
     * hit this piece
     * @param lastEnemyMoved position of the enemy piece that moved last
     * @return whether the conditions for a "hit" were satisfied or not
     */
    private boolean areArgumentsValid(final Set<Piece> enemies, final Position lastEnemyMoved) {
        final Set<Piece> test = new HashSet<>(enemies.stream()
                .filter(t -> t.getCurrentPosition().equals(lastEnemyMoved))
                .collect(Collectors.toSet()));
        if (test.size() != 1) {
            return false;
        }
        return true;
    }
    /**
     * is may or may not be used.
     * @param me
     * @param enemies
     * @param lastEnemyMoved
     * @return true if valid, false otherwise
     */
    private boolean areMixedPieces(final Piece me, final Set<Piece> enemies, final Position lastEnemyMoved) {
        final Set<Piece> test = new HashSet<>(enemies.stream()
                .filter(t -> t.getPlayer().equals(me.getPlayer()))
                .collect(Collectors.toSet()));
        if (test.size() != enemies.size()) {
            return true;
        }
        return false;
    }
    /**
     * {@inheritDoc}.
     */
    public boolean basicWasHit(final Set<Piece> enemies, final Position lastEnemyMoved) throws IllegalArgumentException {
        final var e = Objects.requireNonNull(enemies);
        final var l = Objects.requireNonNull(lastEnemyMoved);
        if (!areArgumentsValid(enemies, lastEnemyMoved)) {
            throw new IllegalArgumentException("last enemy moved not present in enemies");
        }
        if (enemies.size() < 2) {
            return false;
        }
        for (Piece p : e) {
            if (p.getCurrentPosition().getX() == l.getX() ^ p.getCurrentPosition().getY() == l.getY()) {
                return true;
            }
        }
        return false;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public BehaviourTypeOfPiece createQueenBehaviour() {
        return new AbstractBehaviourTypeOfPiece() {
            /**
            * {@inheritDoc}.
            */
            @Override
            public Set<Position> generateHitbox() {
               return this.getFacHitbox().createBasicHitbox();
            }
            /**
            * {@inheritDoc}.
            */
            @Override
            public Set<Vector> generateMoveSet() {
                return this.getFacMoveSet().createBasicMoveSet();
            }
            /**
            * {@inheritDoc}.
            */
            @Override
            public boolean wasHit(final Set<Piece> enemies, final Position lastEnemyMoved) {
                return basicWasHit(enemies, lastEnemyMoved);
            }
            /**
            * {@inheritDoc}.
            */
            @Override
            public void generate() {
                this.setHitbox(this.generateHitbox());
                this.setMoveSet(this.generateMoveSet());
                this.setNameTypeOfPiece("QUEEN");
                this.setTotNumbOfLives(basicNumbOfLives);
            }
        };
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public BehaviourTypeOfPiece createArcherBehaviour() {
        return new AbstractBehaviourTypeOfPiece() {
            /**
            * {@inheritDoc}.
            */
            @Override
            public Set<Position> generateHitbox() {
                final int maxRange = 3;
                return this.getFacHitbox().createArcherHitbox(maxRange);
            }
            /**
            * {@inheritDoc}.
            */
            @Override
            public Set<Vector> generateMoveSet() {
                return this.getFacMoveSet().createBasicMoveSet();
            }
            /**
            * {@inheritDoc}.
            */
            @Override
            public void generate() {
                this.setHitbox(this.generateHitbox());
                this.setMoveSet(this.generateMoveSet());
                this.setNameTypeOfPiece("ARCHER");
                this.setTotNumbOfLives(basicNumbOfLives);
            }
            /**
            * {@inheritDoc}.
            */
            @Override
            public boolean wasHit(final Set<Piece> enemies, final Position lastEnemyMoved) {
                return basicWasHit(enemies, lastEnemyMoved);
            }
        };
    }
    /**
     * {@inheritDoc}.
     */
    @Override
    public BehaviourTypeOfPiece createKingBehaviour() {
        return new AbstractBehaviourTypeOfPiece() {
            /**
            * {@inheritDoc}.
            */
            @Override
            public Set<Position> generateHitbox() {
                return this.getFacHitbox().createBasicHitboxDistance(0);
            }
            /**
            * {@inheritDoc}.
            */
            @Override
            public Set<Vector> generateMoveSet() {
                return this.getFacMoveSet().createBasicMoveSet();
            }
            /**
            * {@inheritDoc}.
            */
            @Override
            public void generate() {
                this.setHitbox(this.generateHitbox());
                this.setMoveSet(this.generateMoveSet());
                this.setNameTypeOfPiece("KING");
                this.setTotNumbOfLives(basicNumbOfLives);
            }
            /**
            * {@inheritDoc}.
            */
            @Override
            public boolean wasHit(final Set<Piece> enemies, final Position lastEnemyMoved) {
                final var e = Objects.requireNonNull(enemies);
                Objects.requireNonNull(lastEnemyMoved);
                final Set<Piece> test = new HashSet<>(e.stream()
                                            .filter(t -> t.getCurrentPosition().equals(lastEnemyMoved))
                                            .collect(Collectors.toSet()));
                if (test.size() != 1) {
                    throw new IllegalArgumentException("lastEnemyMoved is not present in enemies");
                }
                return e.size() >= 4;
            }
        };
    }
    /**
    * {@inheritDoc}.
    */
    @Override
    public BehaviourTypeOfPiece createShieldBehaviour() {
       return new AbstractBehaviourTypeOfPiece() {
            /**
            * {@inheritDoc}.
            */
            @Override
            public Set<Position> generateHitbox() {
                return this.getFacHitbox().createBasicHitbox();
            }
            /**
            * {@inheritDoc}.
            */
            @Override
            public Set<Vector> generateMoveSet() {
                return this.getFacMoveSet().createBasicMoveSet();
            }
            /**
            * {@inheritDoc}.
            */
            @Override
            public void generate() {
                this.setHitbox(this.generateHitbox());
                this.setMoveSet(this.generateMoveSet());
                this.setNameTypeOfPiece("SHIELD");
                this.setTotNumbOfLives(shieldNumbOfLives);
            }
            /**
            * {@inheritDoc}.
            */
            @Override
            public boolean wasHit(final Set<Piece> enemies, final Position lastEnemyMoved) {
                return basicWasHit(enemies, lastEnemyMoved);
            }
        };
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public BehaviourTypeOfPiece createSwapperBehaviour() {
       return new AbstractBehaviourTypeOfPiece() {
        /**
        * {@inheritDoc}
        */
        @Override
        public Set<Position> generateHitbox() {
            return this.getFacHitbox().createBasicHitbox();
        }
        /**
        * {@inheritDoc}.
        */
        @Override
        public Set<Vector> generateMoveSet() {
            /*DEVI FARE QUI LA MODIFICA DI TEMP */
            temp.add(new Position(0, 2));
            temp.add(new Position(2, 1));
            return this.getFacMoveSet().createSwapperMoveSet(temp);
        }
        /**
        * {@inheritDoc}.
        */
        @Override
        public void generate() {
            this.setHitbox(this.generateHitbox());
            this.setMoveSet(this.generateMoveSet());
            this.setNameTypeOfPiece("SWAPPER");
            this.setTotNumbOfLives(basicNumbOfLives);
        }
        /**
        * {@inheritDoc}.
        */
        @Override
        public boolean wasHit(final Set<Piece> enemies, final Position lastEnemyMoved) {
            return basicWasHit(enemies, lastEnemyMoved);
        }
       };
    }
    /**
    * {@inheritDoc}.
    */
    @Override
    public BehaviourTypeOfPiece createBasicPieceBehaviour() {
        return new AbstractBehaviourTypeOfPiece() {
            /**
            * {@inheritDoc}.
            */
            @Override
            public Set<Position> generateHitbox() {
                return this.getFacHitbox().createBasicHitbox();
            }
            /**
            * {@inheritDoc}.
            */
            @Override
            public Set<Vector> generateMoveSet() {
                return this.getFacMoveSet().createBasicMoveSet();
            }
            /**
            * {@inheritDoc}.
            */
            @Override
            public void generate() {
                this.setHitbox(this.generateHitbox());
                this.setMoveSet(this.generateMoveSet());
                this.setNameTypeOfPiece("BASIC_PIECE");
                this.setTotNumbOfLives(basicNumbOfLives);
            }
            /**
            * {@inheritDoc}.
            */
            @Override
            public boolean wasHit(final Set<Piece> enemies, final Position lastEnemyMoved) {
                return basicWasHit(enemies, lastEnemyMoved);
            }
        };
    }
}
