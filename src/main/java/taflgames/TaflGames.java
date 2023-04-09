package taflgames;

import taflgames.view.ViewImpl;

/**
 * The entry point of the application.
 */
public final class TaflGames {

    private TaflGames() {
    }

    /**
     * Starts the application.
     * @param args unused
     */
    public static void main(final String... args) {
        Installer.createFile();
        new ViewImpl();
    }

}
