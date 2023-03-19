package taflgames.model.leaderboard.api;

import java.util.Map;
import java.util.Optional;

import taflgames.common.code.MatchResult;
import taflgames.common.code.Pair;

/**
 * The Leaderboard will store all the results of the matches. At the end of
 * each match, the players will be asked to insert their names, and their
 * results will be saved.
 * <br><br>In order to retrieve data from existing leaderboards correctly, no
 * implementation of this interface should be instantiated directly, but rather
 * through a {@link taflgames.model.leaderboard.api.LeaderboardSaver#retrieveFromSave()}
 * method. New results can be easily added with {@link #addResult(String, MatchResult)},
 * and a LeaderboardSaver or a Leaderboard 'saveToFile' method can be called interchangeably
 * with the same result.
 */
public interface Leaderboard {
    /**
     * Adds a new result at the end of a Match.
     * @param playerName the name of one of the two players.
     * @param result the result obtained by the specified player.
     */
    void addResult(String playerName, MatchResult result);

    /**
     * Returns the Leaderboard represented by a Map which has player names as keys
     * and Pairs of number of wins and number of losses as values.
     * @return a Map representing the current state of the Leaderboard, sorted by
     * number of wins of each player.
     */
    Map<String, Pair<Integer, Integer>> getLeaderboard();

    /**
     * Gets the results associated with the specified player name, if there are any.
     * @param playerName the name of the player whose results are to be searched.
     * @return an Optional containing the result represented by a Pair of wins and losses,
     * or an empty Optional if the player hasn't registered any results yet.
     */
    Optional<Pair<Integer, Integer>> getScoreFromPlayer(String playerName);

    /**
     * Clears all the results stored in the leaderboard.
     */
    void clearLeaderboard();

    /**
     * Saves the results of this session to a file.
     * @param path the path to the save file.
     * @param saver a LeaderboardSaver which will be in charge of saving the leaderboard.
     */
    void saveToFile(String path, LeaderboardSaver saver);

    /**
     * Returns a String representation of the player's score, if present.
     * @param playerName the player to search the scores of.
     * @return a String representation of the scores of this player.
     */
    String getPlayerResultAsString(String playerName);
}
