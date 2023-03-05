package taflgames.controller;

import java.io.IOException;

import taflgames.model.BoardBuilder;

/**
 * This interface allows to load the configuration settings for the setup of the board
 * for each game mode.
 */
public interface SettingsLoader {

    /**
     * Loads the classic mode configuration for the setup of the board
     * and modifies the {@link BoardBuilder} accordingly.
     * @param boardBuilder the board builder
     * @throws IOException if an error occurs while loading the configuration settings
     */
    void loadClassicModeConfig(BoardBuilder boardBuilder) throws IOException;

    /**
     * Loads the variant mode configuration for the setup of the board
     * and modifies the {@link BoardBuilder} accordingly.
     * @param boardBuilder the board builder
     * @throws IOException if an error occurs while loading the configuration settings
     */
    void loadVariantModeConfig(BoardBuilder boardBuilder) throws IOException;

}
