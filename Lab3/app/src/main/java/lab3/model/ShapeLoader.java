package lab3.model;

import com.google.gson.Gson;
import java.io.InputStreamReader;

public class ShapeLoader {
    public static TetrisShapes loadShapes(String presetsPath) {
        Gson gson = new Gson();
        java.io.InputStream is = ShapeLoader.class.getClassLoader().getResourceAsStream(presetsPath);

        if (is == null) {
            System.err.println("Ошибка: файл 'tetraminos.json' не найден в src/main/resources!");
            return null;
        }
        
        try (InputStreamReader reader = new InputStreamReader(is)) {
            TetrisShapes shapes = gson.fromJson(reader, TetrisShapes.class);
            if (shapes == null || shapes.getShapes() == null) {
                System.err.println("Ошибка: JSON пустой или некорректный!");
                return null;
            }
            return shapes;
        } catch (Exception e) {
            System.err.println("Ошибка при чтении JSON: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}