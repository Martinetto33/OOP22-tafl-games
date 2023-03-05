package taflgames.controller;

import java.io.IOException;

import taflgames.model.CellsCollectionBuilder;
import taflgames.model.PiecesCollectionBuilder;

/**
 * This interface allows to load the configuration settings for the setup of the board
 * for each game mode.
 */
public interface SettingsLoader {

    /**
     * Loads the classic mode configuration for the setup of the board
     * and directs the cells and pieces collections building accordingly.
     * @param cellsCollBuilder the cells collection builder
     * @param piecesCollBuilder the pieces collection builder
     * @throws IOException if an error occurs while loading the configuration settings
     */
    void loadClassicModeConfig(
        CellsCollectionBuilder cellsCollBuilder,
        PiecesCollectionBuilder piecesCollBuilder
    ) throws IOException;

    /**
     * Loads the variant mode configuration for the setup of the board
     * and directs the cells and pieces collections building accordingly.
     * @param cellsCollBuilder the cells collection builder
     * @param piecesCollBuilder the pieces collection builder
     * @throws IOException if an error occurs while loading the configuration settings
     */
    void loadVariantModeConfig(
        CellsCollectionBuilder cellsCollBuilder,
        PiecesCollectionBuilder piecesCollBuilder
    ) throws IOException;
    
}
