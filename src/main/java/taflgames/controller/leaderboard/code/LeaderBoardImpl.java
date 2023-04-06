package taflgames.controller.leaderboard.code;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import taflgames.common.code.MatchResult;
import taflgames.common.code.Pair;
import taflgames.controller.leaderboard.api.Leaderboard;
import taflgames.controller.leaderboard.api.LeaderboardSaver;

/**
 * This class models a Leaderboard for Hnefatafl games.
 */
public class LeaderBoardImpl implements Leaderboard {

    private Map<String, Pair<Integer, Integer>> results;
    private static final int EXPECTED_LIST_DIMENSION = 2;

    /**
     * Builds a new Leaderboard.
     */
    public LeaderBoardImpl() {
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
        final Map<String, Pair<Integer, Integer>> result = new LinkedHashMap<>();
        this.results.entrySet().stream()
            .sorted((entry1, entry2) -> {
                final Integer firstEntryWins = entry1.getValue().getX();
                final Integer secondEntryWins = entry2.getValue().getX();
                if (!firstEntryWins.equals(secondEntryWins)) {
                    return secondEntryWins - firstEntryWins;
                }
                /*If the win scores are the same, the entries are sorted according to the
                 * number of losses in descending order.
                 */
                return entry1.getValue().getY() - entry2.getValue().getY();
            })
            .forEachOrdered(entry -> result.put(entry.getKey(), entry.getValue()));
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Pair<Integer, Integer>> getScoreFromPlayer(final String playerName) {
        return this.results.containsKey(playerName) ? Optional.of(this.results.get(playerName))
                                                    : Optional.empty();
    }

    /*Draws are recorded but do not affect the player's result */
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
    public void saveToFile(final LeaderboardSaver saver) {
        saver.saveLeaderboard(this);
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
     * the results. Throws an {@link java.lang.IllegalArgumentException} if not all
     * the lists corresponding to the players' names have exactly 2 elements (one
     * for the wins and one for the losses)
     * @param map the map from which to take the entries
     */
    public void fromMapWithListValues(final Map<String, List<Integer>> map) {
        if (map == null || map.isEmpty()) {
            this.results = new HashMap<>();
            return;
        }
        if (map.values().stream().anyMatch(list -> list.size() != LeaderBoardImpl.EXPECTED_LIST_DIMENSION)) {
            throw new IllegalArgumentException("Not all entries of the read map have a score for both wins and losses");
        }
        this.results = map.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> new Pair<>(entry.getValue().get(0),
                                                                                 entry.getValue().get(1))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPlayerResultAsString(final String playerName) {
        if (!this.results.containsKey(playerName)) {
            return new StringBuilder()
                .append(playerName)
                .append(": no scores found")
                .toString();
        }
        return new StringBuilder()
            .append(playerName)
            .append(" - WINS: ")
            .append(this.results.get(playerName).getX())
            .append(", LOSSES: ")
            .append(this.results.get(playerName).getY())
            .toString();
    }
}

