package taflgames.model.memento;

public class CellExample {
    private String state;

    public CellExample(String state) {
        this.state = state;
    }

    public CellMemento save() {
        return this.new CellMemento();
    }

    public void restore(CellMemento cm) {
        this.state = cm.getState();
    }

    public class CellMemento {
        private String state;

        public CellMemento() {
            this.state = CellExample.this.state;
        }

        public String getState() {
            return state;
        }

        public void restore() {
            CellExample.this.restore(this);
        }
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    
}
