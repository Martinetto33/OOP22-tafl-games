package taflgames.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import taflgames.model.board.code.BoardImpl;
import taflgames.common.Player;
import taflgames.common.code.MatchResult;
import taflgames.common.code.Pair;
import taflgames.common.code.Position;
import taflgames.model.Model;
import taflgames.model.Match;
import taflgames.model.builders.CellsCollectionBuilder;
import taflgames.model.builders.CellsCollectionBuilderImpl;
import taflgames.model.builders.PiecesCollectionBuilder;
import taflgames.model.builders.PiecesCollectionBuilderImpl;
import taflgames.view.View;

/**
 * This class implements the controller of the application.
 */
public final class ControllerImpl implements Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerImpl.class);

    private final View view;
    private Model match;

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
            LOGGER.info("The classic mode match has been initialized successfully.");
        } catch (final IOException ex) {
            /*
             * The view has to know that an error occurred, in order to display an error message
             * and prevent the match from starting without being initialized.
             */
            final String errorMsg = "Error: cannot initialize a new match. " + ex.getMessage();
            LOGGER.error(errorMsg);
            throw new IOException(errorMsg);
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
            LOGGER.info("The variant mode match has been initialized successfully.");
        } catch (final IOException ex) {
            /*
             * The view has to know that an error occurred, in order to display an error message
             * and prevent the match from starting without being initialized.
             */
            final String errorMsg = "Error: cannot initialize a new match. " + ex.getMessage();
            LOGGER.error(errorMsg);
            throw new IOException(errorMsg);
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
    public void makeMove(final Position startPos, final Position endPos) {
        if (this.match.selectSource(startPos) && this.match.selectDestination(startPos, endPos)) {
            this.match.makeMove(startPos, endPos);
            this.view.update();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void passTurn() {
        this.match.setNextActivePlayer();
        //TODO: this.view.update();
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
    public Map<Position, List<String>> getCellsDisposition() {
        return this.match.getCellsMapping();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Player, Map<Position, String>> getPiecesDisposition() {
        return this.match.getPiecesMapping();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo() {
        // TODO: undo
        // TODO: this.view.update();
    }

}
