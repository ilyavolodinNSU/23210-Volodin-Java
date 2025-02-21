package lab3.model;

public class Figure {
    private int[][] position; // shape
    private int id;

    public Figure(int id, int[][] coordinates) {
        this.id = id;
        this.position = coordinates;
    }

    public int getId() {
        return this.id;
    }

    public int[][] getPosition() {
        return this.position;
    }

    public void setPosition(int[][] position) {
        this.position = position;
    }

}
