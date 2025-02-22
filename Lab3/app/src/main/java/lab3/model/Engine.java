package lab3.model;

// класс описывает механики игрового процесса

public class Engine {
    private Model model;
    private Figure currentFigure;
    private EngineStatus status;

    public Engine() {
        this.model = new Model();
        this.currentFigure = genNewFigure();
        this.status = EngineStatus.RUN;
    }

    public EngineStatus getStatus() {
        return this.status;
    }

    public void moveLeft() {
        // добавить перерасчет финального положения
        try {
            shiftPosition(this.currentFigure.getPosition(), -1, 0);
        } catch (CollisionException e) {
            // TODO: handle exception
        }

    }

    public void moveRight(Figure figure)  {
        // добавить перерасчет финального положения
        try {
            shiftPosition(this.currentFigure.getPosition(), 1, 0);
        } catch (CollisionException e) {
            // TODO: handle exception
        }
    }

    // собираем модель, которая будет передаватся в view
    public Model build() {
        Model releaseModel = new Model();
        releaseModel.addScore(this.model.getScore());

        for (int i = 0; i < releaseModel.getField().getHeight(); i++) {
            System.arraycopy(model.getField().getRow(i), 0, releaseModel.getField().getRow(i), 0, releaseModel.getField().getWidth());
        }

        addFigure(releaseModel, finalFigurePosition());
        addFigure(releaseModel, this.currentFigure);

        return releaseModel;
    }

    public void update() {
        try {
            moveDown(this.currentFigure);
        } catch (CollisionException e) {
            addFigure(this.model, this.currentFigure);

            if (filled(currentFigure)) {
                this.status = EngineStatus.OVER;
            } else {
                calcCompletedLines();
                this.currentFigure = genNewFigure();
            }
        }
    }
    
    // делаем фигуру элементом поля
    private void addFigure(Model model, Figure figure) {
        for (int[] coord : figure.getPosition()) {
            if (coord[1] >= 0) model.getField().setCell(coord[0], coord[1], figure.getId());
        }
    }

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

    private void moveDown(Figure figure) throws CollisionException {
        shiftPosition(figure.getPosition(), 0, 1);
    }

    // расчитать корды после падения
    private Figure finalFigurePosition() {
        int[][] position = new int[this.currentFigure.getPosition().length][2];

        for (int i = 0; i < this.currentFigure.getPosition().length; i++) {
            System.arraycopy(this.currentFigure.getPosition()[i], 0, position[i], 0, this.currentFigure.getPosition()[i].length);
        }

        while (true) {
            try {
                shiftPosition(position, 0, 1);
            } catch (CollisionException e) {
                break;
            }
        }

        Figure finalPosFigure = new Figure(-(this.currentFigure.getId()), position);

        return finalPosFigure;
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




    public void debugRenderField(Model model) {
        for (int i = 0; i < model.getField().getHeight(); i++) {
            for (int j = 0; j < model.getField().getWidth(); j++) {
                System.out.print(model.getField().getCell(j, i) + " ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    public void debugRenderFigure(Figure figure) {
        for (int[] row : figure.getPosition()) {
            System.out.printf("%4d %4d%n", row[0], row[1]); // Фиксировано 2 элемента в строке
        }
    }

}
