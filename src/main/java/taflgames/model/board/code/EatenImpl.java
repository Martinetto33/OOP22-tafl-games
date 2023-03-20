package taflgames.model.board.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.board.api.Board;
import taflgames.model.cell.api.Cell;
import taflgames.model.pieces.api.Piece;

public class EatenImpl {

    private static final String DEAD_PIECE = "DEAD_PIECE"; //per segnalare morte pedina alle tombe

    private final Board board;

    public EatenImpl(Board board) {
        this.board = board;
    }

    public Set<Position> trimHitbox(Set<Position> hitbox, Map<Position, Cell> cells) {
        for (Position position : hitbox) {
            if(cells.get(position).getType().equals("Exit") || cells.get(position).getType().equals("Throne")) {
                hitbox.remove(position);
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
     * @param enemies
     * @param pieces
     * @param currPlayer
     * @return
     */
    public Map<Piece, Set<Piece>> checkAllies(List<Piece> enemies, Map<Player, Map<Position, Piece>> pieces, Player currPlayer) {
        Map<Piece, Set<Piece>> finalmap = new HashMap<>();
        /* The following map represents the allies of the piece attempting to eat*/ 
        Map<Position, Piece> allies = pieces.get(currPlayer);
        for (Piece enemy : enemies) {
            allies.entrySet().stream().forEach(x -> {
                if(x.getValue().whereToHit().contains(enemy.getCurrentPosition())) {
                    if(!finalmap.containsKey(enemy)) {
                        Set<Piece> alliesPosition = new HashSet<>();
                        alliesPosition.add(x.getValue());
                        finalmap.put(enemy, alliesPosition);
                    } else {
                        finalmap.get(enemy).add(x.getValue());
                    }
                }
            });
        }
        return finalmap;
    }

    public void notifyAllThreatened( Map<Piece, Set<Piece>> enemiesAndAllies, Piece lastMovedPiece, 
                                        Map<Position, Cell> cells ) {
        List<Piece> deadPieces = enemiesAndAllies.entrySet().stream()
            .filter(entry -> entry.getKey().wasKilled(entry.getValue(), lastMovedPiece.getCurrentPosition()))
            .map(entry -> entry.getKey())
            .toList();
        this.notifyCellsThatPiecesDied(deadPieces, cells);
    }

    private void notifyCellsThatPiecesDied(List<Piece> killedPieces, Map<Position, Cell> cells) {
        for (final Piece deadPiece : killedPieces) {
            cells.get(deadPiece.getCurrentPosition()).setFree(true);
            cells.get(deadPiece.getCurrentPosition()).notify(deadPiece.getCurrentPosition(), deadPiece, List.of(EatenImpl.DEAD_PIECE));
        }
    }
    //verificare se le modifiche fatte qui si vedono
    // una volta morto come faccio a dire alla cella che è libera?
}
