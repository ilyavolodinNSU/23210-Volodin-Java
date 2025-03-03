package lab3.viewer;

import lab3.model.Model;
import lab3.controller.GameListener;
import lab3.controller.MenuListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Map;

public class View {
    private JFrame frame;
    private MenuView menuPanel;
    private GameView gamePanel;
    private JPanel cardContainer;
    private CardLayout cardLayout;

    public View() {
        frame = new JFrame("TETRIS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        //frame.setUndecorated(true);
        
        cardContainer = new JPanel();
        cardLayout = new CardLayout();
        cardContainer.setLayout(cardLayout);

        // меню
        menuPanel = new MenuView();

        //игра 
        gamePanel = new GameView();

        // контейнер
        cardContainer.add(menuPanel, "Меню");
        cardContainer.add(gamePanel, "Игра");

        // фрейм
        frame.add(cardContainer);
        frame.pack();
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.requestFocusInWindow();

        showMenu();
    }

    public void showMenu() {
        cardLayout.show(cardContainer, "Меню");
        frame.pack();
        //frame.requestFocusInWindow();
    }

    public void showGame() {
        cardLayout.show(cardContainer, "Игра");
        frame.pack();
        frame.requestFocusInWindow();
    }

    public void addMenuListener(MenuListener listener) {
        menuPanel.addListener(listener);
    }

    public void addGameListener(GameListener listener) {
        frame.addKeyListener(listener);
        gamePanel.addListener(listener);
    }

    public void close() {
        frame.dispose();
    }

    public void render(Model model) {
        gamePanel.render(model);
        frame.revalidate();
        frame.repaint();
    }
}