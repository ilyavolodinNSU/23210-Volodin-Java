package lab3.controller;

import javax.swing.Timer;

import lab3.model.Engine;
import lab3.model.EngineStatus;
import lab3.viewer.View;

public class Controller {
    private Engine engine;
    private View view;
    private Timer timer;

    public Controller(Engine engine, View view) {
        this.engine = engine;
        this.view = view;

        view.addMenuListener(new MenuListener(this));
        view.addGameListener(new GameListener(this));

        view.showMenu();
    }

    public void startGame() {
        engine.restart();
        view.render(engine.build());
        view.showGame();

        timer = new Timer(1000, e -> {
            if (engine.getStatus() == EngineStatus.OVER) timer.stop();

            engine.update();
            view.render(engine.build());
        });

        timer.start();
    }

    public void exitGame() {
        view.close();
        System.exit(0);
    }

    public void endGame() {
        timer.stop();
        engine.abort();
        view.showMenu();
        // подтверждение выхода
        // результат
        // выход в меню
    }

    public void restartGame() {
        // подтверждение перезагрузки
        engine.restart();
        timer.restart();
    }

    public void moveFigureToLeft() {
        engine.moveShapeLeft();
        view.render(engine.build());
    }

    public void moveFigureToRight() {
        engine.moveShapeRight();
        view.render(engine.build());
    }

    public void rotateClockwise() {
        engine.rotateShape(true);
        view.render(engine.build());
    }

    public void rotateCounterClockwise() {
        engine.rotateShape(false);
        view.render(engine.build());
    }

    public void dropFigure() {
        timer.restart();
        engine.dropShape();
        engine.update();
        view.render(engine.build());
    }
    
}
