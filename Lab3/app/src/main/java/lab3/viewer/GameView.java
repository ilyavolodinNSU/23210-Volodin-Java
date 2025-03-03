package lab3.viewer;

import java.util.Map;

import javax.swing.*;
import javax.swing.border.Border;

import lab3.controller.GameListener;
import lab3.model.Model;

import java.awt.*;

public class GameView extends JPanel {
    private Map<Integer, Color> colorsMap;
    private JPanel matrixPanel;
    private int cellSize = 30;
    private JButton exitButton;
    private JButton restartButton;
    private JLabel scoreLabel;
    private JLabel levelLabel;

    public GameView() {
        colorsMap = ColorsLoader.loadColors("/presets.json");
        setFocusable(true);
        requestFocusInWindow();


        setLayout(new BorderLayout());

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
        this.scoreLabel = new JLabel("Score");
        this.levelLabel = new JLabel("Level");

        headerPanel.add(restartButton);
        headerPanel.add(exitButton);
        headerPanel.add(scoreLabel);
        headerPanel.add(levelLabel);
        

        matrixPanel = new JPanel();

        add(headerPanel, BorderLayout.NORTH);
        add(matrixPanel, BorderLayout.CENTER);
    }

    public void addListener(GameListener listener) {
        exitButton.addActionListener(listener);
        restartButton.addActionListener(listener);
    }

    public void render(Model model) {
        this.scoreLabel.setText("Score " + model.getScore());
        this.levelLabel.setText("Level " + model.getLevel());

        int fieldWidth = model.getField().getWidth();
        int fieldHeight = model.getField().getHeight();

        matrixPanel.removeAll();
        matrixPanel.setLayout(new GridLayout(fieldHeight, fieldWidth));

        Border gridBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

        for (int i = 0; i < fieldHeight; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(cellSize, cellSize));
                cell.setBackground(colorsMap.get(model.getField().getCell(j, i)));

                cell.setBorder(gridBorder);
                matrixPanel.add(cell);
            }
        }
    }
}
