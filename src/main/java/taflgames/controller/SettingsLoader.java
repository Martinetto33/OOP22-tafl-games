package taflgames.controller;

import java.io.IOException;

import taflgames.model.BoardBuilder;

public interface SettingsLoader {

    void loadClassicModeConfig(final BoardBuilder boardBuilder) throws IOException;

    void loadVariantModeConfig(final BoardBuilder boardBuilder) throws IOException;

}
