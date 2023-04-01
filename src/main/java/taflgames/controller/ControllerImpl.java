package taflgames.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import taflgames.model.board.api.Board;
import taflgames.model.board.code.BoardImpl;
import taflgames.common.Player;
import taflgames.common.code.MatchResult;
import taflgames.common.code.Pair;
import taflgames.common.code.Position;
import taflgames.controller.gameloop.api.GameLoop;
import taflgames.controller.gameloop.code.GameLoopImpl;
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
    private Board board;
    private GameLoop gameLoop;

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
            this.board = new BoardImpl(pieces, cells, size);
            this.match = new Match(this.board);
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

        this.createGameLoop();
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
            this.board = new BoardImpl(pieces, cells, size);
            this.match = new Match(this.board);
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

        this.createGameLoop();
    }

    private void createGameLoop() {
        Objects.requireNonNull(this.match);
        this.gameLoop = new GameLoopImpl(this.match);
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
            this.gameLoop.makeMove(startPos, endPos);
        }
        //TODO: this.view.render();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        return this.gameLoop.isOver();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo() {
        this.gameLoop.undo();
        //TODO: this.view.render();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void passTurn() {
        this.gameLoop.passTurn();
        //TODO: this.view.render();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Pair<MatchResult, MatchResult>> getMatchResult() {
        return this.gameLoop.getMatchResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Position, List<String>> getCellsDisposition() {
        return this.board.getMapCells().entrySet().stream()
                .map(entry -> {
                    final List<String> cellTypes = new ArrayList<>();
                    cellTypes.add(0, entry.getValue().getType());

                    /* Mixes cell types with component types; the sprites corresponding
                     * to each of these elements should be drawn. In position 0 there
                     * will be the Cell type, in the others there will optionally
                     * be the types of the CellComponents (such as Tombs).
                    */
                    entry.getValue().getComponents().forEach(e -> cellTypes.add(e.getComponentType()));
                    return Map.entry(entry.getKey(), cellTypes);
                })
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Player, Map<Position, String>> getPiecesDisposition() {
        return this.board.getMapPieces().entrySet().stream()
                .map(entry -> {
                    final Map<Position, String> pieceTypes = entry.getValue().entrySet().stream()
                            .collect(Collectors.toUnmodifiableMap(
                                     Map.Entry::getKey, elem -> elem.getValue().getMyType().getTypeOfPiece()));
                    return Map.entry(entry.getKey(), pieceTypes);
                })
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getCurrentPlayer() {
        return this.match.getActivePlayer();
    }
}
