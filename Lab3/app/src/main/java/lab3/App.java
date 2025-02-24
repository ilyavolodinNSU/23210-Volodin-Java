package lab3;

import javax.swing.*;

import lab3.model.Engine;
import lab3.viewer.View;
import lab3.model.EngineStatus;

// фабрика тетромино

public class App {
    public static void main(String[] args) {
        Engine engine = new Engine();
        View view = new View();

        // while(engine.getStatus() == EngineStatus.RUN) {
        //     engine.update();
        // }

        for (int i = 0; i < 105; i++) {
            switch (i) {
                case 0:
                    engine.moveRight();
                    engine.moveRight();
                    engine.moveRight();
                    engine.moveRight();
                    break;
                case 21:
                    engine.moveRight();
                    engine.moveRight();
                    break;
                case 42:
                    engine.moveLeft();
                    engine.moveLeft();
                    engine.moveLeft();
                    engine.moveLeft();
                    break;
                case 64:
                    engine.moveLeft();
                    engine.moveLeft();
                    break;
            }

            engine.update();
        }

        view.render(engine.build());

        System.out.println(engine.getStatus());

    }
}
