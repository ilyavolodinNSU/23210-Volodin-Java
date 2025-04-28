package lab3.controller;

import java.awt.event.*;

public class MenuListener extends KeyAdapter implements ActionListener {
    private Controller controller;

    public MenuListener(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "start" -> controller.startGame();
            case "exit" -> controller.exitGame();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE -> controller.exitGame();
        }
    }
}
