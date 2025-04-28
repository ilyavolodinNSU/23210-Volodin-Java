package lab3.viewer;

import java.awt.*;
import javax.swing.*;

import lab3.controller.MenuListener;

public class MenuView extends JPanel {
    private JButton exitGameButton;
    private JButton newGameButton;

    public MenuView() {
        setBackground(new Color(20, 20, 30));
        setPreferredSize(new Dimension(400, 500));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel title = new JLabel("TETRIS");
        title.setFont(new Font("SansSerif", Font.BOLD, 48));
        title.setForeground(new Color(0, 150, 136));

        newGameButton = createStyledButton("Новая игра", new Color(0, 150, 136));
        newGameButton.setActionCommand("start");

        exitGameButton = createStyledButton("Выйти", new Color(239, 83, 80));
        exitGameButton.setActionCommand("exit");

        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);

        gbc.gridy = 1;
        add(newGameButton, gbc);

        gbc.gridy = 2;
        add(exitGameButton, gbc);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        return button;
    }

    public void addListener(MenuListener listener) {
        exitGameButton.addActionListener(listener);
        newGameButton.addActionListener(listener);
    }
}