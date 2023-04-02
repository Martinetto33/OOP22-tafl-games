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

        leaderboard.addResult("Alin", MatchResult.VICTORY);
        leaderboard.addResult("Alin", MatchResult.VICTORY);
        leaderboard.addResult("Alin", MatchResult.VICTORY);
        leaderboard.addResult("Alin", MatchResult.DEFEAT);
        leaderboard.addResult("Andrea", MatchResult.VICTORY);
        leaderboard.addResult("Andrea", MatchResult.VICTORY);
        leaderboard.addResult("Andrea", MatchResult.DEFEAT);
        leaderboard.addResult("Andrea", MatchResult.DEFEAT);

        leaderboardManager.saveLeaderboard(leaderboard);
    }
}
