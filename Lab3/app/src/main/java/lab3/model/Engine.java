package lab3.model;

// класс описывает механики игрового процесса

public class Engine {
    private Model model;
    private Figure currentFigure;

    public Engine() {
        this.model = new Model();
        this.currentFigure = genNewFigure();
    }
    
    // делаем фигуру элементом поля (конечное состояние)
    private void addFigure(Model model, Figure figure) {
        for (int[] coord : figure.getPosition()) {
            model.getField().setCell(coord[0], coord[1], figure.getId());
        }
    }

    private boolean collision(int[][] coordinates) {
        for (int[] coord : coordinates) {
            if (model.getField().getCell(coord[0], coord[1]) != 0 
                || coord[0] >= model.getField().getWidth() || coord[0] < 0
                || coord[1] >= model.getField().getHeight() || coord[1] < 0) {
                return true;
            }
        }

        return false;
    }

    private int[][] shiftPosition(int[][] position, int shiftX, int shiftY) throws СollisionException {
        int[][] newPosition = position;

        for (int[] coord : newPosition) {
            coord[0] += shiftX;
            coord[1] += shiftY;
        }

        if (collision(newPosition)) throw new СollisionException();

        return newPosition;
    }

    private void moveDown(Figure figure) throws СollisionException {
        figure.setPosition(shiftPosition(figure.getPosition(), 0, -1));
    }

    private void moveLeft(Figure figure) throws СollisionException  {
        figure.setPosition(shiftPosition(figure.getPosition(), -1, 0));
    }

    private void moveRight(Figure figure) throws СollisionException  {
        figure.setPosition(shiftPosition(figure.getPosition(), 1, 0));
    }

    // расчитать корды после падения
    private int[][] finalPosition() {
        int[][] position = this.currentFigure.getPosition();

        while (true) {
            try {
                position = shiftPosition(position, 0, -1);
            } catch (СollisionException e) {
                break;
            }
        }

        return position;
    }

    // собираем модель, которая будет передаватся в view
    public Model build() {
        Model releaseModel = this.model;
        addFigure(releaseModel, this.currentFigure);
        return releaseModel;
    }

    private Figure genNewFigure() {
        int[][] example = {
            {4, 0}, {5, 0}, // x, y
            {4, 1}, {5, 1}
        };

        Figure newFigure = new Figure(9, example);

        return newFigure;
    }

    public void update() {
        try {
            moveDown(this.currentFigure);
        } catch (СollisionException e) {
            addFigure(this.model, this.currentFigure);
            genNewFigure();
            clearCompletedLines();
        }
    }

    // очищение линий и начисление очков
    // смотрим только строки фигуры (для оптимизации)
    private void clearCompletedLines() {
        int completedLines = 0;

        // проходим по всем строкам фигуры
        // анализируем соответствующие строки поля
        this.currentFigure.getPosition();
    }

    public void debugRenderField(Model model) {
        for (int i = 0; i < model.getField().getHeight(); i++) {
            for (int j = 0; j < model.getField().getWidth(); j++) {
                System.out.print(model.getField().getCell(j, i) + " ");
            }
            System.out.println();
        }
    }

    public void debugRenderFigure(Figure figure) {
        for (int[] row : figure.getPosition()) {
            System.out.printf("%4d %4d%n", row[0], row[1]); // Фиксировано 2 элемента в строке
        }
    }

}
