package taflgames;

import taflgames.controller.leaderboard.code.SampleLeaderboardBuilder;
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
        SampleLeaderboardBuilder.createSampleLeaderboard();
        new ViewImpl();
    }

}
