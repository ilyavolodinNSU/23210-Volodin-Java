package lab3.model;

import java.lang.reflect.Array;
import java.util.Arrays;

// класс описывает механики игрового процесса

public class Engine {
    private Model model;

    private Figure currentFigure;
    private int[][] finalFigurePosition;

    private EngineStatus status;

    public Engine() {
        this.model = new Model();
        this.currentFigure = genNewFigure();
        this.finalFigurePosition = calcFinalPosition(this.currentFigure);
        this.status = EngineStatus.RUN;
    }

    public EngineStatus getStatus() {
        return this.status;
    }

    public void moveLeft() {
        // добавить перерасчет финального положения
        try {
            shiftPosition(this.currentFigure.getPosition(), -1, 0);
            this.finalFigurePosition = calcFinalPosition(this.currentFigure);
        } catch (CollisionException e) {
            System.err.println("Коллизия");
        }

    }

    public void moveRight()  {
        try {
            shiftPosition(this.currentFigure.getPosition(), 1, 0);
            this.finalFigurePosition = calcFinalPosition(this.currentFigure);
        } catch (CollisionException e) {
            System.err.println("Коллизия");
        }
    }

    // собираем модель, которая будет передаватся в view
    public Model build() {
        Model releaseModel = new Model();
        releaseModel.addScore(this.model.getScore());

        for (int i = 0; i < releaseModel.getField().getHeight(); i++) {
            System.arraycopy(model.getField().getRow(i), 0, releaseModel.getField().getRow(i), 0, releaseModel.getField().getWidth());
        }

        assembleField(releaseModel, this.currentFigure, this.finalFigurePosition);

        return releaseModel;
    }

    public void update() {
        if (Arrays.deepEquals(this.currentFigure.getPosition(), this.finalFigurePosition)) {
            addFigure(this.model, this.currentFigure);

            if (filled(currentFigure)) {
                this.status = EngineStatus.OVER;
            } else {
                calcCompletedLines();
                this.currentFigure = genNewFigure();
                this.finalFigurePosition = calcFinalPosition(this.currentFigure);
            }
        } else {
            moveDown(this.currentFigure);
        }
    }

    // задаем значение для конткретных координат
    private void addToField(Model model, int[][] position, int value) {
        for (int[] coord : position) {
            if (coord[1] >= 0) model.getField().setCell(coord[0], coord[1], value);
        }
    }
    
    // делает фигуру статичной частью поля
    private void addFigure(Model model, Figure figure) {
        addToField(model, figure.getPosition(), figure.getId());
    }

    // метод для рендра добавляет поле тень на поле
    private void assembleField(Model model, Figure figure, int[][] finalPosition) {
        addToField(model, finalPosition, -figure.getId());
        addToField(model, figure.getPosition(), figure.getId());
    }

    // проверка на коллизии
    private boolean collision(int[][] coordinates) {
        for (int[] coord : coordinates) {
            if (coord[0] >= model.getField().getWidth() || coord[0] < 0) {
                System.out.println("Коллизия с боковой границей\n");
                return true;
            }

            if (coord[1] >= model.getField().getHeight()) {
                System.out.println("Коллизия с нижней границей\n");
                return true;
            }

            if (coord[1] >= 0 && model.getField().getCell(coord[0], coord[1]) != 0) {
                System.out.println("Коллизия с фигурой\n");
                return true;
            }
        }

        return false;
    }

    private void shiftPosition(int[][] position, int shiftX, int shiftY) throws CollisionException {
        for (int[] coord : position) {
            coord[0] += shiftX;
            coord[1] += shiftY;
        }

        if (collision(position)) {
            for (int[] coord : position) {
                coord[0] -= shiftX;
                coord[1] -= shiftY;
            }

            throw new CollisionException();
        }
    }


    private void moveDown(Figure figure) {
        for (int[] coord : figure.getPosition()) {
            coord[1] += 1;
        }
    }

    // расчитать корды после падения
    private int[][] calcFinalPosition(Figure figure) {
        int[][] position = new int[figure.getPosition().length][2];

        for (int i = 0; i < figure.getPosition().length; i++) {
            System.arraycopy(figure.getPosition()[i], 0, position[i], 0, figure.getPosition()[i].length);
        }

        while (true) {
            try {
                shiftPosition(position, 0, 1);
            } catch (CollisionException e) {
                break;
            }
        }

        return position;
    }

    private Figure genNewFigure() {
        // добавить расчет финального положения
        
        int[][] example = {
            {5, -3},
            {4, -2},
            {5, -2}, // x, y
            {4, -1},
            {5, -1}
        };

        Figure newFigure = new Figure(9, example);

        return newFigure;
    }

    private boolean filled(Figure figure) {
        for (int[] coord : figure.getPosition()) {
            if (coord[1] < 0) return true;
        }

        return false;
    }

    // очищение линий и начисление очков
    // смотрим только строки фигуры (для оптимизации)
    private void calcCompletedLines() {
        int completedLines = 0;

        int[][] figurePosition = this.currentFigure.getPosition();
        int startRow = figurePosition[0][1];
        int finalRow = figurePosition[figurePosition.length-1][1];

        // перебор строк
        for (int row = startRow; row <= finalRow; ++row) {
            boolean lineCompleted = true;

            // обрабатываем строку
            for (int el : this.model.getField().getRow(row)) {
                if (el == 0) {
                    lineCompleted = false;
                    break;
                }
            }

            if (lineCompleted) {
                completedLines++;

                // все строки ниже j свдигаем вверх на 1
                for (int j = (row-1); j > 0; --j) {
                    this.model.getField().shiftRow(j);
                }

                // остальное зануляем
                this.model.getField().resetRow(0);
            }
        }
        
        this.model.addScore(completedLines);
    }

}
