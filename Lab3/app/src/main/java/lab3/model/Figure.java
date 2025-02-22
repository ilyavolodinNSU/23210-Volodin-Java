package lab3.model;

public class Figure {
    private int[][] position; // описывает форму фигуры (y строго должны быть по возрастанию)
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
        // проверка на то что Y по возрастанию и фигура цельная (иначе метод поиска заполненных строк сломается)
        this.position = position;
    }

}
