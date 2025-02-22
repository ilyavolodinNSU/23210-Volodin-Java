package lab3.model;

public class Model {
    private Field field;
    private int score = 0;

    public Model() {
        this.field = new Field();
    }

    public Field getField() {
        return this.field;
    }

    public int getScore() {
        return this.score;
    }

    public void addScore(int n) {
        this.score += n;
    }
}
