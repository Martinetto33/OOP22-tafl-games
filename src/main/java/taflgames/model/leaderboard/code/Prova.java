package taflgames.model.leaderboard.code;

import java.util.LinkedHashMap;
import java.util.Map;

import taflgames.common.code.MatchResult;
import taflgames.model.leaderboard.api.Leaderboard;

//TODO: move this code into the test class
public class Prova {
    private static Map<String, MatchResult> expectedResults = new LinkedHashMap<>();
    private static final String SEP = System.getProperty("file.separator");
    public static void main(String... args) {
        var l = new LeaderboardSaverImpl();
        Prova.expectedResults.put("Alin Bordeianu", MatchResult.VICTORY);
        Prova.expectedResults.put("Elena Boschetti", MatchResult.VICTORY);
        Prova.expectedResults.put("Andrea Piermattei", MatchResult.VICTORY);
        Prova.expectedResults.put("Margherita Raponi", MatchResult.VICTORY);
        Prova.expectedResults.put("Personaggio fittizio", MatchResult.VICTORY);
        Leaderboard lead = new LeaderBoardImpl();
        Prova.expectedResults.entrySet().forEach(entry -> lead.addResult(entry.getKey(), entry.getValue()));
        //l.saveLeaderboard(lead);
        Leaderboard lead2 = l.retrieveFromSave();
        System.out.println(lead2.getLeaderboard().keySet().stream().map(e -> lead2.getPlayerResultAsString(e)).toList());
        l.saveLeaderboard(lead);
        Leaderboard lead3 = l.retrieveFromSave();
        System.out.println(lead3.getLeaderboard().keySet().stream().map(e -> lead3.getPlayerResultAsString(e)).toList());
    }
}
