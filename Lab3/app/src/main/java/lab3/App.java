package lab3;

import java.lang.reflect.Field;

import javax.swing.border.Border;

import lab3.model.Engine;
import lab3.viewer.GameView;
import lab3.controller.Controller;
import lab3.model.Model;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// фабрика тетромино

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Engine engine = new Engine();
            GameView view = new GameView();
            new Controller(engine, view);
        });
    }
}
