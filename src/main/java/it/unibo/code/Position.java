package it.unibo.code;

/**
 * A class modelling a 2D point in space.
 */
public class Position {
    
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void multiplyByInteger(int i) {
        this.setX(this.x * i);
        this.setY(this.y * i);
    }

}
