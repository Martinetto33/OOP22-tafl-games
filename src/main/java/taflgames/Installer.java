package taflgames;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import org.yaml.snakeyaml.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class used to create a file for the leaderboard.
 */
public final class Installer {
    private static final String SEP = File.separator;
    private static final String PATH = System.getProperty("user.home") + SEP + ".tafl-games";
    private static final String FILE_NAME = "leaderboard.yaml";

    private static final Logger LOGGER = LoggerFactory.getLogger(Installer.class);

    /* Used to hide default constructor; needed to avoid checkstyle warnings. */
    private Installer() {

    }

    /**
     * Tests if the expected file exists.
     * @return true if the file exists.
     */
    public static boolean doesFileExist() {
        return Files.exists(Paths.get(PATH + File.separator + FILE_NAME));
    }

    /**
     * Tests if the expected directory exists.
     * @return true if the directory exists.
     */
    public static boolean doesDirectoryExist() {
        return Files.isDirectory(Paths.get(PATH));
    }

    /**
     * Attempts to create the file for the leaderboard.
     * @return true if the file was successfully created.
     */
    public static boolean createFile() {
        if (!doesDirectoryExist()) {
            try {
                final File leaderboardDirectory = new File(PATH);
                if (!leaderboardDirectory.mkdir()) {
                    throw new FileNotFoundException();
                }
                LOGGER.info("Leaderboard directory successfully created.");
            } catch (FileNotFoundException e) {
                LOGGER.error("Could not create directory for leaderboard file.", e);
            }
        }
        if (!doesFileExist()) {
            final File leaderboardFile = new File(PATH + File.separator + FILE_NAME);
            try (FileWriter writer = new FileWriter(leaderboardFile, StandardCharsets.UTF_8)) {
                final Yaml yaml = new Yaml();
                yaml.dump(Collections.emptyMap(), writer);
                LOGGER.info("File 'leaderboard.yaml' at " + leaderboardFile.getAbsolutePath() + " successfully created.");
            } catch (IOException e) {
                LOGGER.error("Error in installer while trying to create file", e);
            }
        }
        return doesFileExist();
    }

    /**
     * Returns the path to which the file was created.
     * @return the path of the leaderboard file.
     */
    public static String getFilePath() {
        return PATH;
    }
}
