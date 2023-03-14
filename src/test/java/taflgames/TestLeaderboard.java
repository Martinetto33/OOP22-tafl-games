package taflgames;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import taflgames.common.code.Pair;
import taflgames.model.leaderboard.api.Leaderboard;
import taflgames.model.leaderboard.code.LeaderBoardImpl;
import taflgames.common.code.MatchResult;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Tests the implementation of the leaderboard.
 * TODO: expand with saves on files and reads from files
 */
//CHECKSTYLE: MagicNumber OFF
/*Magic numbers checks disabled in order to allow quicker writing of the tests; the
 * numbers used in the creation of Positions and results are not intended to be 
 * constants, but only results to verify computations by need.
 */
public class TestLeaderboard {

    private static final int MAP_SIZE = 5;
    private static Map<String, Pair<Integer, Integer>> expectedResults = new HashMap<>();
    private static Leaderboard leaderboard;

    /**
     * Initialises the Map. This fake test should start before any other one.
     */
    @BeforeAll
    static void initialise() {
        TestLeaderboard.expectedResults.put("Alin Bordeianu", new Pair<>(0, 10));
        TestLeaderboard.expectedResults.put("Elena Boschetti", new Pair<>(3, 3));
        TestLeaderboard.expectedResults.put("Andrea Piermattei", new Pair<>(4, 2));
        TestLeaderboard.expectedResults.put("Margherita Raponi", new Pair<>(10, 0));
        TestLeaderboard.expectedResults.put("Personaggio fittizio", new Pair<>(0, 0));
        assertEquals(TestLeaderboard.MAP_SIZE, TestLeaderboard.expectedResults.size());
        TestLeaderboard.leaderboard = new LeaderBoardImpl();

        for (final var playerName : TestLeaderboard.expectedResults.keySet()) {
            System.out.println(playerName);
            List<MatchResult> a = new ArrayList<>();
            /*In case the player has 0 victories and 0 losses, and they have never
             * been registered before, it is assumed that their first match or streak
             * of matches all resulted in a draw. In this case i added the 'continue'
             * clause because otherwise the IntStreams would not be able to generate any
             * element in the range (0, 0).
             */
            if(TestLeaderboard.expectedResults.get(playerName).getX() == 0 &&
               TestLeaderboard.expectedResults.get(playerName).getY() == 0) {
                    leaderboard.addResult(playerName, MatchResult.DRAW);
                    continue;
               }
            a.addAll(List.of(IntStream.range(0, TestLeaderboard.expectedResults.get(playerName).getX())
                              .mapToObj(elem -> MatchResult.VICTORY)
                              .toList(),
                             IntStream.range(0, TestLeaderboard.expectedResults.get(playerName).getY())
                              .mapToObj(elem -> MatchResult.DEFEAT)
                              .toList())
                         .stream()
                         .flatMap(e -> e.stream())
                         .toList());
            a.stream()
                    .forEach(e -> TestLeaderboard.leaderboard.addResult(playerName, e));
        }
        System.out.println(TestLeaderboard.expectedResults.keySet());
        System.out.println(TestLeaderboard.leaderboard.getLeaderboard().keySet());
        System.out.println(TestLeaderboard.leaderboard.getLeaderboard());
        assertTrue(TestLeaderboard.leaderboard.getLeaderboard().keySet().containsAll(TestLeaderboard.expectedResults.keySet()));
        assertTrue(TestLeaderboard.leaderboard.getLeaderboard().values().containsAll(TestLeaderboard.expectedResults.values()));
    }

    /**
     * Tests the addition of new results and the correct
     * registration of the players.
     */
    @Test
    void testResultsRegistration() {
        leaderboard.addResult("Odin", MatchResult.VICTORY);
        leaderboard.addResult("Odin", MatchResult.DEFEAT);
        leaderboard.addResult("Odin", MatchResult.DRAW);
        assertEquals(new Pair<>(1, 1), leaderboard.getScoreFromPlayer("Odin").get());
    }

}
