package lab3.model;

public class Shape implements Cloneable {
    private int[][] position; // описывает форму фигуры (y строго должны быть по возрастанию)
    private int id;

    public Shape(int id, int[][] coordinates) {
        this.id = id;
        this.position = coordinates;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // public Color getColor() {
    //     return this.color;
    // }

    public int[][] getPosition() {
        return this.position;
    }

    public void setPosition(int[][] position) {
        // проверка на то что Y по возрастанию и фигура цельная (иначе метод поиска заполненных строк сломается)
        this.position = position;
    }

    @Override
    public Shape clone() {
        return new Shape(this.id, deepCopy(this.position));
    }

    private static int[][] deepCopy(int[][] original) {
        int[][] deepCopy = new int[original.length][];

        for (int i = 0; i < original.length; i++) {
            deepCopy[i] = new int[original[i].length];
            System.arraycopy(original[i], 0, deepCopy[i], 0, original[i].length);
        }
        
        return deepCopy;
    }

}
