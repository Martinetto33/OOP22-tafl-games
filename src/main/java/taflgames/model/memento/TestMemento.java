package taflgames.model.memento;

import java.util.HashMap;
import java.util.Map;

import taflgames.common.code.Position;
import taflgames.common.code.VectorImpl;

public class TestMemento {
    public static void main(String... args) {
        CellExample cell1 = new CellExample("free");
        CellExample cell2 = new CellExample("free");
        CellExample cell3 = new CellExample("occupied");

        PieceExample piece1 = new PieceExample(true, "Attacker");
        PieceExample piece2 = new PieceExample(true, "Defender");

        Map<Position, PieceExample> piecesPos = new HashMap<>();
        piecesPos.put(new Position(0, 0), piece1);
        piecesPos.put(new Position(1, 1), piece2);

        Map<Position, CellExample> cellsPos = new HashMap<>();
        cellsPos.put(new Position(0, 0), cell1);
        cellsPos.put(new Position(5, 5), cell2);
        cellsPos.put(new Position(3, 4), cell3);

        BoardExample board = new BoardExample(piecesPos, cellsPos);
        MatchExample match = new MatchExample(0, "Attacker", board);

        CareTaker careTaker = new CareTaker(match);

        /*Here starts the testing */
        careTaker.doStuff();
        match.setTurnNumber(match.getTurnNumber() + 1);
        board.getPiecesPositions().remove(new Position(0,0)); //brutto dover rimuovere delle posizioni
        board.getPiecesPositions().put(new Position(2,0), piece1);
        board.setLastMove(new VectorImpl(new Position(0, 0), new Position(2, 0)));
        board.getCellsPositions().get(new Position(5, 5)).setState("occupied");
        piece2.setAlive(false);
        careTaker.doStuff();
        System.out.println(match.getTurnNumber());
        System.out.println(piece2.isAlive());
        System.out.println(board.getPiecesPositions().containsKey(new Position(0, 0)));
        System.out.println(board.getPiecesPositions().get(new Position(2, 0)));
        System.out.println(board.getCellsPositions().get(new Position(5, 5)).getState());
        careTaker.undo(0);
        System.out.println(match.getTurnNumber());
        System.out.println(piece2.isAlive());
        System.out.println(board.getPiecesPositions().containsKey(new Position(0, 0)));
        System.out.println(board.getPiecesPositions().get(new Position(2, 0)));
        System.out.println(board.getCellsPositions().get(new Position(5, 5)).getState());
    }
}
