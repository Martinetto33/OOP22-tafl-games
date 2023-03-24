package taflgames.model.board.code;

import java.util.*;
import java.util.stream.Collectors;

import taflgames.common.Player;
import taflgames.common.api.Vector;
import taflgames.common.code.Position;
import taflgames.model.board.api.Board;
import taflgames.model.board.api.Eaten;
import taflgames.model.cell.api.Cell;
import taflgames.model.cell.api.Resettable;
import taflgames.model.cell.api.TimedEntity;
import taflgames.model.pieces.api.Piece;

public class BoardImpl implements Board, TimedEntity{

    private Map<Position, Cell> cells;
    private Map<Player, Map<Position, Piece>> pieces;
    private final int size;
    private Position currentPos;
    private Set<Resettable> resettableEntities = null;
    private final Eaten eatingManager;
    private Set<TimedEntity> timedEntities = null;

    public BoardImpl(final Map<Player, Map<Position, Piece>> pieces, final Map<Position, Cell> cells, final int size) {
        this.pieces = pieces;
        this.cells = cells;
        this.size = size;
        this.eatingManager = new EatenImpl();
    }

    @Override
    public boolean isStartingPointValid(Position start, Player player) {
        if(!pieces.entrySet().stream()
            .filter(x -> x.getKey().equals(player))
            .filter(y -> y.getValue().keySet().contains(start))
            .collect(Collectors.toList()).isEmpty()) {
            this.currentPos = start;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isDestinationValid(Position start, Position dest, Player player) {
        //Set<Position> possibleDests = new HashSet<>();
        Piece piece = pieces.get(player).get(start);

        //Per le pedine che non sono uno swapper si controlla che la posizione di destinazione non sia già occupata e che possa accogliere quel determinato piece
        //Mentre se la pedina è uno swapper controllo che la cella di destinazione non sia un trono o un uscita poichè non può andarci
        if(!piece.canSwap()){
            if(!cells.get(dest).canAccept(piece)) {
                return false;
            }
        } else if(cells.get(dest).getType().equals("Throne") 
                || cells.get(dest).getType().equals("Exit")
                || (cells.get(start).getType().equals("Slider") && !cells.get(dest).isFree())
                || (!cells.get(dest).isFree() && getPiece(dest).getPlayer().equals(player))) {
                return false;
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
        //controllo se la cella di arrivo è libera per lo swapper, poichè se la cella non fosse libera dovrei gestire lo swapper come viene fatto dopo questo if
        if(cells.get(dest).isFree()) {
            for (Vector vector : vectors) {
                for(int numberOfBox = 1; numberOfBox < this.size; numberOfBox++) {
                    if(vector.multiplyByScalar(numberOfBox).applyToPosition(start).equals(dest)) {
                        if(isPathFree(start, dest)) {
                            return true;
                        }
                    }
                }  
            }
        }

        /* SUPPONIAMO di non averne trovato nessuno. Allora si procede a verificare se è comunque uno spostamento valido secondo
        * altre proprietà della pedina.
        * QUINDI, per trattare il caso dello Swapper (che nel nostro caso è l'unico con una mossa speciale), possiamo dotare
        * qualsiasi pedina di un metodo canSwap(), che chiaramente ritorna true nel caso sia uno Swapper e false altrimenti.
        */
        if (piece.canSwap() 
            && (dest.getX() == start.getX() || dest.getY() == start.getY())) {
            // Si verifica se la posizione dest è una delle posizioni occupate da una pedina avversaria.
            // Se lo è, allora la mossa è valida, altrimenti no.

            // trovo la tipologia di pedina nella casella di destinazione dopodichè controllo che non sia un re poichè lo swapper non può scambiare posizione con un re
            Piece destPiece = getPiece(dest);
            if(destPiece != null && destPiece.getMyType().getTypeOfPiece().equals("KING")) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void updatePiecePos(Position oldPos, Position newPos) {
        Piece p = pieces.entrySet().stream()
            .filter(x -> x.getValue().containsKey(oldPos))
            .map(x -> x.getValue().get(oldPos))
            .findAny()
            .get();
        if(!p.canSwap()) {
            pieces.entrySet().stream().forEach(x -> {
                if(x.getValue().containsKey(oldPos)) {
                    pieces.replace(x.getKey(), Collections.singletonMap(newPos, x.getValue().get(oldPos)));
                    signalOnMove(newPos, p);
                }
            });
            cells.get(oldPos).setFree(true);
            cells.get(newPos).setFree(false);
        } else {
            Player palyerInTurn = p.getPlayer();
            pieces.entrySet().stream().forEach(x -> {
                if(x.getValue().containsKey(oldPos)) {
                    pieces.replace(x.getKey(), Collections.singletonMap(newPos, x.getValue().get(oldPos)));
                }
            });
            pieces.entrySet().stream().forEach(x -> {
                if(x.getValue().containsKey(newPos) && !x.getKey().equals(palyerInTurn)) {
                    pieces.replace(x.getKey(), Collections.singletonMap(oldPos, x.getValue().get(newPos)));
                }
            });
            signalOnMove(newPos, p);
        }
        this.currentPos = newPos;
    }

    @Override
    public Position getFurthestReachablePos(Position startPos, Vector direction) {
        Position furthestReachable = startPos;
        for(int numberOfBox = 1; numberOfBox < this.size; numberOfBox++) {
            Position reachablePos = direction.multiplyByScalar(numberOfBox).applyToPosition(startPos);
            if(reachablePos.getX() == this.size || reachablePos.getY() == this.size
                || reachablePos.getX() < 0 || reachablePos.getY() < 0 
                || !cells.get(reachablePos).canAccept(getPiece(startPos))) {
                    if(getPiece(startPos).canSwap()){
                        if( !cells.get(reachablePos).isFree()
                            && (!cells.get(reachablePos).getType().equals("Throne") 
                                    || !cells.get(reachablePos).getType().equals("Exit"))
                            && cells.get(startPos).getType().equals("Sider")
                            && getPiece(reachablePos).getPlayer().equals(getPiece(startPos).getPlayer())) {
                            furthestReachable = reachablePos;
                        }
                    }
                break;
            } else {
                furthestReachable = reachablePos;
            }
        } 
        return furthestReachable;
    }

    private void signalOnMove(Position source, Piece movedPiece) {
        // Ottengo le posizioni delle celle che potrebbero avere interesse nel conoscere l'ultima mossa fatta
        Set<Position> triggeredPos = eatingManager.trimHitbox(movedPiece, pieces, cells, size).stream()
                .collect(Collectors.toSet());
                //.map(x -> new Position(x.getX() + source.getX(), x.getY() + source.getY()))
        // Controllo se nelle posizioni ottenute ci sono entità; in caso, vengono triggerate
        if(!triggeredPos.isEmpty()) {
            for (Position pos : triggeredPos) {
                Cell cell = cells.get(pos);
                cell.notify(source, movedPiece, List.of(movedPiece.sendSignalMove()), pieces, cells);
            }
        }
    }

    private boolean isPathFree(Position start, Position dest) {
        if(start.getX() == dest.getX()) { 
            if(start.getY() < dest.getY()) {
                for(int i=start.getY()+1; i<dest.getY(); i++) {
                    if(!cells.get(new Position(start.getX(), i)).canAccept(getPiece(start))) {
                        return false;
                    }
                }
            } else {
                for(int i=start.getY()-1; i<dest.getY(); i--) {
                    if(!cells.get(new Position(start.getX(), i)).canAccept(getPiece(start))) {
                        return false;
                    }
                }
            }
        } else {
            if(start.getX() < dest.getX()) {
                for(int i=start.getX() + 1; i<dest.getX(); i++) {
                    if(!cells.get(new Position(i, start.getY())).canAccept(getPiece(start))) {
                        return false;
                    }
                }
            } else {
                for(int i=start.getX() - 1; i<dest.getX(); i--) {
                    if(!cells.get(new Position(i, start.getY())).canAccept(getPiece(start))) {
                        return false;
                    }
                }
            }
        } 
        return true;
    }

    @Override
    public void notifyTurnHasEnded(int turn) {
        if (this.resettableEntities != null) {
            this.resettableEntities.forEach(e -> e.reset());
        } 
        if (this.timedEntities != null) {
            this.timedEntities.forEach(elem -> elem.notifyTurnHasEnded(turn));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void eat(){
        Piece currPiece = getPiece(currentPos);
        Set<Position> updatedHitbox = eatingManager.trimHitbox(currPiece, pieces, cells, size);
        List<Piece> enemies = eatingManager.getThreatenedPos(updatedHitbox, pieces, currPiece);
        Map<Piece, Set<Piece>> enemiesAndAllies = eatingManager.checkAllies(enemies, pieces, currPiece);
        eatingManager.notifyAllThreatened(enemiesAndAllies, currPiece, cells, pieces);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addResettableEntities(final Set<Resettable> resettableEntities) {
        this.resettableEntities = resettableEntities;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTimedEntities(final Set<TimedEntity> timedEntities) {
        this.timedEntities = timedEntities;
    }

    public boolean isDraw(final Player playerInTurn) {
        /*finding king position */
        Piece king = pieces.get(Player.DEFENDER).entrySet().stream()
                        .filter(elem -> elem.getValue().getMyType().getTypeOfPiece().equals("KING"))
                        .map(x -> x.getValue())
                        .findAny()
                        .get();
        /*If the king is on the boarder, the position adjacent to it are controlled to see if the king is trapped */
        

        if (!pieces.get(playerInTurn).entrySet().stream()
            .filter(pos -> pos.getValue().getMyType().getTypeOfPiece().equals("SWAPPER"))
            .collect(Collectors.toList())
            .isEmpty()) {
                            return false;
        } else if (king.getCurrentPosition().getX() == 0 || king.getCurrentPosition().getY() == 0
            || king.getCurrentPosition().getX() == this.size-1 || king.getCurrentPosition().getX() == this.size-1) {

                if (getAdjacentPositions(king.getCurrentPosition()).stream()
                    .filter(pos -> !cells.get(pos).isFree())
                    .filter(pos -> pieces.get(Player.ATTACKER).keySet().contains(pos))
                    .collect(Collectors.toSet())
                    .size() == 3) {
                                return true;
                }
        } else if (!pieces.get(playerInTurn).entrySet().stream()
                    .filter(pos -> !getAdjacentPositions(pos.getKey()).stream()
                            .filter(adjPos -> cells.get(adjPos).canAccept(pos.getValue()))
                            .collect(Collectors.toSet()).isEmpty())
                    .collect(Collectors.toSet()).isEmpty()) {
                        return false;
        } 
            
        return true;
    }

    private Piece getPiece(Position pos) {
        Piece p = pieces.entrySet().stream()
            .filter(x -> x.getValue().containsKey(pos))
            .map(x -> x.getValue().get(pos))
            .findAny()
            .get();
        return p;
    }

    private Set<Position> getAdjacentPositions(final Position currPos) {
        Set<Position> setOfPosition = new HashSet<>();
        setOfPosition.add(new Position(currPos.getX()+1, currPos.getY()));
        setOfPosition.add(new Position(currPos.getX()-1, currPos.getY()));
        setOfPosition.add(new Position(currPos.getX(), currPos.getY()+1));
        setOfPosition.add(new Position(currPos.getX(), currPos.getY()-1));
        return setOfPosition.stream()
                                .filter(pos -> pos.getX() >= 0 && pos.getY() <= 0 && pos.getX() < this.size && pos.getY() <this.size)
                                .collect(Collectors.toSet());
    }
     
}
    