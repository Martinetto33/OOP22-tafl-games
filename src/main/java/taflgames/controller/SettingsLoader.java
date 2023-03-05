package taflgames.controller;

import java.io.IOException;

import taflgames.model.BoardBuilder;

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
