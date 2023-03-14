package taflgames;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import taflgames.common.code.Pair;

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
    private Map<String, Pair<Integer, Integer>> results = new HashMap<>();

    /**
     * Initialises the Map. This fake test should start before any other one.
     */
    @Test
    @Order(1)
    void initialise() {
        this.results.put("Alin Bordeianu", new Pair<>(0, 10));
        this.results.put("Elena Boschetti", new Pair<>(3, 3));
        this.results.put("Andrea Piermattei", new Pair<>(4, 2));
        this.results.put("Margherita Raponi", new Pair<>(10, 0));
        this.results.put("Personaggio fittizio", new Pair<>(0, 0));
        assertEquals(5, this.results.size());
    }

    
}
