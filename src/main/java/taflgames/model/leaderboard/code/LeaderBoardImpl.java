package taflgames.model.leaderboard.code;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import taflgames.common.code.MatchResult;
import taflgames.common.code.Pair;
import taflgames.model.leaderboard.api.Leaderboard;
import taflgames.model.leaderboard.api.LeaderboardSaver;

/**
 * This class models a Leaderboard for Hnefatafl games.
 */
public class LeaderBoardImpl implements Leaderboard {

    private Map<String, Pair<Integer, Integer>> results;
    private final LeaderboardSaver saver;
    private static final int EXPECTED_LIST_DIMENSION = 2;

    /**
     * Builds a new Leaderboard.
     */
    public LeaderBoardImpl() {
        this.saver = new LeaderboardSaverImpl();
        this.results = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addResult(final String playerName, final MatchResult result) {
        this.results.put(playerName, this.results.containsKey(playerName) ? this.evaluate(this.results.get(playerName), result) 
                                                                          : this.evaluate(new Pair<>(0, 0), result));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Pair<Integer, Integer>> getLeaderboard() {
        return this.results.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().getX() - entry1.getValue().getX())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Pair<Integer, Integer>> getScoreFromPlayer(final String playerName) {
        return this.results.containsKey(playerName) ? Optional.of(this.results.get(playerName))
                                                    : Optional.empty();
    }

    /*Draws are not recorded and do not affect the player's result */
    private Pair<Integer, Integer> evaluate(final Pair<Integer, Integer> score, final MatchResult result) {
        switch (result) {
            case VICTORY: return new Pair<>(score.getX() + 1, score.getY());
            case DEFEAT: return new Pair<>(score.getX(), score.getY() + 1);
            case DRAW: return score;
            default: return score;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearLeaderboard() {
        this.results.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveToFile() {
        this.saver.saveLeaderboard(this);
    }

    /**
     * Adds all the entries from a map of String-Pair<Integer, Integer> into
     * the results.
     * @param map the map from which to take the entries
     */
    public void fromMap(final Map<String, Pair<Integer, Integer>> map) {
        this.results.putAll(map);
    }

    /**
     * Adds all the entries from a map of String-List<Integer> into
     * the results.
     * @param map the map from which to take the entries
     */
    public void fromMapWithListValues(final Map<String, List<Integer>> map) {
        if (map.values().stream().noneMatch(list -> list.size() == LeaderBoardImpl.EXPECTED_LIST_DIMENSION)) {
            throw new IllegalArgumentException("Not all entries of the read map have a score for both wins and losses");
        }
        this.results = map.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> new Pair<>(entry.getValue().get(0),
                                                                                 entry.getValue().get(1))));
    }

    /**
     * {@inheritDoc}
     */
    public String getPlayerResultAsString(final String playerName) {
        if (!this.results.containsKey(playerName)) {
            return new StringBuilder()
                .append(playerName)
                .append(": no scores found")
                .toString();
        }
        return new StringBuilder()
            .append(playerName)
            .append("- WINS: ")
            .append(this.results.get(playerName).getX())
            .append(", LOSSES: ")
            .append(this.results.get(playerName).getY())
            .toString();
    }
}

