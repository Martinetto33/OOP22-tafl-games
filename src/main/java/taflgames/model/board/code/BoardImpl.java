package taflgames.model.board.code;

import java.util.*;
import java.util.stream.Collectors;

import taflgames.common.Player;
import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.model.board.api.Board;
import taflgames.model.cell.api.Cell;
import taflgames.model.cell.api.Resettable;
import taflgames.model.cell.api.TimedEntity;
import taflgames.model.pieces.api.Piece;

public class BoardImpl implements Board, TimedEntity{

    private Map<Position, Cell> cells;
    private Map<Player, Map<Position, Piece>> pieces;
    private final int size;
    private Position currentPos;
    private boolean isSwapper;
    private final Set<Resettable> resettableEntities = new HashSet<>();

    public BoardImpl(final Map<Player, Map<Position, Piece>> pieces, final Map<Position, Cell> cells, final int size) {
        this.pieces = pieces;
        this.cells = cells;
        this.size = size;
        //come si fa il costruttore di resettable?
        resettableEntities.add(null);
    }

    @Override
    public boolean isStartingPointValid(Position start, Player player) {
        isSwapper = false;

        if(!pieces.entrySet().stream()
            .filter(x -> x.getKey().equals(player))
            .filter(y -> y.getValue().keySet().contains(start))
            .collect(Collectors.toList()).isEmpty()) {
            currentPos = start;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isDestinationValid(Position start, Position dest, Player player) {
        //Set<Position> possibleDests = new HashSet<>();
        Piece piece = pieces.get(player).get(start);

        //Per le pedine che non sono uno swapper si controlla che la posizione di destinazione non sia già occupata
        if(!piece.canSwap()){
            if(!cells.get(dest).isFree() || !cells.get(dest).canAccept(piece)) {
                return false;
            }
        }

        // Si ottengono i vettori che rappresentano i possibili spostamenti della pedina
        Set<Vector> vectors = piece.whereToMove();
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
        
        for (Vector vector : vectors) {
            for(int numberOfBox = 1; numberOfBox < this.size; numberOfBox++) {
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
            isSwapper = true;
        // Si verifica se la posizione dest è una delle posizioni occupate da una pedina avversaria.
        // Se lo è, allora la mossa è valida, altrimenti no.
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
                return true;
            }
        }
        return false;
    }

    @Override
    public void updatePiecePos(Position oldPos, Position newPos) {
        if(!isSwapper) {
            pieces.entrySet().stream().forEach(x -> {
                if(x.getValue().containsKey(oldPos)) {
                    pieces.replace(x.getKey(), Collections.singletonMap(newPos, x.getValue().get(oldPos)));
                    signalOnMove(newPos, x.getValue().get(newPos));
                }
            });
            cells.get(oldPos).setFree(true);
            cells.get(newPos).setFree(false);
        } else {
            Player palyerInTurn = pieces.entrySet().stream().filter(x -> x.getValue().containsKey(oldPos)).map(x -> x.getKey()).findAny().get();
            pieces.entrySet().stream().forEach(x -> {
                if(x.getValue().containsKey(oldPos)) {
                    pieces.replace(x.getKey(), Collections.singletonMap(newPos, x.getValue().get(oldPos)));
                    signalOnMove(newPos, x.getValue().get(newPos));
                }
            });
            pieces.entrySet().stream().forEach(x -> {
                if(x.getValue().containsKey(newPos) && !x.getKey().equals(palyerInTurn)) {
                    pieces.replace(x.getKey(), Collections.singletonMap(newPos, x.getValue().get(oldPos)));
                }
            });

        }
    }

    @Override
    public Position getFurthestReachablePos(Position startPos, Vector direction) {
        Position furthestReachable = null;
        for(int numberOfBox = 0; numberOfBox < this.size; numberOfBox++) {
            Position reachablePos = direction.multiplyByScalar(numberOfBox).applyToPosition(startPos);
            if(!cells.get(reachablePos).isFree()) {
                break;
            } else {
                furthestReachable = reachablePos;
            }
        }  
        return furthestReachable;
    }

    @Override
    public void moveByVector(Vector direction) {
    }

    private void signalOnMove(Position source, Piece movedPiece) {
        // Ottengo le posizioni delle celle che potrebbero avere interesse nel conoscere l'ultima mossa fatta
        Set<Position> triggeredPos = movedPiece.whereToHit().stream()
                .map(x -> new Position(x.getX() +source.getX(), x.getY() + source.getY()))
                .collect(Collectors.toSet());
        // Controllo se nelle posizioni ottenute ci sono entità; in caso, vengono triggerate
        for (Position pos : triggeredPos) {
            Cell cell = cells.get(pos);
            cell.notify(source, movedPiece, List.of(movedPiece.sendSignalMove()));
        }
    }

    private boolean isPathFree(Position start, Position dest) {
        if(start.getX() == dest.getX()) {  
            if(start.getY() < dest.getY()) {
                for(int i=start.getY(); i<dest.getY(); i++) {
                    if(!cells.get(dest).isFree()) {
                        return false;
                    }
                }
            } else {
                for(int i=start.getY(); i<dest.getY(); i--) {
                    if(!cells.get(dest).isFree()) {
                        return false;
                    }
                }
            }
        } else {
            if(start.getX() < dest.getX()) {
                for(int i=start.getX() + 1; i<dest.getX(); i++) {
                    if(!cells.get(dest).isFree()) {
                        return false;
                    }
                }
            } else {
                for(int i=start.getY() - 1; i<dest.getY(); i--) {
                    if(!cells.get(dest).isFree()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void notifyTurnHasEnded(int turn) {
        this.resettableEntities.forEach(e -> e.reset());
    }
}


    