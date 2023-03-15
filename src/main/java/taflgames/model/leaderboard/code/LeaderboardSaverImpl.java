package taflgames.model.leaderboard.code;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import taflgames.model.leaderboard.api.Leaderboard;
import taflgames.model.leaderboard.api.LeaderboardSaver;

/**
 * This class allows to save {@link taflgames.model.leaderboard.api.Leaderboard} type objects
 * to .yaml files.
 */
public class LeaderboardSaverImpl implements LeaderboardSaver {

    private static final String SEP = System.getProperty("file.separator");
    private static final String PATH = "taflgames" + SEP + "leaderboardSave" + SEP;
    private static final String LEADERBOARD_SAVE_FILE_NAME = "leaderboard.save";
    /**
     * {@inheritDoc}
     */
    @Override
    public void saveLeaderboard(Leaderboard leaderboard) {
        try (PrintWriter printWriter = new PrintWriter(new File(PATH + LEADERBOARD_SAVE_FILE_NAME))) {
            final Yaml yaml = new Yaml();
            yaml.dump(leaderboard, printWriter); //TODO: old leaderboards should be overwritten but this method only appends new data
        } catch (IOException e) {
            System.out.println("Error while trying to access the save file for the leaderboard.");
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Leaderboard retrieveFromSave() {
        try (InputStream inputStream = new FileInputStream(PATH + LEADERBOARD_SAVE_FILE_NAME)) {
            Yaml yaml = new Yaml(new Constructor(LeaderBoardImpl.class, null));
            LeaderBoardImpl leaderboard = yaml.load(inputStream);
            return leaderboard;
        } catch (IOException e) {
            System.out.println("Error while trying to read from the save file for the leaderboard.");
            e.printStackTrace();
        }
        return null;
    }
    
}
