package taflgames.model.leaderboard.api;

import java.util.Map;
import java.util.Optional;

import taflgames.common.code.MatchResult;
import taflgames.common.code.Pair;

/**
 * The Leaderboard will store all the results of the matches. At the end of
 * each match, the players will be asked to insert their names, and their
 * results will be saved.
 */
public interface Leaderboard {
    /**
     * Adds a new result at the end of a Match.
     * @param playerName the name of one of the two players
     * @param result the result obtained by the specified player
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
     * @param playerName the name of the player whose results are to be searched
     * @return an Optional containing the result represented by a Pair of wins and losses,
     * or an empty Optional if the player hasn't registered any results yet.
     */
    Optional<Pair<Integer, Integer>> getScoreFromPlayer(String playerName);
}
