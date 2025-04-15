package factory;

import java.util.Timer;
import java.util.TimerTask;

import factory.GUI.MainFrame;
import factory.infrastructure.FactoryManager;

public class Factory {
    public static void main(String[] args) {
        FactoryManager factory = new FactoryManager("app/src/main/resources/config.properties");
        factory.start();

        MainFrame gui = new MainFrame();

        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                gui.render(factory.getStateDto());
            }
        };

        timer.schedule(timerTask, 0, 1000);
    }
}
