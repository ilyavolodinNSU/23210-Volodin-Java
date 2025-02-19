package lab3.model;

public class Field {
    private int width = 10, height = 20;
    private int[][] matrix; // номер будет описывать цвет

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new int[width][height];
    }

    public Field() {
        this.matrix = new int[this.width][this.height];
    }

    private boolean checkFreeArea(int[][] coordinates) {
        for (int[] coordinate : coordinates) {
            if (getCell(coordinate[0], coordinate[1]) != 0) {
                return false;
            }
        }

        return true;
    }

    public void reset() {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                this.matrix[i][j] = 0;
            }
        }
    }

    public void setCell(int x, int y, int value) {
        this.matrix[x][y] = value;
    }

    private int getCell(int x, int y) {
        return this.matrix[x][y];
    }

    public void spawnFigure(int value, int[][] coordinates) throws Exception {
        if (checkFreeArea(coordinates) == false) {
            throw new Exception();
        }

        for (int[] coordinate : coordinates) {
            setCell(coordinate[0], coordinate[1], value);
        }
    }

    public void moveFigure(int value) throws Exception {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (this.matrix[i][j] == value) {

                } 
            }
        }
    }
}