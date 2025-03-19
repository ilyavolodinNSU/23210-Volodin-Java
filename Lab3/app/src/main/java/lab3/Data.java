package lab3;

import lab3.model.Field;

public class Data {
    private Field field;
    private int totalLines = 0;
    private int score = 0;
    private int level = 0;
    private float dropSpeed = 0;

    public Data() {
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

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int n) {
        this.level = n;
    }

    public int getTotalLines() {
        return this.totalLines;
    }

    public void addTotalLines(int n) {
        this.totalLines += n;
    }
}
