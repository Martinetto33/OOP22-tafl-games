package taflgames.controller.leaderboard.api;

/**
 * This interface models an object capable of saving Leaderboard types objects to file.
 * <br><br>Users willing to save match results to file should create an instance of an implementation
 * of this interface such as {@link taflgames.controller.leaderboard.code.LeaderboardSaverImpl},
 * extract a {@link taflgames.controller.leaderboard.api.Leaderboard} implementing object through 
 * the {@link #retrieveFromSave()} method and then use that object to add new results. <br><br>At the end 
 * of the session, a "save" method can be called either on this LeaderboardSaver or on the 
 * Leaderboard object.
 */
public interface LeaderboardSaver {

    /**
     * Allows to save a given {@link taflgames.controller.leaderboard.api.Leaderboard} to file.
     * @param leaderboard the leaderboard to be saved.
     */
    void saveLeaderboard(Leaderboard leaderboard);

    /**
     * Reads and constructs a {@link taflgames.controller.leaderboard.api.Leaderboard} from a file.
     * @return the leaderboard retrieved from the file, or an empty Leaderboard if no
     * saved leaderboard is found.
     */
    Leaderboard retrieveFromSave();
}
