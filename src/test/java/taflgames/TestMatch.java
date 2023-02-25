package taflgames;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import taflgames.common.Player;
import taflgames.model.Match;
import taflgames.model.MatchImpl;

/**
 * JUnit tests for {@link Match}.
 */
class TestMatch {

    private Match match;

    /**
     * Initializes each test before its execution.
     */
    @BeforeEach
    void init() {
        match = new MatchImpl();
    }

    /**
     * Test the player turn queue.
     */
    @Test
    void testQueue() {
        final int turns = 4;
        final List<Player> resultingQueue = new LinkedList<>();
        for (int i = 0; i < turns; i++) {
            resultingQueue.add(match.getActivePlayer());
            match.setNextActivePlayer();
        }
        assertEquals(
            List.of(Player.ATTACKER, Player.DEFENDER, Player.ATTACKER, Player.DEFENDER), 
            resultingQueue
        );
    }

}
