package lab3.viewer;

import java.util.Map;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import lab3.Data;
import lab3.controller.GameListener;

public class GameView extends JPanel {
    private Map<Integer, Color> colorsMap;
    private JPanel matrixPanel;
    private int cellSize;
    private JButton exitButton;
    private JButton restartButton;
    private JLabel scoreLabel;
    private JLabel levelLabel;
    private int panelWidth;
    private static final int SCREEN_MARGIN = 20;
    private static final int HEADER_PADDING_VERTICAL = 20;
    private static final int HEADER_PADDING_HORIZONTAL = 20;
    private static final int FIELD_BORDER_THICKNESS = 2;
    private static final int FIELD_PADDING = 10;
    private static final int HEADER_CONTENT_HEIGHT = 40;
    private static final int GRID_GAP = 1;
    private static final int PANEL_GAP = 10;

    public GameView() {
        colorsMap = ColorsLoader.loadColors("/presets.json");
        setFocusable(true);
        requestFocusInWindow();
        setBackground(new Color(20, 20, 30));

        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int availableHeight = screenHeight - (2 * SCREEN_MARGIN);

        int fixedHeight = HEADER_CONTENT_HEIGHT + HEADER_PADDING_VERTICAL + 
                         (2 * FIELD_BORDER_THICKNESS) + FIELD_PADDING + PANEL_GAP;
        int fieldHeight = availableHeight - fixedHeight;
        cellSize = fieldHeight / 20;
        int actualFieldHeight = cellSize * 20;

        panelWidth = (cellSize * 10) + (2 * FIELD_BORDER_THICKNESS) + FIELD_PADDING;
        int totalPanelHeight = actualFieldHeight + fixedHeight;
        setPreferredSize(new Dimension(panelWidth, totalPanelHeight));
        setMaximumSize(new Dimension(panelWidth, totalPanelHeight));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBackground(new Color(30, 30, 40));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.setPreferredSize(new Dimension(panelWidth, HEADER_CONTENT_HEIGHT + HEADER_PADDING_VERTICAL));
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, HEADER_CONTENT_HEIGHT + HEADER_PADDING_VERTICAL));

        headerPanel.add(Box.createHorizontalGlue());
        restartButton = createStyledButton("Новая игра", new Color(0, 150, 136));
        restartButton.setActionCommand("restart");
        headerPanel.add(restartButton);
        
        headerPanel.add(Box.createHorizontalStrut(20));
        scoreLabel = createStyledLabel("Score: 0");
        headerPanel.add(scoreLabel);
        
        headerPanel.add(Box.createHorizontalStrut(20));
        levelLabel = createStyledLabel("Level: 1");
        headerPanel.add(levelLabel);
        
        headerPanel.add(Box.createHorizontalStrut(20));
        exitButton = createStyledButton("Выход", new Color(239, 83, 80));
        exitButton.setActionCommand("back");
        headerPanel.add(exitButton);
        headerPanel.add(Box.createHorizontalGlue());

        matrixPanel = new JPanel();
        matrixPanel.setBackground(new Color(25, 25, 35));
        matrixPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 50, 60), FIELD_BORDER_THICKNESS),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        int fieldWidthPixels = cellSize * 10;
        matrixPanel.setPreferredSize(new Dimension(fieldWidthPixels, actualFieldHeight));
        matrixPanel.setMaximumSize(new Dimension(fieldWidthPixels, actualFieldHeight));

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        add(headerPanel, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.gridy = 1;
        gbc.insets = new Insets(PANEL_GAP, 0, 0, 0);
        add(matrixPanel, gbc);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, HEADER_CONTENT_HEIGHT - 16));
        button.setMaximumSize(new Dimension(120, HEADER_CONTENT_HEIGHT - 16));
        return button;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return label;
    }

    public void addListener(GameListener listener) {
        exitButton.addActionListener(listener);
        restartButton.addActionListener(listener);
    }

    public void render(Data data) {
        scoreLabel.setText("Score: " + data.getScore());
        levelLabel.setText("Level: " + data.getLevel());

        int fieldWidth = data.getField().getWidth();
        int fieldHeight = data.getField().getHeight();

        matrixPanel.removeAll();
        matrixPanel.setLayout(new GridLayout(fieldHeight, fieldWidth, GRID_GAP, GRID_GAP));

        Border cellBorder = BorderFactory.createLineBorder(new Color(50, 50, 60), 1);

        for (int i = 0; i < fieldHeight; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(cellSize - GRID_GAP, cellSize - GRID_GAP));
                cell.setMinimumSize(new Dimension(cellSize - GRID_GAP, cellSize - GRID_GAP));
                cell.setMaximumSize(new Dimension(cellSize - GRID_GAP, cellSize - GRID_GAP));
                Color baseColor = colorsMap.get(data.getField().getCell(j, i));
                cell.setBackground(baseColor);
                cell.setBorder(cellBorder);
                matrixPanel.add(cell);
            }
        }
        matrixPanel.revalidate();
        matrixPanel.repaint();
        revalidate();
        repaint();
    }
}