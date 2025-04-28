package lab3;

import lab3.model.Engine;
import lab3.viewer.View;
import lab3.controller.Controller;
import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Engine engine = new Engine();
            View view = new View();
            new Controller(engine, view);
        });
    }
}
