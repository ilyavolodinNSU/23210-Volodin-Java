package lab3.model;

import java.util.Arrays;

public class GameLogic {
    public static void moveLeft(Field field, Figure figure) throws CollisionException {
        Figure copy = figure.clone();
        
        shiftPosition(copy.getPosition(), -1, 0);

        if (collision(field, figure)) throw new CollisionException();

        figure = copy;
    }

    public static void moveRight(Field field, Figure figure) throws CollisionException {
        Figure copy = figure.clone();
        
        shiftPosition(copy.getPosition(), 1, 0);

        if (collision(field, figure)) throw new CollisionException();

        figure = copy;
    }

    private static boolean filled(Figure figure) {
        for (int[] coord : figure.getPosition()) {
            if (coord[1] < 0) return true;
        }

        return false;
    }

    // расчитать корды после падения
    private static int[][] calcFinalPosition(Field field, Figure figure) {
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

    private static void moveDown(Figure figure) {
        for (int[] coord : figure.getPosition()) {
            coord[1] += 1;
        }
    }

    private static void shiftPosition(int[][] position, int shiftX, int shiftY) {
        for (int[] coord : position) {
            coord[0] += shiftX;
            coord[1] += shiftY;
        }
    }

    public static void dropFigure() {
        this.currentFigure.setPosition(this.finalFigurePosition);
    }

    // поворот фигуры
    public static void rotate(Figure figure, boolean clockwise) {
        int[][] position = figure.getPosition();

        for (int i = 0; i < position.length; i++) {
            int x = position[i][0];
            int y = position[i][1];
            position[i][0] = -y;
            position[i][1] = x;
        }

        // нормализация в пределы поля
        int minX = Arrays.stream(position).mapToInt(b -> b[0]).min().orElse(0);
        int minY = Arrays.stream(position).mapToInt(b -> b[1]).min().orElse(0);
        for (int i = 0; i < position.length; i++) {
            position[i][0] -= minX;
            position[i][1] -= minY;
        }

        this.finalFigurePosition = calcFinalPosition(this.currentFigure);
    }

    // проверка на коллизии фигуры относительно поля
    public static boolean collision(Field field, Figure figure) {
        for (int[] coord : figure.getPosition()) {
            if (coord[0] >= field.getWidth() || coord[0] < 0) {
                System.out.println("Коллизия с боковой границей\n");
                return true;
            }

            if (coord[1] >= field.getHeight()) {
                System.out.println("Коллизия с нижней границей\n");
                return true;
            }

            if (coord[1] >= 0 && field.getCell(coord[0], coord[1]) != 0) {
                System.out.println("Коллизия с фигурой\n");
                return true;
            }
        }

        return false;
    }

    // очищение линий и начисление очков
    // смотрим только строки фигуры (для оптимизации)
    private void calcCompletedLines() {
        int completedLines = 0;

        int[][] figurePosition = this.currentFigure.getPosition();

        // перебор строк
        for (int[] row : figurePosition) {
            int y = row[1];

            boolean lineCompleted = true;
            // обрабатываем строку
            for (int el : this.model.getField().getRow(y)) {
                if (el == 0) {
                    lineCompleted = false;
                    break;
                }
            }

            if (lineCompleted) {
                completedLines++;

                // все строки ниже j свдигаем вверх на 1
                for (int j = (y-1); j > 0; --j) {
                    this.model.getField().shiftRow(j);
                }

                // остальное зануляем
                this.model.getField().resetRow(0);
            }
        }
        
        this.model.addTotalLines(completedLines);
        this.model.setLevel(calcLevel(this.model.getTotalLines()));
        this.model.addScore(calcScore(this.model.getTotalLines(), this.model.getLevel()));
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
    public static void assembleField(Model model, Figure figure, int[][] finalPosition) {
        addToField(model, finalPosition, -figure.getId());
        addToField(model, figure.getPosition(), figure.getId());
    }


    private int calcLevel(int totalLines) {
        return totalLines/10;
    }

    private int calcScore(int totalLines, int level) {
        int base = 0;

        switch (totalLines) {
            case 1 -> base = 40;
            case 2 -> base = 100;
            case 3 -> base = 300;
            case 4 -> base = 1200;
        }

        return base*(level+1);
    }

    private static int[][] copyPosition(int[][] original) {
        int[][] copy = new int[original.length][];

        for (int i = 0; i < original.length; i++) {
            copy[i] = new int[original[i].length];
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }

        return copy;
    } 
}
