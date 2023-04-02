package taflgames.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.iterators.LoopingIterator;

import taflgames.common.Player;
import taflgames.common.code.MatchResult;
import taflgames.common.code.Pair;
import taflgames.common.code.Position;
import taflgames.model.board.api.Board;
import taflgames.model.memento.api.BoardMemento;
import taflgames.model.memento.api.MatchMemento;

/**
 * This class implements a match.
 */
public final class Match implements Model {

    private final Board board;
    private final LoopingIterator<Player> turnQueue;
    private Player activePlayer;
    private int turnNumber;

    /**
     * Creates a new match.
     * @param board the board used for the match
     */
    public Match(final Board board) {
        this.board = board;
        this.turnQueue = new LoopingIterator<>(
            List.of(Player.ATTACKER, Player.DEFENDER)
        );
        this.activePlayer = this.turnQueue.next();
        this.turnNumber = 0;
    }

    @Override
    public Player getActivePlayer() {
        return this.activePlayer;
    }

    @Override
    public int getTurnNumber() {
        return this.turnNumber;
    }

    @Override
    public void setNextActivePlayer() {
        this.activePlayer = this.turnQueue.next();
        this.board.notifyTurnHasEnded(turnNumber);
        this.turnNumber++;
    }

    @Override
    public boolean selectSource(final Position start) {
        return this.board.isStartingPointValid(start, activePlayer);
    }

    @Override
    public boolean selectDestination(final Position start, final Position destination) {
        return this.board.isDestinationValid(start, destination, activePlayer);
    }

    @Override
    public void makeMove(final Position start, final Position destination) {
        this.board.updatePiecePos(start, destination, activePlayer);
        this.board.eat();   // Performs the eatings caused by the move just made.
    }

    @Override
    public Optional<Pair<MatchResult, MatchResult>> getMatchEndStatus() {
        if (this.board.checkForWinningPlayer().isPresent()) {
            final Player winner = this.board.checkForWinningPlayer().get();
            return winner.equals(Player.ATTACKER)
                    ? Optional.of(new Pair<>(MatchResult.VICTORY, MatchResult.DEFEAT))
                    : Optional.of(new Pair<>(MatchResult.DEFEAT, MatchResult.VICTORY));
        } else if (this.board.isDraw(activePlayer)) {
            return Optional.of(new Pair<>(MatchResult.DRAW, MatchResult.DRAW));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Map<Position, List<String>> getCellsMapping() {
        return this.board.getMapCells().entrySet().stream()
                .map(entry -> {
                    final List<String> cellTypes = new ArrayList<>();
                    cellTypes.add(0, entry.getValue().getType());
                    /* 
                     * Mixes cell types with component types; the sprites corresponding
                     * to each of these elements should be drawn. In position 0 there
                     * will be the Cell type, in the others there will optionally
                     * be the types of the CellComponents (such as Tombs).
                    */
                    entry.getValue().getComponents().forEach(e -> cellTypes.add(e.getComponentType()));
                    return Map.entry(entry.getKey(), cellTypes);
                })
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<Player, Map<Position, String>> getPiecesMapping() {
        return this.board.getMapPieces().entrySet().stream()
                .map(entry -> {
                    final Map<Position, String> pieceTypes = entry.getValue().entrySet().stream()
                            .collect(Collectors.toUnmodifiableMap(
                                    Map.Entry::getKey, elem -> elem.getValue().getMyType().getTypeOfPiece()
                            ));
                    return Map.entry(entry.getKey(), pieceTypes);
                })
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * This inner class implements a {@link MatchMemento}, which is responsible
     * for saving the current state of the match and provide it on request.
     */
    public final class MatchMementoImpl implements MatchMemento {

        private final int turnNumber;
        private final Player activePlayer;
        private final BoardMemento boardMemento;

        /**
         * Creates a new snapshot of the current state of the match.
         * @param boardMemento the snapshot of the current state of the {@link Board}
         */
        public MatchMementoImpl(final BoardMemento boardMemento) {
            this.turnNumber = Match.this.turnNumber;
            this.activePlayer = Match.this.activePlayer;
            this.boardMemento = boardMemento;
        }

        @Override
        public int getTurnNumber() {
            return this.turnNumber;
        }

        @Override
        public Player getActivePlayer() {
            return this.activePlayer;
        }

        @Override
        public BoardMemento getBoardMemento() {
            return this.boardMemento;
        }

    }

    @Override
    public MatchMemento save() {
        return new MatchMementoImpl(this.board.save());
    }

    @Override
    public void restore(final MatchMemento matchMemento) {
        this.turnNumber = matchMemento.getTurnNumber();
        this.activePlayer = matchMemento.getActivePlayer();
        matchMemento.getBoardMemento().restore();
    }

}
