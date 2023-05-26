package taflgames.common;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import taflgames.common.code.Position;

/**
 * An utility class that groups some useful methods
 * (e.g. for positions set generation).
 */
public final class Utils {

    private Utils() {
        // Empty
    }

    /**
     * Creates a set containing all the positions of the board.
     * @param boardSize the size of the board
     * @return the set of positions
     */
    public static Set<Position> generateAllPositions(final int boardSize) {
        return  Stream.iterate(0, row -> row + 1).limit(boardSize)
                .map(row -> Stream.iterate(0, col -> col + 1).limit(boardSize)
                                .map(col -> new Position(row, col))
                                .collect(Collectors.toSet()))
                .collect(HashSet::new, HashSet::addAll, HashSet::addAll);
    }

}
