package taflgames.model.board.code;

import java.util.*;
import java.util.stream.Collectors;


import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.model.board.api.Board;
import taflgames.model.cell.api.Cell;


public class BoardImpl implements Board{

    private Map<Position, Cell> cells;
    private Map<Player, Map<Position, Piece>> pieces;
    private final int size;
    

    public BoardImpl(final Map<Player, Map<Position, Piece>> pieces, final Map<Position, Cell> cells,final int size) {
        this.pieces = pieces;
        this.cells = cells;
        this.size = size;
    }

    @Override
    public boolean isStartingPointValid(Position start, Player player) {

        if(!pieces.entrySet().stream().filter(x -> x.getKey().equals(player)).filter(y -> y.getValue().keySet().contains(start)).collect(Collectors.toList()).isEmpty()) {
            return true;
        } else {
            return false;
        }
        
    }

    @Override
    public boolean isDestinationValid(Position start, Position dest, Player player) {
        Set<Position> possibleDests = new HashSet<>();
        Piece piece = pieces.get(player).get(start);
        

        // Si ottengono i vettori che rappresentano i possibili spostamenti della pedina
        Set<Vector> vectors = piece.getMovementVectors();
        /* Nel caso delle pedine normali, i vettori restituiti saranno (-1,0), (0,1), (1,0), (0,-1).
        *
        * NOTA1: uno spostamento equivale a sommare la posizione di partenza a un vettore v che indica lo spostamento: start + v = dest
        *
        * NOTA2: per fare un esempio, se una pedina si sposta di N caselle a destra, ciò equivale a dire che dest = start + N * (0, 1);
        * ciò equivale anche a dire che lo spostamento dato dal vettore (0, 1) è applicato N volte.
        *
        * QUINDI, per verificare se una mossa è valida, si verifica se, per uno dei vettori (v) dati da piece.getVectors(),
        * esiste uno scalare N t.c. start + N * v = dest.
        * Se se ne trova uno, si deve verificare che tutte le celle nel percorso che porta la pedina da start a dest siano libere.
        * Se lo sono, allora la mossa è valida, altrimenti non lo è e si deve continuare la ricerca.
        */
        if(!pieces.entrySet().stream().filter(x -> x.getValue().keySet().contains(dest)).collect(Collectors.toList()).isEmpty()) {
            return false;
        }

        for (Vector vector : vectors) {
            for(int numberOfBox = 0; numberOfBox < this.size; numberOfBox++) {
                if(vector.multiplyByScalar(numberOfBox).applyToPosition(start).equals(dest)) {
                    if(isPathFree(start, dest)) {
                        return true;
                    }
                }
            }  
        }
        


        /* SUPPONIAMO di non averne trovato nessuno. Allora si procede a verificare se è comunque uno spostamento valido secondo
        * altre proprietà della pedina.
        * QUINDI, per trattare il caso dello Swapper (che nel nostro caso è l'unico con una mossa speciale), possiamo dotare
        * qualsiasi pedina di un metodo canSwap(), che chiaramente ritorna true nel caso sia uno Swapper e false altrimenti.
        */
        if (piece.canSwap()) {
        // Si verifica se la posizione dest è una delle posizioni occupate da una pedina avversaria.
        // Se lo è, allora la mossa è valida, altrimenti no.
            if(isPathFree(start, dest)) {
                Map<Position, Piece> element = new HashMap<>();
                Optional<Piece> destPiece = null;

                // trovo la tipologia di pedina dopodichè controllo che non sia un re poichè lo swapper non può scambiare posizione con un re
                for (Map.Entry<Player, Map<Position, Piece>> item : pieces.entrySet()) {
                    Map<Position, Piece> valueMap = item.getValue();
                    destPiece = valueMap.entrySet().stream().filter(x -> x.getKey().equals(dest)).map(x -> x.getValue()).findAny();
                    //TODO RE
                    /* if(destPiece.isPresent() && destPiece.get().equals(vectors)) {
                        return false;
                    } */
                    return false;
                }
                
                element.put(dest, destPiece.get());
                if(!pieces.entrySet().stream().filter(x -> x.getValue().equals(element)).map(x -> x.getKey().equals(player)).findAny().get()) {
                    updatePiecePos(start, dest);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void updatePiecePos(Position oldPos, Position newPos) {
        pieces.entrySet().stream().forEach(x -> {
            if(x.getValue().containsKey(oldPos)) {
                pieces.replace(x.getKey(), Collections.singletonMap(newPos, x.getValue().get(oldPos)));
            }
        });
    }

    @Override
    public Position getFurthestReachablePos(Vector direction) {
        
        return null;
    }

    @Override
    public void moveByVector(Vector direction) {
    }

    private void signalOnMove(Position source, Piece movedPiece) {

    }

    private boolean isPathFree(Position start, Position dest) {
        if(start.getX() == dest.getX()) {  
            if(start.getY() < dest.getY()) {
                for(int i=start.getY(); i<dest.getY(); i++) {
                    for (Map<Position, Piece> elem : pieces.values()) {
                        if(elem.keySet().contains(new Position(start.getX(), i))) {
                            return false;
                        }
                    }
                }
            } else {
                for(int i=start.getY(); i<dest.getY(); i--) {
                    for (Map<Position, Piece> elem : pieces.values()) {
                        if(elem.keySet().contains(new Position(start.getX(), i))) {
                            return false;
                        }
                    }
                }
            }
        } else {
            if(start.getX() < dest.getX()) {
                for(int i=start.getX() + 1; i<dest.getX(); i++) {
                    for (Map<Position, Piece> elem : pieces.values()) {
                        if(elem.keySet().contains(new Position(i, start.getY()))) {
                            return false;
                        }
                    }
                }
            } else {
                for(int i=start.getY() - 1; i<dest.getY(); i--) {
                    for (Map<Position, Piece> elem : pieces.values()) {
                        if(elem.keySet().contains(new Position(i, start.getY()))) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}


    