package lab3.model;

public class Figure {
    private String colour;
    private int[][] coordinates;
    private int id;

    public Figure(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
