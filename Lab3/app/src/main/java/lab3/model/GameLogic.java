package lab3.model;

import java.util.Arrays;

// работает с field, shape

public class GameLogic {
    public static void moveLeft(Field field, Shape shape) {
        MatOper.shiftMatrix(shape.getPosition(), -1, 0);
        if (MatOper.collisionMatrix(field.getMatrix(), shape.getPosition())) MatOper.shiftMatrix(shape.getPosition(), 1, 0);
    }

    public static void moveRight(Field field, Shape shape) {
        MatOper.shiftMatrix(shape.getPosition(), 1, 0);
        if (MatOper.collisionMatrix(field.getMatrix(), shape.getPosition())) MatOper.shiftMatrix(shape.getPosition(), -1, 0);
    }

    public static void moveDown(Shape shape) {
        MatOper.shiftMatrix(shape.getPosition(), 0, 1);
    }

    public static boolean filled(Shape shape) {
        for (int[] coord : shape.getPosition()) {
            if (coord[1] < 0) return true;
        }
        return false;
    }

    // расчитать корды после падения
    public static int[][] calcFinalPosition(Field field, Shape shape) {
        int[][] position = MatOper.cloneMatrix(shape.getPosition());

        System.err.println("start " + Arrays.deepToString(position) + "\n");

        while (MatOper.collisionMatrix(field.getMatrix(), position) == false) {
            MatOper.shiftMatrix(position, 0, 1);
            //System.err.println("shift " + Arrays.deepToString(position) + "\n");
        }

        System.err.println("final " + Arrays.deepToString(position) + "\n");

        MatOper.shiftMatrix(position, 0, -1);

        return position;
    }

    // поворот фигуры
    public static void rotate(Field field, Shape shape, boolean clockwise) {
        int[][] position = MatOper.cloneMatrix(shape.getPosition());

        MatOper.rotateMatrix(position, clockwise);

        if (MatOper.collisionMatrix(field.getMatrix(), position)) {
            MatOper.rotateMatrix(position, false);
        } else {
            shape.setPosition(position);
        }
    }

    // очищение линий и начисление очков
    // смотрим только строки фигуры (для оптимизации)
    public static int calcCompletedLines(Field field, Shape shape) {
        int completedLines = 0;
        int[][] figurePosition = shape.getPosition();

        // перебор строк
        for (int[] row : figurePosition) {
            int y = row[1];

            boolean lineCompleted = true;
            // обрабатываем строку
            for (int el : field.getRow(y)) {
                if (el == 0) {
                    lineCompleted = false;
                    break;
                }
            }

            if (lineCompleted) {
                completedLines++;

                // все строки ниже j свдигаем вверх на 1
                for (int j = (y-1); j > 0; --j) {
                    field.shiftRow(j);
                }

                // остальное зануляем
                field.resetRow(0);
            }
        }

        return completedLines;
    }
    
    // делает фигуру статичной частью поля
    public static void addFigure(Field field, Shape shape) {
        MatOper.addToMatrix(field.getMatrix(), shape.getPosition(), shape.getId());
    }

    // метод для рендра (добавляет фигуру и тень на поле)
    public static void assembleField(Field field, Shape shape, int[][] finalPosition) {
        MatOper.addToMatrix(field.getMatrix(), finalPosition, -shape.getId());
        MatOper.addToMatrix(field.getMatrix(), shape.getPosition(), shape.getId());
    }

    public static int calcLevel(int totalLines) {
        return totalLines/10;
    }

    public static int calcScore(int totalLines, int level) {
        int base = 0;

        switch (totalLines) {
            case 1 -> base = 40;
            case 2 -> base = 100;
            case 3 -> base = 300;
            case 4 -> base = 1200;
        }

        return base*(level+1);
    }
}
