package taflgames.model.board.code;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.board.api.Board;
import taflgames.model.board.api.Eaten;
import taflgames.model.cell.api.Cell;
import taflgames.model.pieces.api.Piece;
import taflgames.model.cell.code.Tomb;

/**
 * This class models the eating of a Piece {@link taflgames.model.board.api.Eaten}.
 */
public class EatenImpl implements Eaten {

    private static final String TOMB = "Tomb";
    private static final String ARCHER = "ARCHER";
    private static final String DEAD_PIECE = "DEAD_PIECE"; //to signal the death of a piece to Tomb cells

    private final CellsHitbox cellsHitbox;

    /**
     * Create a new EatenImpl.
     * @param board the current board of the game.
     */
    public EatenImpl(final Board board) {
        this.cellsHitbox = new CellsHitbox(board);
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public Set<Position> trimHitbox(final Piece currentPiece, final Map<Player, Map<Position, Piece>> pieces, 
                                    final Map<Position, Cell> cells, final int size) {
        final Set<Position> hitbox = currentPiece.whereToHit();
        final Iterator<Position> hitboxIterator = hitbox.iterator();
        while (hitboxIterator.hasNext()) {
            final Position position = hitboxIterator.next();
            if (position.getX() < 0 || position.getY() < 0) {
                hitboxIterator.remove();
            } else if (position.getX() >= size || position.getY() >= size) {
                hitboxIterator.remove();
            } 
        }
        final Set<Position> samePlayerPieces = hitbox.stream()
                                            .filter(x -> pieces.get(currentPiece.getPlayer()).keySet().contains(x))
                                            .collect(Collectors.toSet());

        if (!samePlayerPieces.isEmpty()) {
            if (!ARCHER.equals(currentPiece.getMyType().getTypeOfPiece())) {
                samePlayerPieces.forEach(x -> hitbox.remove(x));
            } else {
                samePlayerPieces.forEach(x -> {
                    final Set<Position> elemToDelete = new HashSet<>();
                    if (currentPiece.getCurrentPosition().getY() < x.getY()) {
                        hitbox.stream().filter(elem -> elem.getY() >= x.getY()).forEach(elem -> elemToDelete.add(elem));
                    } else if (currentPiece.getCurrentPosition().getY() > x.getY()) {
                        hitbox.stream().filter(elem -> elem.getY() <= x.getY()).forEach(elem -> elemToDelete.add(elem));
                    } else if (currentPiece.getCurrentPosition().getX() < x.getX()) {
                        hitbox.stream().filter(elem -> elem.getX() >= x.getX()).forEach(elem -> elemToDelete.add(elem));
                    } else {
                        hitbox.stream().filter(elem -> elem.getX() <= x.getX()).forEach(elem -> elemToDelete.add(elem));
                    }
                    hitbox.removeAll(elemToDelete);
                });
            }
        }
        return hitbox;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Piece> getThreatenedPos(final Set<Position> hitbox, final Map<Player, Map<Position, Piece>> pieces, 
                                        final Piece piece) {
        List<Piece> enemies;
        enemies = pieces.entrySet().stream()
            .filter(entry -> entry.getKey() != piece.getPlayer())
            .flatMap(entry -> entry.getValue().entrySet().stream())
            .filter(entry -> hitbox.contains(entry.getKey()))
            .map(entry -> entry.getValue())
            .toList();
        return enemies;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Piece, Set<Piece>> checkAllies(final List<Piece> enemiesList, final Map<Player, Map<Position, Piece>> pieces, 
                                                final Piece lastMovedPiece, final Map<Position, Cell> cells, final int size) {
        final Map<Piece, Set<Piece>> mapOfEnemiesAndTheirKillers = new HashMap<>();
        /* The following map represents the allies of the piece attempting to eat the enemy
        that are on the same row and the same column as the piece */
        final Map<Position, Piece> allies = new HashMap<>();
        pieces.get(lastMovedPiece.getPlayer()).entrySet().stream().forEach(ally -> allies.put(ally.getKey(), ally.getValue()));
        /* Exits and Thrones can be considered as allies of the current player! 
         * This simple addition should work as normal.
        */
        this.cellsHitbox.getCellsAsPiecesWithHitbox(lastMovedPiece.getPlayer())
                        .forEach(piece -> allies.put(piece.getCurrentPosition(), piece));

        for (final Piece enemy : enemiesList) {
            allies.entrySet().stream().forEach(x -> {
                if (trimHitbox(x.getValue(), pieces, cells, size).contains(enemy.getCurrentPosition())) {
                    if (!mapOfEnemiesAndTheirKillers.containsKey(enemy)) {
                        final Set<Piece> alliesPositions = new HashSet<>();
                        alliesPositions.add(x.getValue());
                        mapOfEnemiesAndTheirKillers.put(enemy, alliesPositions);
                    } else {
                        mapOfEnemiesAndTheirKillers.get(enemy).add(x.getValue());
                    }
                }
            });
        }
        return mapOfEnemiesAndTheirKillers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyAllThreatened(final Map<Piece, Set<Piece>> alliesMenacing, final Piece lastMovedPiece, 
                                    final Map<Position, Cell> cells, final Map<Player,
                                    Map<Position, Piece>> pieces, final boolean doTombsSpawn) {
        final List<Piece> deadPieces = alliesMenacing.entrySet().stream()
            .filter(entry -> entry.getKey().wasKilled(entry.getValue(), lastMovedPiece.getCurrentPosition()))
            .map(entry -> entry.getKey())
            .toList();
        if (!deadPieces.isEmpty()) {
            this.notifyCellsThatPiecesDied(deadPieces, cells, pieces, doTombsSpawn);
        }
    }

    /**
     * Notify all pieces that were threatened by two or more pieces that they were eaten.
     * @param killedPieces List of the pieces that were eaten.
     * @param cells the Map of Position and Cell that that associate
     * to each Position of the Board the type of Cell that is placed there.
     * @param pieces the Map that associate to each Player it's own Map of Piece and Position.
     * @param doTombsSpawn true if Tombs can still spawn.
     */
    private void notifyCellsThatPiecesDied(final List<Piece> killedPieces, final Map<Position, Cell> cells, 
                                            final Map<Player, Map<Position, Piece>> pieces, final boolean doTombsSpawn) {
        for (final Piece deadPiece : killedPieces) {
            cells.get(deadPiece.getCurrentPosition()).setFree(true);
            pieces.get(deadPiece.getPlayer()).remove(deadPiece.getCurrentPosition());

            /* 
             * Spawning Tombs if needed; no more than one Tomb for each cell.
             * A new Tomb CellComponent is attached only if there aren't any already
             * attached.
             */
            if (doTombsSpawn && !cells.get(deadPiece.getCurrentPosition()).getComponents()
                                .stream()
                                .anyMatch(c -> TOMB.equals(c.getComponentType()))
            ) {
                cells.get(deadPiece.getCurrentPosition()).attachComponent(new Tomb());
            }

            cells.get(deadPiece.getCurrentPosition()).notify(deadPiece.getCurrentPosition(), deadPiece, 
                        List.of(EatenImpl.DEAD_PIECE), pieces, cells);
        }
    }
}
