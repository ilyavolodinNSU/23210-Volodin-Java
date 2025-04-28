package factory.infrastructure.controllers;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import factory.GUI.MainFrame;
import factory.core.entities.parts.Body;
import factory.core.services.PartServices;
import factory.infrastructure.FactoryState;
import factory.infrastructure.controllers.repository.PartsRepoMonitor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

// поставщик может: создать, передать на склад, ждать, проверить размер склада

@AllArgsConstructor
public class SupplierBody implements Runnable {
    private final PartServices<Body> bodyService;
    private final FactoryState factoryState;
    private final PartsRepoMonitor partsRepoMonitor;

    // создает часть и пушит и репозиторий
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(factoryState.getSuppBDelay().get() * 1000);

                factoryState.setActiveSuppsBodies(true);
                Thread.sleep(1000);

                var m = bodyService.supply(ThreadLocalRandom.current().nextInt(0, 10000000));

                factoryState.setActiveSuppsBodies(false);

                bodyService.store(m);

                partsRepoMonitor.signal();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
