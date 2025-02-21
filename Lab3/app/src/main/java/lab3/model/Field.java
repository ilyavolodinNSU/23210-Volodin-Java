package lab3.model;

// класс описывает поле и методы работы с ним

public class Field {
    private int width = 10, height = 20;
    private int[][] matrix; // номер будет описывать цвет

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new int[height][width];
    }

    public Field() {
        this.matrix = new int[this.height][this.width];
    }

    public void reset() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.matrix[i][j] = 0;
            }
        }
    }

    public void setCell(int x, int y, int value) {
        this.matrix[y][x] = value;
    }

    public int getCell(int x, int y) {
        return this.matrix[y][x];
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}