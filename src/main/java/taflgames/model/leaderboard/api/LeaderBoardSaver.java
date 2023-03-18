package taflgames.model.leaderboard.api;

/**
 * This interface models an object capable of saving Leaderboard types objects to file.
 */
public interface LeaderboardSaver {

    /**
     * Allows to save a given {@link taflgames.model.leaderboard.api.Leaderboard} to file.
     * @param leaderboard the leaderboard to be saved.
     */
    void saveLeaderboard(Leaderboard leaderboard);

    /**
     * Reads and constructs a {@link taflgames.model.leaderboard.api.Leaderboard} from a file.
     * @return the leaderboard retrieved from the file, or an empty Leaderboard if no
     * saved leaderboard is found.
     */
    Leaderboard retrieveFromSave();

    void setPath(String path);

    String getTestPath();

    String getDefaultPath();
}
