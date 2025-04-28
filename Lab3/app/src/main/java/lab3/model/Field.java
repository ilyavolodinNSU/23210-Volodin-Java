package lab3.model;

import java.util.Arrays;

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

    public int[][] getMatrix() {
        return this.matrix;
    }

    public void reset() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.matrix[i][j] = 0;
            }
        }
    }

    public void resetRow(int y) {
        Arrays.fill(matrix[y], 0);
    }

    public void setCell(int x, int y, int value) {
        this.matrix[y][x] = value;
    }

    public int getCell(int x, int y) {
        return this.matrix[y][x];
    }

    public int[] getRow(int y) {
        return this.matrix[y];
    }

    public void shiftRow(int y) {
        System.arraycopy(
            matrix[y],
            0,
            matrix[y + 1],
            0,
            this.width);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}