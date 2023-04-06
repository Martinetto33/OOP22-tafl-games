package taflgames.testViewMatchPannel;

import java.util.HashMap;
import java.util.Map;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.common.code.VectorImpl;
import taflgames.view.scenes.CellImageInfo;
import taflgames.view.scenes.PieceImageInfo;

public final class EntitiesToDrawImpl implements EntitiesToDraw {

    private static final String BASIC_PIECE_NAME = "BASIC_PIECE";
    private static final String CELL_SLIDER_NAME = "CELL_SLIDER";

    private final Map<Position, PieceImageInfo> piecesAlive = new HashMap<>();
    private final Map<Position, CellImageInfo> backgroundCells = new HashMap<>();
    private final Map<Position, CellImageInfo> specialCells = new HashMap<>();

    // CHECKSTYLE: MagicNumber OFF
    // MagicNumber rule disabled because the numbers in the following code represent coordinates

    @Override
    public void createPiecesAlive() {
        this.piecesAlive.put(new Position(3, 2), new PieceImageInfo(BASIC_PIECE_NAME, Player.ATTACKER));
        this.piecesAlive.put(new Position(7, 2), new PieceImageInfo("ARCHER", Player.DEFENDER));
        this.piecesAlive.put(new Position(5, 5), new PieceImageInfo(BASIC_PIECE_NAME, Player.DEFENDER));
        this.piecesAlive.put(new Position(0, 0), new PieceImageInfo(BASIC_PIECE_NAME, Player.DEFENDER));
        this.piecesAlive.put(new Position(9, 4), new PieceImageInfo(BASIC_PIECE_NAME, Player.DEFENDER));
    }

    @Override
    public void createBackgroundCells() {
       this.backgroundCells.put(new Position(3, 2), new CellImageInfo("CELL_BASIC", null, new VectorImpl(0, 0)));
       this.backgroundCells.put(new Position(5, 7), new CellImageInfo("CELL_THRONE", null, new VectorImpl(0, 0)));
       this.backgroundCells.put(new Position(8, 8), new CellImageInfo("CELL_BASIC", null, new VectorImpl(0, 0)));
       this.backgroundCells.put(new Position(1, 7), new CellImageInfo("CELL_BASIC", null, new VectorImpl(0, 0)));
    }

    @Override
    public void createSpecialCells() {
       this.specialCells.put(new Position(1, 8), new CellImageInfo(CELL_SLIDER_NAME, null, new VectorImpl(-1, 0)));
       this.specialCells.put(new Position(8, 7), new CellImageInfo(CELL_SLIDER_NAME, null, new VectorImpl(0, -1)));
       this.specialCells.put(new Position(5, 7), new CellImageInfo(CELL_SLIDER_NAME, null, new VectorImpl(1, 0)));
       this.specialCells.put(new Position(3, 7), new CellImageInfo(CELL_SLIDER_NAME, null, new VectorImpl(0, 1)));
    }

    // CHECKSTYLE: MagicNumber ON

    @Override
    public Map<Position, PieceImageInfo> getPiecesAlive() {
        return this.piecesAlive;
    }

    @Override
    public Map<Position, CellImageInfo> getBackgroundCells() {
        return this.backgroundCells;
    }

    @Override
    public Map<Position, CellImageInfo> getSpecialCells() {
        return this.specialCells;
    }
}
