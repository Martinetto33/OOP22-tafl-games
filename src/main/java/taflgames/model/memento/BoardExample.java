package taflgames.model.memento;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import taflgames.common.api.Vector;
import taflgames.common.code.Position;

public class BoardExample { 
    private Map<Position, PieceExample> piecesPositions;
    private Map<Position, CellExample> cellsPositions;
    private Vector lastMove;

    public BoardExample(Map<Position, PieceExample> piecesPositions, Map<Position, CellExample> cellsPositions) {
        this.piecesPositions = piecesPositions;
        this.cellsPositions = cellsPositions;
        this.lastMove = null;
    }

    public BoardMemento save() {
        return this.new BoardMemento(this.piecesPositions.values().stream().map(x -> x.save()).toList(),
        this.cellsPositions.values().stream().map(y -> y.save()).toList(), this.lastMove);
    }

    public void restore(BoardMemento bm) {
        this.cellsPositions = new HashMap<>();
        this.cellsPositions.putAll(bm.getCellsPositions());//con questa new non dovrebbe più darmi problemi di puntatori che puntano alla stessa collezione
        /*AGGIORNAMENTO: addAll() crea una shallow copy della collection, non una deep copy! aaaaaa questo significa che non copia gli oggetti uno ad uno in una
         * nuova collezione ma clona soltanto il puntatore alla stessa collezione.
         */
        this.piecesPositions = new HashMap<>();
        this.piecesPositions.putAll(bm.getPiecesPositions());
        this.revertMovement(this.lastMove);
        bm.getCellsMemento().forEach(x -> x.restore());
        bm.getPiecesMemento().forEach(x -> x.restore());
        this.lastMove = bm.getLastMove();
    }

    private void revertMovement(Vector v) {
        if(v != null) {
            PieceExample movedPiece = this.piecesPositions.get(v.getEndPos());
            this.piecesPositions.remove(v.getEndPos());
            this.piecesPositions.put(v.getStartPos(), movedPiece);
        }
    }

    public void setLastMove(Vector v) {
        this.lastMove = v;
    }

    public class BoardMemento {
        private Map<Position, PieceExample> piecesPositions;
        private Map<Position, CellExample> cellsPositions;
        /*Metto la serie di memento dentro alla Inner Class */
        private List<PieceExample.PieceMemento> piecesMemento;
        private List<CellExample.CellMemento> cellsMemento;
        private Vector lastMove;
        
        public BoardMemento(List<PieceExample.PieceMemento> pmList, List<CellExample.CellMemento> cmList, Vector lastMove) {
            this.piecesPositions = Collections.unmodifiableMap(BoardExample.this.piecesPositions);
            this.cellsPositions = Collections.unmodifiableMap(BoardExample.this.cellsPositions);
            this.piecesMemento = pmList;
            this.cellsMemento = cmList;
            this.lastMove = lastMove;
        }

        public Map<Position, PieceExample> getPiecesPositions() {
            return piecesPositions;
        }

        public Map<Position, CellExample> getCellsPositions() {
            return cellsPositions;
        }

        public List<PieceExample.PieceMemento> getPiecesMemento() {
            return piecesMemento;
        }

        public List<CellExample.CellMemento> getCellsMemento() {
            return cellsMemento;
        }

        public Vector getLastMove() {
            return lastMove;
        }
        
    }

    public Map<Position, PieceExample> getPiecesPositions() {
        return piecesPositions;
    }

    public Map<Position, CellExample> getCellsPositions() {
        return cellsPositions;
    }

}
