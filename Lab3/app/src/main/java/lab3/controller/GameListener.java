package lab3.controller;

import java.awt.event.*;

public class GameListener extends KeyAdapter implements ActionListener {
    private Controller controller;

    public GameListener(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> controller.moveFigureToLeft();
            case KeyEvent.VK_D -> controller.moveFigureToRight();
            case KeyEvent.VK_W -> controller.rotateClockwise();
            case KeyEvent.VK_S -> controller.rotateCounterClockwise();
            case KeyEvent.VK_SPACE -> controller.dropFigure();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "restart" -> controller.restartGame();  
            case "back" -> controller.endGame();
                
        }
    }
}
