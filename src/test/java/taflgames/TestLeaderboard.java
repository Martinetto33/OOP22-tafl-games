package taflgames;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import taflgames.common.code.Pair;
import taflgames.controller.leaderboard.api.Leaderboard;
import taflgames.controller.leaderboard.code.LeaderBoardImpl;
import taflgames.controller.leaderboard.code.LeaderboardSaverImpl;
import taflgames.controller.leaderboard.api.LeaderboardSaver;
import taflgames.common.code.MatchResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

//CHECKSTYLE: MagicNumber OFF
/*Magic numbers checks disabled in order to allow quicker writing of the tests; the
* numbers used in the creation of Positions and results are not intended to be 
* constants, but only results to verify computations by need.
*/
/**
 * Tests the implementation of the leaderboard.
 */
class TestLeaderboard {

    private static final int MAP_SIZE = 6;
    private static final Map<String, Pair<Integer, Integer>> EXPECTED_RESULTS = new HashMap<>();
    private static Leaderboard sampleLeaderboard;

    /**
     * Initialises the Map. This fake test should start before any other one.
     */
    @BeforeAll
    static void initialise() {
        TestLeaderboard.EXPECTED_RESULTS.put("Alin Bordeianu", new Pair<>(0, 10));
        TestLeaderboard.EXPECTED_RESULTS.put("Elena Boschetti", new Pair<>(3, 3));
        TestLeaderboard.EXPECTED_RESULTS.put("Andrea Piermattei", new Pair<>(4, 2));
        TestLeaderboard.EXPECTED_RESULTS.put("Margherita Raponi", new Pair<>(10, 0));
        TestLeaderboard.EXPECTED_RESULTS.put("Personaggio fittizio", new Pair<>(0, 0));
        TestLeaderboard.EXPECTED_RESULTS.put("Qualcuno di molto scarso", new Pair<>(0, 11));
        assertEquals(TestLeaderboard.MAP_SIZE, TestLeaderboard.EXPECTED_RESULTS.size());
        TestLeaderboard.sampleLeaderboard = new LeaderBoardImpl();

        for (final var result : TestLeaderboard.EXPECTED_RESULTS.entrySet()) {
            final String playerName = result.getKey();
            final List<MatchResult> a = new ArrayList<>();
            /*In case the player has 0 victories and 0 losses, and they have never
             * been registered before, it is assumed that their first match or streak
             * of matches all resulted in a draw. In this case i added the 'continue'
             * clause because otherwise the IntStreams would not be able to generate any
             * element in the range (0, 0).
             */
            if (TestLeaderboard.EXPECTED_RESULTS.get(playerName).getX() == 0 
                && TestLeaderboard.EXPECTED_RESULTS.get(playerName).getY() == 0) {
                    sampleLeaderboard.addResult(playerName, MatchResult.DRAW);
                    continue;
               }
            a.addAll(List.of(IntStream.range(0, TestLeaderboard.EXPECTED_RESULTS.get(playerName).getX())
                              .mapToObj(elem -> MatchResult.VICTORY)
                              .toList(),
                             IntStream.range(0, TestLeaderboard.EXPECTED_RESULTS.get(playerName).getY())
                              .mapToObj(elem -> MatchResult.DEFEAT)
                              .toList())
                         .stream()
                         .flatMap(e -> e.stream())
                         .toList());
            a.stream()
                    .forEach(e -> sampleLeaderboard.addResult(playerName, e));
        }
        assertTrue(sampleLeaderboard.getLeaderboard().keySet().containsAll(TestLeaderboard.EXPECTED_RESULTS.keySet()));
        assertTrue(sampleLeaderboard.getLeaderboard().values().containsAll(TestLeaderboard.EXPECTED_RESULTS.values()));
    }

    /**
     * Tests the addition of new results and the correct
     * registration of the players.
     */
    @Test
    void testResultsRegistration() {
        final Leaderboard leaderboard = new LeaderBoardImpl();
        final String player1 = "Odin";
        final String player2 = "Thor";
        leaderboard.addResult(player1, MatchResult.VICTORY);
        leaderboard.addResult(player1, MatchResult.DEFEAT);
        leaderboard.addResult(player1, MatchResult.DRAW);
        assertEquals(new Pair<>(1, 1), leaderboard.getScoreFromPlayer(player1).get());
        assertTrue(leaderboard.getScoreFromPlayer(player2).isEmpty());
        leaderboard.addResult(player2, MatchResult.DRAW);
        assertFalse(leaderboard.getScoreFromPlayer(player2).isEmpty());
    }

    /**
     * Tests the "getLeaderboard()" method, which should return a map
     * in which the keys are ordered according to the number of wins of the player.
     * If two players have the same number of wins, they are ordered according
     * to the number of losses, in descending order.
     */
    @Test
    void testOrderedLeaderboard() {
        assertEquals(TestLeaderboard.sampleLeaderboard.getLeaderboard().keySet().stream().toList(),
                     List.of("Margherita Raponi", "Andrea Piermattei", "Elena Boschetti", "Personaggio fittizio",
                     "Alin Bordeianu", "Qualcuno di molto scarso"));
    }

    /**
     * Tests the saving of the Leaderboard to file and its retrieval from file.
     */
    @Test
    void testSave() {
        final LeaderboardSaver saver = new LeaderboardSaverImpl();
        //saver.setPath(saver.getTestPath()); //working directory of test classes differs from the one of the main classes
        saver.saveLeaderboard(TestLeaderboard.sampleLeaderboard);
        Leaderboard sixElementsLeaderboard = saver.retrieveFromSave();
        assertEquals(TestLeaderboard.MAP_SIZE, sixElementsLeaderboard.getLeaderboard().size());
        sixElementsLeaderboard.addResult("Fenrir", MatchResult.DEFEAT);
        sixElementsLeaderboard.saveToFile(saver);
        sixElementsLeaderboard = saver.retrieveFromSave();
        assertTrue(sixElementsLeaderboard.getLeaderboard().containsKey("Fenrir"));
        /* The clear method empties the results map, so an empty map will be saved to file.
         * The method "fromMapWithListValues" checks if the map is empty and returns
         * a new empty map if that is the case, otherwise throws
         * an exception if the format doesn't match the requirements.
         */
        sixElementsLeaderboard.clearLeaderboard();
        assertEquals(TestLeaderboard.MAP_SIZE, TestLeaderboard.EXPECTED_RESULTS.size());
        sixElementsLeaderboard.saveToFile(saver);
        sixElementsLeaderboard = saver.retrieveFromSave();
        assertEquals(0, sixElementsLeaderboard.getLeaderboard().size());
    }

    /**
     * Tests the behaviour of the application if no save file is found and "retrieveFromSave()"
     * is actually called.
     */
    @Test
    void testIfNoLeaderboardExists() {
        final LeaderboardSaver l = new LeaderboardSaverImpl();
        l.saveLeaderboard(new LeaderBoardImpl());
        final Leaderboard lead = l.retrieveFromSave();
        assertTrue(lead.getLeaderboard().isEmpty());
    }
}
