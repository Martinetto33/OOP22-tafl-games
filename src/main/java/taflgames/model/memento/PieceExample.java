package taflgames.model.memento;

public class PieceExample {
    private boolean isAlive;
    private String team;

    public PieceExample(boolean isAlive, String team) {
        this.isAlive = isAlive;
        this.team = team;
    }

    public PieceMemento save() {
        return this.new PieceMemento();
    }

    public void restore(PieceMemento pm) {
        this.isAlive = pm.isAlive();
        this.team = pm.getTeam();
    }

    public class PieceMemento {
        private boolean isAlive;
        private String team;
        
        public PieceMemento() {
            this.isAlive = PieceExample.this.isAlive;
            this.team = PieceExample.this.team;
        }

        public boolean isAlive() {
            return isAlive;
        }

        public String getTeam() {
            return team;
        }

        public void restore() {
            PieceExample.this.restore(this);
        }
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public String getTeam() {
        return team;
    }

    
}
