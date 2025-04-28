package factory.infrastructure.controllers;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import factory.GUI.MainFrame;
import factory.core.entities.parts.Motor;
import factory.core.services.PartServices;
import factory.infrastructure.FactoryState;
import factory.infrastructure.controllers.repository.PartsRepoMonitor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

// поставщик может: создать, передать на склад, ждать, проверить размер склада

@AllArgsConstructor
public class SupplierMotor implements Runnable {
    private final PartServices<Motor> motorService;
    private final FactoryState factoryState;
    private final PartsRepoMonitor partsRepoMonitor;

    // создает часть и пушит и репозиторий
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(factoryState.getSuppMDelay().get() * 1000);

                factoryState.setActiveSuppsMotors(true);
                Thread.sleep(1000);

                var m = motorService.supply(ThreadLocalRandom.current().nextInt(0, 10000000));

                factoryState.setActiveSuppsMotors(false);

                motorService.store(m);

                partsRepoMonitor.signal();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
