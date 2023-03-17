package taflgames.common.code;

import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.representer.BaseRepresenter;
import org.yaml.snakeyaml.representer.Represent;

/*WARNING: QUESTA CLASSE E' SOLO UN TEST E POTREBBE ESSERE CANCELLATA */
public class PairRepresenterYaml extends BaseRepresenter {
    
    public PairRepresenterYaml() {
        this.representers.put(Pair.class, nullRepresenter);
    }

    private class PairRepresent implements Represent {
        @Override
        public Node representData(Object data) {
            final Pair<Integer, Integer> p = (Pair<Integer, Integer>) data;
            String pair = new StringBuilder()
                                .append(String.valueOf(p.getX()))
                                .append(", ")
                                .append(String.valueOf(p.getY()))
                                .toString();
            return null;
        }

    }
}
