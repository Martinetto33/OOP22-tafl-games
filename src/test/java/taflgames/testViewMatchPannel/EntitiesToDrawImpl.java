package taflgames.testViewMatchPannel;

import java.util.*;
import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.common.code.VectorImpl;
import taflgames.view.scenes.CellImageInfo;
import taflgames.view.scenes.PieceImageInfo;


public class EntitiesToDrawImpl implements EntitiesToDraw{

    public EntitiesToDrawImpl() {
    }

    Map<Position,PieceImageInfo> piecesAlive = new HashMap<>();
    Map<Position, CellImageInfo> backgroundCells = new HashMap<>();
    Map<Position, CellImageInfo> specialCells = new HashMap<>();

    public void createPiecesAlive() {
        this.piecesAlive.put(new Position(3, 2), new PieceImageInfo("BASIC_PIECE", Player.ATTACKER));
        this.piecesAlive.put(new Position(7, 2), new PieceImageInfo("ARCHER", Player.DEFENDER));
        this.piecesAlive.put(new Position(5, 5), new PieceImageInfo("BASIC_PIECE", Player.DEFENDER));
        this.piecesAlive.put(new Position(0, 0), new PieceImageInfo("BASIC_PIECE", Player.DEFENDER));
        this.piecesAlive.put(new Position(9, 4), new PieceImageInfo("BASIC_PIECE", Player.DEFENDER));
    }

    public void createBackgroundCells() {
       this.backgroundCells.put(new Position(3, 2), new CellImageInfo("CELL_BASIC", null,new VectorImpl(0, 0)));
       this.backgroundCells.put(new Position(5, 7), new CellImageInfo("CELL_THRONE", null, new VectorImpl(0, 0)));
       this.backgroundCells.put(new Position(8, 8), new CellImageInfo("CELL_BASIC", null, new VectorImpl(0, 0)));
       this.backgroundCells.put(new Position(1, 7), new CellImageInfo("CELL_BASIC", null, new VectorImpl(0, 0)));
    }

    public void createSpecialCells() {
       this.specialCells.put(new Position(1, 8), new CellImageInfo("CELL_SLIDER", null, new VectorImpl(-1, 0)));
       this.specialCells.put(new Position(8, 7), new CellImageInfo("CELL_SLIDER", null, new VectorImpl(0, -1)));
       this.specialCells.put(new Position(5, 7), new CellImageInfo("CELL_SLIDER", null, new VectorImpl(1, 0)));
       this.specialCells.put(new Position(3, 7), new CellImageInfo("CELL_SLIDER", null, new VectorImpl(0, 1)));
    }

    public Map<Position,PieceImageInfo> getPiecesAlive() {
        return this.piecesAlive;
    }

    public Map<Position, CellImageInfo> getBackgroundCells() {
        return this.backgroundCells;
    }

    public Map<Position, CellImageInfo> getSpecialCells() {
        return this.specialCells;
    }
}
