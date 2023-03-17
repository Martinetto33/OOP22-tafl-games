package taflgames.model.leaderboard.code;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import taflgames.common.code.MatchResult;
import taflgames.common.code.Pair;
import taflgames.model.Match;
import taflgames.model.leaderboard.api.Leaderboard;

public class Prova {
    private static Map<String, MatchResult> expectedResults = new LinkedHashMap<>();
    private static final String SEP = System.getProperty("file.separator");
    private static final String PATH = System.getProperty("user.dir") + SEP + "tafl-games" + SEP + "src"
    + SEP + "main" + SEP + "resources" + SEP + "taflgames" + SEP + "leaderboardSave" + SEP;
    private static final String LEADERBOARD_SAVE_FILE_NAME = "leaderboard.yaml" + SEP;
    
    public static void main(String... args) {
        var l = new LeadBoAnotherAttempt();
        Prova.expectedResults.put("Alin Bordeianu", MatchResult.VICTORY);
        Prova.expectedResults.put("Elena Boschetti", MatchResult.VICTORY);
        Prova.expectedResults.put("Andrea Piermattei", MatchResult.VICTORY);
        Prova.expectedResults.put("Margherita Raponi", MatchResult.VICTORY);
        Prova.expectedResults.put("Personaggio fittizio", MatchResult.VICTORY);
        Leaderboard lead = new LeaderBoardImpl();
        Prova.expectedResults.entrySet().forEach(entry -> lead.addResult(entry.getKey(), entry.getValue()));
        l.saveLeaderboard(lead);
        System.out.println(l.retrieveFromSave().getLeaderboard());
    }
    public <T> void saveLeaderboard(T element) {
        /* There's no reason for keeping old leaderboards,
         * so the FileWriter has 'false' as append parameter:
         * this way, old leaderboards will be overwritten.
        */
        try (FileWriter writer = new FileWriter(PATH + LEADERBOARD_SAVE_FILE_NAME, false)) {
            final Yaml yaml = new Yaml();
            yaml.dump(element, writer);
        } catch (IOException e) {
            System.out.println("Error while trying to access the save file for the leaderboard.");
            e.printStackTrace();
        }
    }

    public <T> T retrieveFromSave() {
        try (InputStream inputStream = new FileInputStream(PATH + LEADERBOARD_SAVE_FILE_NAME)) {
            Yaml yaml = new Yaml(new Constructor(getClass(), new LoaderOptions()));
            T t = yaml.load(inputStream);
            return t;
        } catch (IOException e) {
            System.out.println("Error while trying to read from the save file for the leaderboard.");
            e.printStackTrace();
        }
        return null;
    }
}
