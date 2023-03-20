package taflgames.model.leaderboard.api;

/**
 * This interface models an object capable of saving Leaderboard types objects to file.
 * <br><br>Users willing to save match results to file should create an instance of an implementation
 * of this interface such as {@link taflgames.model.leaderboard.code.LeaderboardSaverImpl},
 * extract a {@link taflgames.model.leaderboard.api.Leaderboard} implementing object through 
 * the {@link #retrieveFromSave()} method and then use that object to add new results. <br><br>At the end 
 * of the session, a "save" method can be called either on this LeaderboardSaver or on the 
 * Leaderboard object.
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

    /**
     * Sets a path to which saves and from which retrievals will be attempted. There are
     * only two acceptable paths, the default one executed at runtime and the test one
     * executed at test time. In order to get those, the method {@link #getTestPath()}
     * or the method {@link #getDefaultPath()} can be called. If no path is specified,
     * the default one will be used.
     * @param path the path of the file to and from which saves and retrievals will be
     * attempted.
     */
    void setPath(String path);

    /**
     * Returns the path to file that should be used in testing instances.
     * @return the test path.
     */
    String getTestPath();

    /**
     * Returns the default path to which the application will attempt to
     * manage the save files.
     * @return the default path.
     */
    String getDefaultPath();
}
