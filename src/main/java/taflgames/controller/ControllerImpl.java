package taflgames.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import taflgames.model.board.code.BoardImpl;
import taflgames.common.Player;
import taflgames.common.code.MatchResult;
import taflgames.common.code.Pair;
import taflgames.common.code.Position;
import taflgames.controller.entitystate.CellState;
import taflgames.controller.entitystate.PieceState;
import taflgames.controller.leaderboard.api.LeaderboardSaver;
import taflgames.controller.leaderboard.code.LeaderBoardImpl;
import taflgames.controller.leaderboard.code.LeaderboardSaverImpl;
import taflgames.model.Model;
import taflgames.model.Match;
import taflgames.model.builders.CellsCollectionBuilder;
import taflgames.model.builders.CellsCollectionBuilderImpl;
import taflgames.model.builders.PiecesCollectionBuilder;
import taflgames.model.builders.PiecesCollectionBuilderImpl;
import taflgames.model.memento.api.Caretaker;
import taflgames.model.memento.code.CaretakerImpl;
import taflgames.view.View;

/**
 * This class implements the controller of the application.
 */
public final class ControllerImpl implements Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerImpl.class);

    private final View view;
    private Model match;
    private Caretaker caretaker;

    /**
     * Instantiates a controller for the application.
     * @param view the view of the application
     */
    public ControllerImpl(final View view) {
        this.view = view;
    }

    @Override
    public void createClassicModeMatch() throws IOException {
        final SettingsLoader loader = new SettingsLoaderImpl();
        final CellsCollectionBuilder cellsCollBuilder = new CellsCollectionBuilderImpl();
        final PiecesCollectionBuilder piecesCollBuilder = new PiecesCollectionBuilderImpl();
        try {
            loader.loadClassicModeConfig(cellsCollBuilder, piecesCollBuilder);
            final var pieces = piecesCollBuilder.build();
            final var cells = cellsCollBuilder.build();
            final int size = (int) Math.sqrt(cells.size());
            this.match = new Match(
                new BoardImpl(pieces, cells, size)
            );
            this.caretaker = new CaretakerImpl(this.match);
            this.caretaker.updateHistory();
        } catch (final IOException exception) {
            final String errorMsg = "Error: cannot initialize a new match.";
            LOGGER.error(errorMsg, exception);
            throw exception;
        }
    }

    @Override
    public void createVariantModeMatch() throws IOException {
        final SettingsLoader loader = new SettingsLoaderImpl();
        final CellsCollectionBuilder cellsCollBuilder = new CellsCollectionBuilderImpl();
        final PiecesCollectionBuilder piecesCollBuilder = new PiecesCollectionBuilderImpl();
        try {
            loader.loadVariantModeConfig(cellsCollBuilder, piecesCollBuilder);
            final var pieces = piecesCollBuilder.build();
            final var cells = cellsCollBuilder.build();
            final int size = (int) Math.sqrt(cells.size());
            this.match = new Match(
                new BoardImpl(pieces, cells, size)
            );
            this.caretaker = new CaretakerImpl(this.match);
            this.caretaker.updateHistory();
        } catch (final IOException exception) {
            final String errorMsg = "Error: cannot initialize a new match. " + exception.getMessage();
            LOGGER.error(errorMsg, exception);
            throw exception;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isStartingPointValid(final Position p) {
        return this.match.selectSource(p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDestinationValid(final Position startPos, final Position endPos) {
        return this.match.selectDestination(startPos, endPos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean moveIfLegal(final Position startPos, final Position endPos) {
        final boolean isMoveLegal = this.match.selectSource(startPos) && this.match.selectDestination(startPos, endPos);
        if (isMoveLegal) {
            this.match.makeMove(startPos, endPos);
            // The move has been performed, so the board view must be updated.
            this.view.update();
        }
        return isMoveLegal;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void passTurn() {
        if (!this.isOver()) {
            this.match.setNextActivePlayer();
            this.caretaker.updateHistory();
            this.view.update();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getCurrentPlayer() {
        return this.match.getActivePlayer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        return this.match.getMatchEndStatus().isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Pair<MatchResult, MatchResult>> getMatchResult() {
        return this.match.getMatchEndStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Position, CellState> getCellsDisposition() {
        return this.match.getCellsMapping();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Position, PieceState> getPiecesDisposition() {
        return this.match.getPiecesMapping();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo() {
        /* If history was not updated, this
         * method should do nothing.
         */
        if (this.caretaker.isLocked()) {
            this.caretaker.unlockHistory();
            this.caretaker.undo();
            this.view.update();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Pair<Integer, Integer>> getLeaderboard() {
        final LeaderboardSaver leaderboardManager = new LeaderboardSaverImpl();
        return leaderboardManager.retrieveFromSave().getLeaderboard();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearLeaderboard() {
        final LeaderboardSaver l = new LeaderboardSaverImpl();
        l.saveLeaderboard(new LeaderBoardImpl());
    }
}
