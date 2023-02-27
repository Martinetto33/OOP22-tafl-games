package taflgames.model.memento;

import taflgames.model.memento.BoardExample.BoardMemento;

public class MatchExample { /*Posso rendere queste classi astratte e non ci saranno problemi, perché possono contenere Inner Classes */
    private int turnNumber;
    private String playerInTurn;
    private BoardExample board;

    public MatchExample(int turnNumber, String playerInTurn, BoardExample board) {
        this.turnNumber = turnNumber;
        this.playerInTurn = playerInTurn;
        this.board = board;
    }

    public MatchMemento save() {
        return this.new MatchMemento(this.board.save());
    }

    /*Can't be done if there is nothing saved, but the checks are missing for now.
     * TODO: add check for empty memento
     */
    public void restore(MatchMemento m) {
        this.turnNumber = m.getTurnNumber();
        this.playerInTurn = m.getPlayerInTurn();
        this.board.restore(m.getBm());
    }

    public class MatchMemento {
        private int turnNumber;
        private String playerInTurn;
        private BoardMemento bm;

        public MatchMemento(BoardMemento bm) {
            this.turnNumber = MatchExample.this.turnNumber; //Inner Classes can see private members
            this.playerInTurn = MatchExample.this.playerInTurn;
            this.bm = bm;
        }

        public int getTurnNumber() {
            return turnNumber;
        }

        public String getPlayerInTurn() {
            return playerInTurn;
        }

        public BoardMemento getBm() {
            return bm;
        }
        
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public void setPlayerInTurn(String playerInTurn) {
        this.playerInTurn = playerInTurn;
    }

    public int getTurnNumber() {
        return turnNumber;
    }
    
}
