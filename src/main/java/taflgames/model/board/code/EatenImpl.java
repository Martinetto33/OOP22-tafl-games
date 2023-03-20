package taflgames.model.board.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.model.board.api.Board;
import taflgames.model.cell.api.Cell;

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
    
    public List<Position> getThreatenedPos(Set<Position> hitbox, Map<Player, Map<Position, Piece>> pieces, Piece piece) {
        List<Position> enemies = new ArrayList<>();
        for (Position position : hitbox) {
            if(pieces.entrySet().stream().filter(x -> x.getKey() != piece.getTeam()).map(x -> x.getValue().keySet().contains(position)).findAny().get()) {
                enemies.add(position);
            }
        }
        return enemies;
    }

    public Map<Position, List<Position>> checkAllies(List<Position> enemies, Map<Player, Map<Position, Piece>> pieces, Player currPlayer) {
        Map<Position, List<Position>> finalmap = new HashMap<>();
        Map<Position, Piece> allies = pieces.get(currPlayer);
        for (Position position : enemies) {
            allies.entrySet().stream().forEach(x -> {
                if(x.getValue().getHitbox().contains(position)) {
                    if(!finalmap.keySet().contains(position)) {
                        List<Position> alliesPosition = new ArrayList<>();
                        alliesPosition.add(x.getKey());
                        finalmap.put(position, alliesPosition);
                    }
                    finalmap.get(position).add(x.getKey());
                }
            });
        }
        return finalmap;
    }

    public void notifyAllThreatened( Map<Position, List<Position>> enemiesAndAllies, Piece lastMovedPiece, 
        Map<Player, Map<Position, Piece>> pieces, Map<Position, Cell> cells) {
        Map<Position, Piece> deadPiecesAndCoordinates = new HashMap<>();
        
    }

    private void notifyCellsThatPiecesDied(List<Position> killedPieces, Map<Position, Cell> cells, Map<Player, Map<Position, Piece>> pieces) {
        
    }
    //verificare se le modifiche fatte qui si vedono
    // una volta morto come faccio a dire alla cella che è libera?
}
