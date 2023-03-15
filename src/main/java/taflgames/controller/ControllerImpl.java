package taflgames.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import taflgames.model.BoardImpl;
import taflgames.model.Match;
import taflgames.model.MatchImpl;
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
    private Match match;  // the model

    /**
     * Instantiates a controller for the application.
     * @param view the view of the application
     */
    public ControllerImpl(final View view) {
        this.view = view;
    }

    @Override
    public void createClassicModeMatch() {
        final SettingsLoader loader = new SettingsLoaderImpl();
        final CellsCollectionBuilder cellsCollBuilder = new CellsCollectionBuilderImpl();
        final PiecesCollectionBuilder piecesCollBuilder = new PiecesCollectionBuilderImpl();
        try {
            loader.loadClassicModeConfig(cellsCollBuilder, piecesCollBuilder);
            this.match = new MatchImpl(
                new BoardImpl(cellsCollBuilder.build(), piecesCollBuilder.build())
            );
        } catch (final IOException ex) {
            /*
             * TO DO: the view has to know that an error occurred in order to display an error message
             * and prevent the match from starting without being initialized.
             */
            LOGGER.error("Error: cannot initialize a new match. ", ex.getMessage());
        }
    }

    @Override
    public void createVariantModeMatch() {
        final SettingsLoader loader = new SettingsLoaderImpl();
        final CellsCollectionBuilder cellsCollBuilder = new CellsCollectionBuilderImpl();
        final PiecesCollectionBuilder piecesCollBuilder = new PiecesCollectionBuilderImpl();
        try {
            loader.loadVariantModeConfig(cellsCollBuilder, piecesCollBuilder);
            this.match = new MatchImpl(
                new BoardImpl(cellsCollBuilder.build(), piecesCollBuilder.build())
            );
        } catch (final IOException ex) {
            /*
             * TO DO: the view has to know that an error occurred in order to display an error message
             * and prevent the match from starting without being initialized.
             */
            LOGGER.error("Error: cannot initialize a new match. ", ex.getMessage());
        }
    }
   
}
