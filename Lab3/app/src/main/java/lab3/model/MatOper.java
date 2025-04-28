package lab3.model;

// глупые операции с векторами
// не знает про классы модели, поля и тд

public class MatOper {
    public static boolean collisionMatrix(int[][] fieldMatrix, int[][] shapePosition) {
        for (int[] coord : shapePosition) {
            if (coord[0] >= fieldMatrix[0].length || coord[0] < 0) {
                System.out.println("Коллизия с боковой границей\n");
                return true;
            }

            if (coord[1] >= fieldMatrix.length) {
                System.out.println("Коллизия с нижней границей\n");
                return true;
            }

            if (coord[1] >= 0 && fieldMatrix[coord[1]][coord[0]] != 0) {
                System.out.println("Коллизия с фигурой\n");
                return true;
            }
        }

        return false;
    }

    public static void shiftMatrix(int[][] position, int shiftX, int shiftY) {
        for (int[] coord : position) {
            coord[0] += shiftX;
            coord[1] += shiftY;
        }
    }

    public static int[][] cloneMatrix(int[][] matrix) {
        int[][] newMatrix = new int[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[i].length);
        }

        return newMatrix;
    }

    public static void rotateMatrix(int[][] matrix, boolean clockwise) {
        int sumX = 0, sumY = 0;
        for (int i = 0; i < matrix.length; i++) {
            sumX += matrix[i][0];
            sumY += matrix[i][1];
        }
        double centerX = sumX / (double) matrix.length; 
        double centerY = sumY / (double) matrix.length;
    
        for (int i = 0; i < matrix.length; i++) {
            double x = matrix[i][0] - centerX;
            double y = matrix[i][1] - centerY;
    
            if (clockwise) {
                matrix[i][0] = (int) Math.round(y + centerX);
                matrix[i][1] = (int) Math.round(-x + centerY);
            } else {
                matrix[i][0] = (int) Math.round(-y + centerX);
                matrix[i][1] = (int) Math.round(x + centerY);
            }
        }
    }

    public static void addToMatrix(int[][] fieldMatrix, int[][] shapePosition, int value) {
        for (int[] coord : shapePosition) {
            if (coord[1] >= 0) fieldMatrix[coord[1]][coord[0]] = value;
        }
    }
}
