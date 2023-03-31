package taflgames.testViewMatchPannel;

import java.util.*;
import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.view.scenes.CellImageInfo;
import taflgames.view.scenes.PieceImageInfo;


public class EntitiesToDrawImpl implements EntitiesToDraw{

    public EntitiesToDrawImpl() {
    }

    Map<Position,PieceImageInfo> piecesAlive = new HashMap<>();
    Map<Position, CellImageInfo> backgroundCells = new HashMap<>();

    public void createPiecesAlive() {
        this.piecesAlive.put(new Position(3, 2), new PieceImageInfo("BASIC_PIECE", Player.ATTACKER));
        this.piecesAlive.put(new Position(7, 2), new PieceImageInfo("ARCHER", Player.DEFENDER));
        this.piecesAlive.put(new Position(5, 5), new PieceImageInfo("BASIC_PIECE", Player.DEFENDER));
        this.piecesAlive.put(new Position(0, 0), new PieceImageInfo("BASIC_PIECE", Player.DEFENDER));
        this.piecesAlive.put(new Position(9, 4), new PieceImageInfo("BASIC_PIECE", Player.DEFENDER));
    }

    public void createBackgroundCells() {
       this.backgroundCells.put(new Position(3, 2), new CellImageInfo("CELL_BASIC", Player.DEFENDER, 0));
       this.backgroundCells.put(new Position(5, 7), new CellImageInfo("CELL_THRONE", Player.DEFENDER, 0));
       this.backgroundCells.put(new Position(8, 8), new CellImageInfo("CELL_BASIC", Player.DEFENDER, 0));
       this.backgroundCells.put(new Position(1, 1), new CellImageInfo("CELL_BASIC", Player.DEFENDER, 0));
    }

    public Map<Position,PieceImageInfo> getPiecesAlive() {
        return this.piecesAlive;
    }

    public Map<Position, CellImageInfo> getBackgroundCells() {
        return this.backgroundCells;
    }
}
