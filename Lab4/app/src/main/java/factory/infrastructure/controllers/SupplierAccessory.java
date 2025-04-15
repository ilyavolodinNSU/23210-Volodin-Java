package factory.infrastructure.controllers;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import factory.GUI.MainFrame;
import factory.core.entities.parts.Accessory;
import factory.core.services.PartServices;
import factory.infrastructure.FactoryState;
import factory.infrastructure.controllers.repository.PartsRepoMonitor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

// поставщик может: создать, передать на склад, ждать, проверить размер склада

@AllArgsConstructor
public class SupplierAccessory implements Runnable {
    private final PartServices<Accessory> accessoryService;
    private final FactoryState factoryState;
    private final PartsRepoMonitor partsRepoMonitor;

    // создает часть и пушит и репозиторий
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(factoryState.getSuppsADelay().get() * 1000);

                factoryState.setActiveSuppsAccessories(true);
                Thread.sleep(1000);

                var m = accessoryService.supply(ThreadLocalRandom.current().nextInt(0, 10000000));

                factoryState.setActiveSuppsAccessories(false);

                accessoryService.store(m);

                partsRepoMonitor.signal();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
