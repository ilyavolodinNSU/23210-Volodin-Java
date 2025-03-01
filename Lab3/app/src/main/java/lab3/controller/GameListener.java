package lab3.controller;

import java.awt.event.*;

public class GameListener extends KeyAdapter implements ActionListener {
    private Controller controller;

    public GameListener(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) controller.moveFigureToLeft();
        else if (e.getKeyCode() == KeyEvent.VK_D) controller.moveFigureToRight();
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) controller.dropFigure();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("back")) controller.endGame();
        else if (e.getActionCommand().equals("restart")) controller.restartGame();
        else if (e.getActionCommand().equals("start")) controller.startGame();
        else if (e.getActionCommand().equals("table")) controller.exitGame();
        else if (e.getActionCommand().equals("exit")) controller.exitGame();
    }
}
