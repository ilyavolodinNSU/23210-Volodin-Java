package lab3;

import javax.swing.*;

import lab3.model.Engine;
import lab3.viewer.View;
import lab3.model.EngineStatus;

// p.s можно переделать физику: при спавне фигуры расчет её коллизий и тд для финального состояния,
// а потом просто двигать на n итераций, которые были расчитаны для финального состояния

public class App {
    public static void main(String[] args) {
        Engine engine = new Engine();
        View view = new View();

        while(engine.getStatus() == EngineStatus.RUN) {
            engine.update();
        }

        // for (int i = 0; i < 83; i++) {
        //     engine.update();
        // }

        view.render(engine.build());

        System.out.println(engine.getStatus());

    }
}
