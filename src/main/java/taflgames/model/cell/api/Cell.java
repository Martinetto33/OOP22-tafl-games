package taflgames.model.cell.api;
import java.util.List;

import taflgames.common.code.Position;

public interface Cell {
    
    public void notify(Position source, Position sender, List<String> events);

    public boolean canAccept();

}
