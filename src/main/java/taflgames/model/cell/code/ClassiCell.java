package taflgames.model.cell.code;

import java.util.List;
import taflgames.common.code.Position;
import taflgames.model.cell.api.Cell;

public class ClassiCell implements Cell{

    @Override
    public void notify(Position source, Position sender, List<String> events) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean canAccept() {
        return true;
    }
    
}
