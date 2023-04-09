package taflgames.controller.leaderboard.code;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.yaml.snakeyaml.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import taflgames.Installer;
import taflgames.common.code.Pair;
import taflgames.controller.leaderboard.api.Leaderboard;
import taflgames.controller.leaderboard.api.LeaderboardSaver;

/**
 * This is the reification of a {@link taflgames.model.leaderboard.api.LeaderboardSaver}.
 * It provides one method for saving a leaderboard to a file and one to retrieve a
 * leaderboard from a file, if it exists.
 */
public class LeaderboardSaverImpl implements LeaderboardSaver {

    private static final String LEADERBOARD_SAVE_FILE_NAME = "leaderboard.yaml";
    private static final Logger LOGGER = LoggerFactory.getLogger(LeaderboardSaverImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveLeaderboard(final Leaderboard leaderboard) {
        final Map<String, Pair<Integer, Integer>> internalMap = leaderboard.getLeaderboard();
        final Map<String, List<Integer>> otherMap = new LinkedHashMap<>();
        internalMap.entrySet().stream()
                .map(entry -> new Pair<String, List<Integer>>(entry.getKey(), 
                    List.of(entry.getValue().getX(), entry.getValue().getY())))
                .forEach(pair -> otherMap.put(pair.getX(), pair.getY()));
        try {
            final File leaderboardFile = new File(Installer.getFilePath() + File.separator + LEADERBOARD_SAVE_FILE_NAME);
            try (FileWriter writer = new FileWriter(leaderboardFile, StandardCharsets.UTF_8)) {
                final Yaml yaml = new Yaml();
                yaml.dump(otherMap, writer);
            }
        } catch (IOException e) {
            LOGGER.error("Error while trying to access the save file for the leaderboard.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Leaderboard retrieveFromSave() {
        try (FileInputStream inputStream = Objects.requireNonNull(new FileInputStream(
                new File(Installer.getFilePath() + File.separator + LEADERBOARD_SAVE_FILE_NAME)
            ))) {
            final Yaml yaml = new Yaml();
            final LeaderBoardImpl leaderboard = new LeaderBoardImpl();
            leaderboard.fromMapWithListValues(yaml.load(inputStream));
            return leaderboard;
        } catch (IOException e) {
            LOGGER.error("Error while trying to read from the save file for the leaderboard.", e);
            return new LeaderBoardImpl();
        }
    }
}
