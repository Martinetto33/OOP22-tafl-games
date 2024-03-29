package taflgames.model.cell.code;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
//import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import taflgames.common.code.Position;
import taflgames.controller.entitystate.CellState;
import taflgames.model.cell.api.Cell;
import taflgames.model.cell.api.CellComponent;
import taflgames.model.memento.api.CellComponentMemento;
import taflgames.model.memento.api.CellMemento;
import taflgames.model.pieces.api.Piece;
import taflgames.common.Player;

/**
 * The Tomb is a particular entity that can contain dead pieces of both teams.
 * Only Queens can interact with them, spawning back an allied Piece if they
 * land to a cell adjacent to the Tomb.
 */
public final class Tomb extends AbstractCell implements CellComponent {

    private Map<Player, Queue<Piece>> deadPieces = new HashMap<>();
    private static final String TOMB_TYPE = "Tomb";

    /**
     * {@inheritDoc}
     */
    @Override
    public void notify(
        final Position source,
        final Piece sender,
        final List<String> events,
        final Map<Player, Map<Position, Piece>> pieces,
        final Map<Position, Cell> cells
    ) {
        // Per ora considero event come una stringa
        if (events.contains("QUEEN_MOVE")) {
            // viene resuscitata una pedina del giocatore mangiata sulla casella corrente (se esiste)
            resumePiece(sender.getPlayer(), pieces, cells);
        }
        if (events.contains("DEAD_PIECE")) {
            addDeadPieces(sender.getPlayer(), sender);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canAccept(final Piece piece) {
        return super.isFree();
    }

    /**
     * Resume the last Piece of the given Player that died on the tomb.
     * @param player the Player in turn.
     * @param cells the Map of Position and Cell that associate
     * to each Position of the Board the type of Cell that is placed there.
     * @param pieces the Map that associate to each Player it's own map of Piece and Position.
     */
    private void resumePiece(
        final Player player,
        final Map<Player, Map<Position, Piece>> pieces,
        final Map<Position, Cell> cells
    ) {
        /* Controls if on the tomb there are any dead pieces of the current player */
        if (this.deadPieces.get(player) != null && !deadPieces.get(player).isEmpty()) {
            final Piece pieceToResume = deadPieces.get(player).poll();    // get the first piece in the queue
            pieceToResume.reanimate();  // resurrect the piece, now it's alive
            cells.get(pieceToResume.getCurrentPosition()).setFree(false);
            pieces.get(player).put(pieceToResume.getCurrentPosition(), pieceToResume);
        }
    }

    /**
     * When a piece dies on the tomb it's added to {@link #deadPieces}.
     * This map associate to each player his pieces that died on the tomb.
     * @param player the Player in turn.
     * @param piece the dead Piece.
     */
    private void addDeadPieces(final Player player, final Piece piece) {
        if (!deadPieces.containsKey(player)) {
            final Queue<Piece> list = new LinkedList<>();
            list.add(piece);
            deadPieces.put(player, list);
        } else {
            deadPieces.get(player).add(piece);
        }
    }

    /**
     * Used for view purposes. If the tomb only contains pieces of the same team,
     * it returns the name of that team; otherwise it returns null.
     * @return a {@link taflgames.common.Player} if the dead pieces are all of the
     * same team, or null otherwise.
     */
    public Player peekTeamOfTheTomb() {
        if (this.deadPieces.isEmpty()) {
            return null;
        }
        if (this.deadPieces.values().stream()
                .flatMap(queue -> queue.stream())
                .allMatch(piece -> piece.getPlayer().equals(Player.ATTACKER))) {
            return Player.ATTACKER;
        } else if (this.deadPieces.values().stream()
                       .flatMap(queue -> queue.stream())
                       .allMatch(piece -> piece.getPlayer().equals(Player.DEFENDER))) {
                    return Player.DEFENDER;
                }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return TOMB_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CellMemento save() {
        return this.new TombMementoImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CellComponentMemento saveComponentState() {
        return this.new TombMementoImpl();
    }

    /**
     * Restores the state of this Tomb.
     * @param tm the {@link taflgames.model.cell.code.Tomb.TombMementoImpl}
     * containing the information about the previous state of this Tomb.
     */
    public void restore(final TombMementoImpl tm) {
        this.deadPieces = tm.getInnerDeadPieces().entrySet().stream()
            .map(entry -> {
                /* This longer lambda creates a deep copy of the Queues, to
                * ensure that modifications of the state of the match do not
                * affect this snapshot of the queued dead pieces.
                */
                final Queue<Piece> queue = new LinkedList<>();
                entry.getValue().stream().forEachOrdered(piece -> queue.add(piece));
                return Map.entry(entry.getKey(), queue);
            })
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        super.restore(tm);
    }

    /**
     * A class modelling a Memento for a Tomb.
     */
    public final class TombMementoImpl implements CellMemento, CellComponentMemento {
        private final Map<Player, Queue<Piece>> innerDeadPieces;
        private final boolean isFree;

        /**
         * Builds a new TombMementoImpl that stores the state of a Tomb.
         */
        @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "A method to get a copy of an object of type Piece is not provided."
        )
        public TombMementoImpl() {
            /* This way of copying maps should create a deep copy. */
            this.innerDeadPieces = Map.copyOf(Tomb.this.deadPieces).entrySet().stream()
                .map(entry -> {
                    /* This longer lambda creates a deep copy of the Queues, to
                     * ensure that modifications of the state of the match do not
                     * affect this snapshot of the queued dead pieces.
                     */
                    final Queue<Piece> queue = new LinkedList<>();
                    entry.getValue().stream().forEachOrdered(piece -> queue.add(piece));
                    return Map.entry(entry.getKey(), queue);
                })
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
            this.isFree = Tomb.this.isFree();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void restore() {
            Tomb.this.restore(this);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean getCellStatus() {
            return this.isFree;
        }

        /**
         * Returns the dead pieces as they were at the moment of the save.
         * @return a Map of dead pieces associated to their teams.
         */
        public Map<Player, Queue<Piece>> getInnerDeadPieces() {
            return Map.copyOf(this.innerDeadPieces);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<CellComponentMemento> getComponentMementos() {
            return Collections.emptyList();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyComponent(
        final Position source,
        final Piece sender,
        final List<String> events,
        final Map<Player, Map<Position, Piece>> pieces,
        final Map<Position, Cell> cells
    ) {
        this.notify(source, sender, events, pieces, cells);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActive() {
        if (this.deadPieces.isEmpty()) {
            return false;
        }
        /* If there's at least one of the
         * queues that is not empty, this Tomb is still
         * active.
         */
        return this.deadPieces.values().stream()
                .filter(queue -> !queue.isEmpty())
                .findAny()
                .isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getComponentType() {
        return this.getType();
    }

    /**
     * The case of the Tomb is managed directly in the AbstractCell.
     */
    @Override
    public CellState getSubclassCellState() {
        return null;
    }
}
