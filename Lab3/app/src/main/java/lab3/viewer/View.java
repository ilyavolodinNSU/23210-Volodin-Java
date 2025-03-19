package lab3.viewer;

import lab3.Data;
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
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.getContentPane().setBackground(new Color(20, 20, 30));

        // центрируем
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        
        cardContainer = new JPanel();
        cardLayout = new CardLayout();
        cardContainer.setLayout(cardLayout);
        cardContainer.setOpaque(false);

        // меню
        menuPanel = new MenuView();

        //игра 
        gamePanel = new GameView();

        // контейнер
        cardContainer.add(menuPanel, "Меню");
        cardContainer.add(gamePanel, "Игра");

        frame.add(cardContainer, gbc);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
    }

    public void showMenu() {
        cardLayout.show(cardContainer, "Меню");
        frame.revalidate();
        frame.repaint();
    }

    public void showGame() {
        cardLayout.show(cardContainer, "Игра");
        frame.revalidate();
        frame.repaint();
        frame.requestFocusInWindow();
    }

    public void addMenuListener(MenuListener listener) {
        frame.addKeyListener(listener);
        menuPanel.addListener(listener);
    }

    public void addGameListener(GameListener listener) {
        frame.addKeyListener(listener);
        gamePanel.addListener(listener);
    }

    public void close() {
        frame.dispose();
    }

    public void render(Data data) {
        gamePanel.render(data);
        frame.revalidate();
        frame.repaint();
    }
}