package taflgames.model.cell.code;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import taflgames.common.code.Position;
import taflgames.model.cell.api.Cell;
import taflgames.model.memento.api.CellMemento;
import taflgames.model.pieces.api.Piece;
import taflgames.common.Player;


public class Tomb extends AbstractCell{

    private Map<Player, Queue<Piece>> deadPieces = new HashMap<>();	

    public Tomb() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notify(Position source, Piece sender, List<String> events, Map<Player, Map<Position, Piece>> pieces,
                            Map<Position, Cell> cells) {
        // Per ora considero event come una stringa
        if (events.contains("QUEEN_MOVE")) {
            resumePiece(sender.getPlayer(), pieces, cells);  // viene resuscitata una pedina del giocatore mangiata sulla casella corrente (se esiste)
        }
        if(events.contains("DEAD_PIECE")) {
            addDeadPieces(sender.getPlayer(), sender);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canAccept(Piece piece) {
        if(super.isFree()) {
            return true;
        } else {
            return false;
        }
    }
    
    private void resumePiece(final Player player, Map<Player, Map<Position, Piece>> pieces,
                                Map<Position, Cell> cells) {
        // Se sulla tomba ci sono pedine mangiate del giocatore corrente
        if (this.deadPieces.get(player) != null && !deadPieces.get(player).isEmpty()) {
            Piece pieceToResume = deadPieces.get(player).poll();// prende la prima pedina in coda
            pieceToResume.reanimate();	// ora è viva
            cells.get(pieceToResume.getCurrentPosition()).setFree(false);
            pieces.get(player).put(pieceToResume.getCurrentPosition(), pieceToResume);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return "Tomb";
    }

    public CellMemento save() {
        return this.new TombMementoImpl();
    }

    public void restore(TombMementoImpl tm) {
        this.deadPieces = tm.getInnerDeadPieces();
        super.restore(tm);
    }

    public class TombMementoImpl implements CellMemento {
        private final Map<Player, Queue<Piece>> innerDeadPieces;
        private final boolean isFree;

        public TombMementoImpl() {
            /* This way of copying maps should create a deep copy. */
            this.innerDeadPieces = Tomb.this.deadPieces.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            this.isFree = Tomb.this.isFree();
        }

        @Override
        public void restore() {
            Tomb.this.restore(this);
        }

        @Override
        public boolean getCellStatus() {
            return this.isFree;
        }

        public Map<Player, Queue<Piece>> getInnerDeadPieces() {
            return this.innerDeadPieces;
        }

    }
}
