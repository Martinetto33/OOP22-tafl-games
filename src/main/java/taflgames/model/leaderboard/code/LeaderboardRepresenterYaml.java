package taflgames.model.leaderboard.code;

import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.representer.BaseRepresenter;
import org.yaml.snakeyaml.representer.Represent;

/**
 * This class helps the Yaml dumper save {@link taflgames.model.leaderboard.code.LeaderBoardImpl}
 * type objects.
 */
public class LeaderboardRepresenterYaml extends BaseRepresenter {
    public LeaderboardRepresenterYaml() {
        this.representers.put(LeaderBoardImpl.class, nullRepresenter);
    }

    private class LeaderboardRepresent implements Represent {

        @Override
        public Node representData(Object data) {
            LeaderBoardImpl leaderboard = (LeaderBoardImpl) data;
            //TODO, first I need to do a Pair representer
            return null;
        }

    }
}
