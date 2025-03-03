package lab3.model;

public class Figure implements Cloneable {
    private int[][] position; // описывает форму фигуры (y строго должны быть по возрастанию)
    private int id;

    public Figure(int id, int[][] coordinates) {
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
    protected Figure clone() {
        return new Figure(this.id, deepCopy(this.position));
    }

    private static int[][] deepCopy(int[][] array) {
        int[][] copy = new int[array.length][];

        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i].clone();
        }
        
        return copy;
    }

}
