package lab3;

import lab3.model.Engine;
import lab3.viewer.View;
import lab3.controller.Controller;
import javax.swing.*;

/* Задачи
3) таблица рекордов 1
4) предупреждения при выходе 2
5) графика 1
6) рефакторинг 1
7) регистрация (опционально) 3
8) тесты 2
*/

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Engine engine = new Engine();
            View view = new View();
            new Controller(engine, view);
        });
    }
}
