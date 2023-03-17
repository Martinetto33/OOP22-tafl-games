package taflgames.model.leaderboard.code;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import taflgames.common.code.Pair;
import taflgames.model.leaderboard.api.Leaderboard;
import taflgames.model.leaderboard.api.LeaderboardSaver;

public class LeaderboardSaverImpl implements LeaderboardSaver {
    private static final String SEP = System.getProperty("file.separator");
    private static final String PATH = System.getProperty("user.dir") + SEP + "tafl-games" + SEP + "src"
    + SEP + "main" + SEP + "resources" + SEP + "taflgames" + SEP + "leaderboardSave" + SEP;
    private static final String LEADERBOARD_SAVE_FILE_NAME = "leaderboard.yaml" + SEP;

    @Override
    public void saveLeaderboard(Leaderboard leaderboard) {
        Map<String, Pair<Integer, Integer>> internalMap = leaderboard.getLeaderboard();
        Map<String, List<Integer>> otherMap = new LinkedHashMap<>();
        internalMap.entrySet().stream()
                .map(entry -> new Pair<String, List<Integer>>(entry.getKey(), 
                    List.of(entry.getValue().getX(), entry.getValue().getY())))
                .forEach(pair -> otherMap.put(pair.getX(), pair.getY()));
        try (FileWriter writer = new FileWriter(PATH + LEADERBOARD_SAVE_FILE_NAME, false)) {
            final Yaml yaml = new Yaml();
            yaml.dump(otherMap, writer);
        } catch (IOException e) {
            System.out.println("Error while trying to access the save file for the leaderboard.");
            e.printStackTrace();
        }
    }

    @Override
    public Leaderboard retrieveFromSave() {
        try (InputStream inputStream = new FileInputStream(PATH + LEADERBOARD_SAVE_FILE_NAME)) {
            Yaml yaml = new Yaml();
            LeaderBoardImpl leaderboard = new LeaderBoardImpl();
            leaderboard.fromMapWithListValues(yaml.load(inputStream));
            return leaderboard;
        } catch (IOException e) {
            System.out.println("Error while trying to read from the save file for the leaderboard.");
            e.printStackTrace();
        }
        return null;
    }
    
}
