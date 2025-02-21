package lab3;

import javax.swing.*;

import lab3.model.Engine;
import lab3.model.Model;
import lab3.viewer.View;

public class App {
    public static void main(String[] args) {
        Engine engine = new Engine();
        
        View view = new View(engine.build());
        view.render();
    }
}
