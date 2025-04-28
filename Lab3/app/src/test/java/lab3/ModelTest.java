package lab3;

import org.junit.jupiter.api.*;

import lab3.model.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

class ModelTest {
    private Field field;
    private Shape shape;
    private Engine engine;

    @BeforeEach
    void setUp() {
        field = new Field();
        shape = new Shape(1, new int[][]{{5,0}, {6,0}, {5,1}, {6,1}}); // Квадратная фигура
        engine = new Engine();
    }

    @Test
    void testFieldOperations() {
        // Проверка работы с ячейками
        field.setCell(2, 3, 5);
        assertEquals(5, field.getCell(2, 3));
        
        // Проверка сброса строки
        field.resetRow(5);
        assertArrayEquals(new int[10], field.getRow(5));
    }

    // @Test
    // void testShapeMovement() {
    //     int[][] originalPos = shape.getPosition();
        
    //     // Движение влево
    //     GameLogic.moveLeft(field, shape);
    //     assertNotEquals(originalPos[0][0], shape.getPosition()[0][0]);
        
    //     // Движение вправо
    //     GameLogic.moveRight(field, shape);
    //     assertEquals(originalPos[0][0], shape.getPosition()[0][0]);
        
    //     // Движение вниз
    //     GameLogic.moveDown(shape);
    //     assertEquals(originalPos[0][1]+1, shape.getPosition()[0][1]);
    // }

    @Test
    void testShapeRotation() {
        int[][] original = MatOper.cloneMatrix(shape.getPosition());
        
        // Вращение по часовой
        GameLogic.rotate(field, shape, true);
        assertFalse(Arrays.deepEquals(original, shape.getPosition()));
        
        // Вращение против часовой
        GameLogic.rotate(field, shape, false);
        assertTrue(Arrays.deepEquals(original, shape.getPosition()));
    }

    @Test
    void testEngineLogic() {
        // Проверка начального состояния
        assertEquals(EngineStatus.RUN, engine.getStatus());
        
        // Проверка движения через движок
        engine.moveShapeLeft();
        engine.moveShapeRight();
        
        // Проверка сброса
        engine.abort();
        assertEquals(EngineStatus.OVER, engine.getStatus());
        
        engine.restart();
        assertEquals(EngineStatus.RUN, engine.getStatus());
    }

    @Test
    void testMatrixOperations() {
        int[][] matrix = {{0,1}, {1,0}};
        int[][] cloned = MatOper.cloneMatrix(matrix);
        
        // Проверка клонирования
        assertTrue(Arrays.deepEquals(matrix, cloned));
        
        // Проверка сдвига
        MatOper.shiftMatrix(cloned, 1, 0);
        assertEquals(1, cloned[0][0]);
        
        // Проверка коллизий
        assertFalse(MatOper.collisionMatrix(new int[20][10], shape.getPosition()));
    }

    @Test
    void testScoreCalculation() {
        assertEquals(40, GameLogic.calcScore(1, 0));
        assertEquals(100, GameLogic.calcScore(2, 0));
        assertEquals(300, GameLogic.calcScore(3, 0));
        assertEquals(1200, GameLogic.calcScore(4, 0));
    }

    @Test
    void testShapeCloning() {
        Shape clone = shape.clone();
        assertNotSame(shape.getPosition(), clone.getPosition());
        assertTrue(Arrays.deepEquals(shape.getPosition(), clone.getPosition()));
    }
}