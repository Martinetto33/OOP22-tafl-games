package taflgames;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.yaml.snakeyaml.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class used to create a file for the leaderboard.
 */
public final class Installer {
    private static final String PATH = System.getProperty("user.home");
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
        return new File(PATH + File.separator + FILE_NAME).isFile();
    }

    /**
     * Attempts to create the file for the leaderboard.
     * @return ad
     */
    public static boolean createFile() {
        /* final File folder = new File(PATH);
        boolean b = folder.mkdir();
        System.out.println("Created folder? " + b); */
        final File leaderboardFile = new File(PATH + File.separator + FILE_NAME);
        try (FileWriter writer = new FileWriter(leaderboardFile, StandardCharsets.UTF_8)) {
            final Yaml yaml = new Yaml();
            yaml.dump(Collections.emptyMap(), writer);
        } catch (IOException e) {
            LOGGER.error("Error in installer while trying to create folder", e);
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
