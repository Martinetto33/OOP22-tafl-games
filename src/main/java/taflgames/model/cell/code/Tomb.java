package taflgames.model.cell.code;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import taflgames.common.code.Position;
import taflgames.model.pieces.api.Piece;
import taflgames.common.Player;


public class Tomb extends AbstractCell{

    private Map<Player, Queue<Piece>> deadPieces = new HashMap<>();	

    public Tomb() {
        super();
    }

    @Override
    public void notify(Position source, Piece sender, List<String> events) {
        // Per ora considero event come una stringa
        if (events.contains("QUEEN_MOVE")) {
            resumePiece(sender.getPlayer());  // viene resuscitata una pedina del giocatore mangiata sulla casella corrente (se esiste)
        }
        if(events.contains("DEAD_PIECE")) {
            addDeadPieces(sender.getPlayer(), sender);
        }
    }

    @Override
    public boolean canAccept(Piece piece) {
        if(super.isFree()) {
            return true;
        } else {
            return false;
        }
    }
    
    private void resumePiece(final Player player) {
        // Se sulla tomba ci sono pedine mangiate del giocatore corrente
        if (!deadPieces.get(player).isEmpty()) {
            Piece pieceToResume = deadPieces.get(player).poll();	// prende la prima pedina in coda
            pieceToResume.reanimate();	// ora è viva
        }
    }

    public void addDeadPieces(final Player player, Piece piece) {
        if(!deadPieces.containsKey(player)) {
            Queue<Piece> list = new LinkedList<>();
            list.add(piece);
            deadPieces.put(player, list);
        } else {
            deadPieces.get(player).add(piece);
        }
    }

    @Override
    public String getType() {
        return "Tomb";
    }

}
