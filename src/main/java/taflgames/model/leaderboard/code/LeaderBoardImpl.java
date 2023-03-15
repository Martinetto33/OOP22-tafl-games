package taflgames.model.leaderboard.code;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import taflgames.common.code.MatchResult;
import taflgames.common.code.Pair;
import taflgames.model.leaderboard.api.Leaderboard;

/**
 * This class models a Leaderboard for Hnefatafl games.
 */
public class LeaderBoardImpl implements Leaderboard {

    private Map<String, Pair<Integer, Integer>> results;

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
}
