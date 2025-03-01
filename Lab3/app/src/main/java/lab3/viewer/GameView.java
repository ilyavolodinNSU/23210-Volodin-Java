package lab3.viewer;

import lab3.model.Model;
import lab3.controller.GameListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GameView {
    private JFrame frame;
    private JPanel matrixPanel;
    private JPanel menuPanel;
    private JPanel gamePanel;
    private int cellSize = 30;
    private JButton exitButton;
    private JButton restartButton;
    private JButton exitGameButton;
    private JButton newGameButton;
    private JPanel cardContainer;
    private CardLayout cardLayout;

    public GameView() {
        frame = new JFrame("TETRIS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        
        cardContainer = new JPanel();
        cardLayout = new CardLayout();
        cardContainer.setLayout(cardLayout);

        // меню
        menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout());
        menuPanel.setBackground(Color.BLACK);
        newGameButton = new JButton("Новая игра");
        newGameButton.setActionCommand("start");
        JButton scoreTableButton = new JButton("Счёт");
        scoreTableButton.setActionCommand("table");
        exitGameButton = new JButton("Выйти");
        exitGameButton.setActionCommand("exit");
        menuPanel.add(newGameButton);
        menuPanel.add(scoreTableButton);
        menuPanel.add(exitGameButton);
        menuPanel.setPreferredSize(new Dimension(300, 400));

        //игра 
        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        headerPanel.setBackground(Color.LIGHT_GRAY);
        headerPanel.setPreferredSize(new Dimension(0, 50));
        this.restartButton = new JButton("Новая игра");
        this.restartButton.setActionCommand("restart");
        this.restartButton.setFocusable(false);
        this.exitButton = new JButton("Выход");
        this.exitButton.setActionCommand("back");
        this.exitButton.setFocusable(false);
        headerPanel.add(restartButton);
        headerPanel.add(exitButton);

        matrixPanel = new JPanel();

        gamePanel.add(headerPanel, BorderLayout.NORTH);
        gamePanel.add(matrixPanel, BorderLayout.CENTER);

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
        matrixPanel.removeAll();
        cardLayout.show(cardContainer, "Меню");
        frame.pack();
        frame.requestFocusInWindow();
    }

    public void showGame() {
        cardLayout.show(cardContainer, "Игра");
        frame.pack();
        frame.requestFocusInWindow();
    }

    public void addListener(GameListener listener) {
        frame.addKeyListener(listener);
        exitButton.addActionListener(listener);
        restartButton.addActionListener(listener);
        exitGameButton.addActionListener(listener);
        newGameButton.addActionListener(listener);
    }

    public void close() {
        frame.dispose();
    }

    public void render(Model model) {
        int fieldWidth = model.getField().getWidth();
        int fieldHeight = model.getField().getHeight();
        int frameWidth = fieldWidth * cellSize;
        int frameHeight = fieldHeight * cellSize + 50;

        matrixPanel.removeAll();
        matrixPanel.setLayout(new GridLayout(fieldHeight, fieldWidth));

        Border gridBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

        for (int i = 0; i < fieldHeight; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(cellSize, cellSize));
                cell.setBackground(model.getField().getCell(j, i) == 0 ? new Color(55,55,55) :
                                  model.getField().getCell(j, i) == 1 ? new Color(55,55,55) :
                                  model.getField().getCell(j, i) == 9 ? new Color(255,68,0) : new Color(255,68,0, 128));

                cell.setBorder(gridBorder);
                matrixPanel.add(cell);
            }
        }

        frame.revalidate();
        frame.repaint();
        frame.pack();
    }
}