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

public class EatenImpl implements Eaten{

    private static final String DEAD_PIECE = "DEAD_PIECE"; //per segnalare morte pedina alle tombe
    
    private final Board board;
    private final CellsHitbox cellsHitbox;

    public EatenImpl(final Board board) {
        this.board = board;
        this.cellsHitbox = new CellsHitbox(board);
    }

    public Set<Position> trimHitbox(Piece currentPiece, Map<Player, Map<Position, Piece>> pieces, Map<Position, Cell> cells, final int size) {
        Set<Position> hitbox = currentPiece.whereToHit();
        Iterator<Position> hitboxIterator = hitbox.iterator();
        while(hitboxIterator.hasNext()) {
            Position position = hitboxIterator.next();
            if(position.getX() < 0 || position.getY() < 0 ) {
                hitboxIterator.remove();
            }
            else if(position.getX() >= size || position.getY() >= size ) {
                hitboxIterator.remove();
            } 
        }
        Set<Position> samePlayerPieces = hitbox.stream()
                                            .filter(x -> pieces.get(currentPiece.getPlayer()).keySet().contains(x))
                                            .collect(Collectors.toSet());

        if(!samePlayerPieces.isEmpty()) {
            /* ??????? Da rivedere, questo metodo non taglia la hitbox a partire dal
             * centro della pedina. Bisogna proseguire passo passo. Al momento la hitbox non
             * viene fermata da ostacoli.
             */
            if(!currentPiece.getMyType().getTypeOfPiece().equals("ARCHER")) {
                samePlayerPieces.forEach(x -> hitbox.remove(x));
            } else {
                samePlayerPieces.forEach(x -> {
                    Set<Position> elemToDelete = new HashSet<>();
                    if(currentPiece.getCurrentPosition().getY() < x.getY()) {
                        hitbox.stream().filter(elem -> elem.getY() >= x.getY()).forEach(elem -> elemToDelete.add(elem));
    
                    } else if(currentPiece.getCurrentPosition().getY() > x.getY()) {
                        hitbox.stream().filter(elem -> elem.getY() <= x.getY()).forEach(elem -> elemToDelete.add(elem));
                    } else if(currentPiece.getCurrentPosition().getX() < x.getX()) {
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
     * This method finds the enemies that are in the hitbox of the piece that wants to eat
     * @param hitbox
     * @param pieces
     * @param piece
     * @return
     */
    public List<Piece> getThreatenedPos(Set<Position> hitbox, Map<Player, Map<Position, Piece>> pieces, Piece piece) {
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
     * This method will create a map that associates each menaced enemy to all the allies
     * that are threatening it at the moment, so as to call "wasKilled" method on each
     * of them.
     * @param enemiesList
     * @param pieces
     * @param currPlayer
     * @return
     * TODO: insert hitboxes of Exits and Thrones
     */
    public Map<Piece, Set<Piece>> checkAllies(List<Piece> enemiesList, Map<Player, Map<Position, Piece>> pieces, Piece lastMovedPiece) {
        Map<Piece, Set<Piece>> mapOfEnemiesAndTheirKillers = new HashMap<>();
        /* The following map represents the allies of the piece attempting to eat the enemy
        that are on the same row and the same column as the piece */
        Map<Position, Piece> allies = new HashMap<>();
        pieces.get(lastMovedPiece.getPlayer()).entrySet().stream()
                                        .filter(ally -> ally.getKey().getX() == lastMovedPiece.getCurrentPosition().getX()
                                            || ally.getKey().getY() == lastMovedPiece.getCurrentPosition().getY())
                                        .forEach(ally -> allies.put(ally.getKey(), ally.getValue()));
        for (Piece enemy : enemiesList) {
            allies.entrySet().stream().forEach(x -> {
                if(x.getValue().whereToHit().contains(enemy.getCurrentPosition())) {
                    if(!mapOfEnemiesAndTheirKillers.containsKey(enemy)) {
                        Set<Piece> alliesPositions = new HashSet<>();
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

    public void notifyAllThreatened( Map<Piece, Set<Piece>> alliesMenacing, Piece lastMovedPiece, 
                                        Map<Position, Cell> cells, Map<Player, Map<Position, Piece>> pieces) {
        List<Piece> deadPieces = alliesMenacing.entrySet().stream()
            .filter(entry -> entry.getKey().wasKilled(entry.getValue(), lastMovedPiece.getCurrentPosition()))
            .map(entry -> entry.getKey())
            .toList();
        if (!deadPieces.isEmpty()) {
            this.notifyCellsThatPiecesDied(deadPieces, cells, pieces);
        }
    }

    private void notifyCellsThatPiecesDied(List<Piece> killedPieces, Map<Position, Cell> cells, Map<Player, Map<Position, Piece>> pieces) {
        for (final Piece deadPiece : killedPieces) {
            cells.get(deadPiece.getCurrentPosition()).setFree(true);
            pieces.get(deadPiece.getPlayer()).remove(deadPiece.getCurrentPosition());
            cells.get(deadPiece.getCurrentPosition()).notify(deadPiece.getCurrentPosition(), deadPiece, List.of(EatenImpl.DEAD_PIECE), pieces, cells);
        }
    }
}
