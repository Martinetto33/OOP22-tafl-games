package taflgames.controller.leaderboard.code;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import taflgames.common.code.Pair;
import taflgames.controller.leaderboard.api.Leaderboard;
import taflgames.controller.leaderboard.api.LeaderboardSaver;

/**
 * This is the reification of a {@link taflgames.model.leaderboard.api.LeaderboardSaver}.
 * It provides one method for saving a leaderboard to a file and one to retrieve a
 * leaderboard from a file, if it exists.
 */
public class LeaderboardSaverImpl implements LeaderboardSaver {
    private static final String SEP = System.getProperty("file.separator");
    private static final String DEFAULT_PATH = System.getProperty("user.dir") + SEP + "src"
    + SEP + "main" + SEP + "resources" + SEP + "taflgames" + SEP + "leaderboardSave" + SEP;
    private static final String LEADERBOARD_SAVE_FILE_NAME = "leaderboard.yaml" + SEP;
    private static final String TEST_PATH = System.getProperty("user.dir") + SEP + "src"
    + SEP + "main" + SEP + "resources" + SEP + "taflgames" + SEP + "leaderboardSave" + SEP;
    private String chosenPath = LeaderboardSaverImpl.DEFAULT_PATH + LEADERBOARD_SAVE_FILE_NAME;
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
        if (!Files.exists(Paths.get(this.chosenPath))) {
                new File(this.chosenPath);
                LOGGER.info("New file created");
        }
        try (FileWriter writer = new FileWriter(this.chosenPath, false)) {
            final Yaml yaml = new Yaml();
            yaml.dump(otherMap, writer);
        } catch (IOException e) {
            LOGGER.info("Error while trying to access the save file for the leaderboard.");
            LOGGER.error("Exception occurred!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Leaderboard retrieveFromSave() {
        if (!Files.exists(Paths.get(this.chosenPath))) {
            new File(this.chosenPath);
            LOGGER.info("New file created");
        }
        try (InputStream inputStream = new FileInputStream(this.chosenPath)) {
            final Yaml yaml = new Yaml();
            final LeaderBoardImpl leaderboard = new LeaderBoardImpl();
            leaderboard.fromMapWithListValues(yaml.load(inputStream));
            return leaderboard;
        } catch (IOException e) {
            LOGGER.error("Error while trying to read from the save file for the leaderboard.", e);
            return new LeaderBoardImpl();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPath(final String path) {
        if (!LeaderboardSaverImpl.DEFAULT_PATH.equals(path) && !LeaderboardSaverImpl.TEST_PATH.equals(path)) {
            return;
        }
        this.chosenPath = path;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDefaultPath() {
        return DEFAULT_PATH + LEADERBOARD_SAVE_FILE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTestPath() {
        return TEST_PATH + LEADERBOARD_SAVE_FILE_NAME;
    }

}
