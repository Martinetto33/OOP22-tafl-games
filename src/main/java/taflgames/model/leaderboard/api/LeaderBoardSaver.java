package taflgames.model.leaderboard.api;

/**
 * This interface models an object capable of saving Leaderboard types objects to file.
 */
public interface LeaderBoardSaver {
    
    void saveLeaderboard(Leaderboard leaderboard);

    Leaderboard retrieveFromSave();

}
