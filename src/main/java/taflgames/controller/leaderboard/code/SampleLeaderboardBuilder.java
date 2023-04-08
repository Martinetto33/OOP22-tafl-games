package taflgames.controller.leaderboard.code;

import taflgames.common.code.MatchResult;
import taflgames.controller.leaderboard.api.Leaderboard;
import taflgames.controller.leaderboard.api.LeaderboardSaver;

/**
 * A utility class for creating a quick leaderboard where needed.
 */
public final class SampleLeaderboardBuilder {
    /* Used to hide default constructor; needed to avoid checkstyle warnings. */
    private SampleLeaderboardBuilder() {

    }

    /**
     * Creates a leaderboard, adds some results to it and modifies
     * the leaderboard default file.
     */
    public static void createSampleLeaderboard() {
        final LeaderboardSaver leaderboardManager = new LeaderboardSaverImpl();
        final Leaderboard leaderboard = leaderboardManager.retrieveFromSave();
        final String player1 = "Alin";
        final String player2 = "Andrea";

        leaderboard.addResult(player1, MatchResult.VICTORY);
        leaderboard.addResult(player1, MatchResult.VICTORY);
        leaderboard.addResult(player1, MatchResult.VICTORY);
        leaderboard.addResult(player1, MatchResult.DEFEAT);
        leaderboard.addResult(player2, MatchResult.VICTORY);
        leaderboard.addResult(player2, MatchResult.VICTORY);
        leaderboard.addResult(player2, MatchResult.DEFEAT);
        leaderboard.addResult(player2, MatchResult.DEFEAT);

        leaderboardManager.saveLeaderboard(leaderboard);
    }
}
