package lab3.viewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import lab3.controller.MenuListener;

public class MenuView extends JPanel {
    private JButton exitGameButton;
    private JButton newGameButton;

    public MenuView() {
        setLayout(new FlowLayout());
        setBackground(Color.BLACK);
        newGameButton = new JButton("Новая игра");
        newGameButton.setActionCommand("start");
        JButton scoreTableButton = new JButton("Счёт");
        scoreTableButton.setActionCommand("table");
        exitGameButton = new JButton("Выйти");
        exitGameButton.setActionCommand("exit");
        add(newGameButton);
        add(scoreTableButton);
        add(exitGameButton);
        setPreferredSize(new Dimension(300, 400));
    }

    public void addListener(MenuListener listener) {
        exitGameButton.addActionListener(listener);
        newGameButton.addActionListener(listener);
    }
}
