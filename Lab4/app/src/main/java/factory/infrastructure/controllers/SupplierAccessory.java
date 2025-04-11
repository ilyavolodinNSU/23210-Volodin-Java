package factory.infrastructure.controllers;

import java.util.concurrent.ThreadLocalRandom;

import factory.core.entities.parts.Accessory;
import factory.core.services.PartServices;
import lombok.RequiredArgsConstructor;

// поставщик может: создать, передать на склад, ждать, проверить размер склада

@RequiredArgsConstructor
public class SupplierAccessory implements Runnable {
    private final PartServices<Accessory> accessoryService;

    // создает часть и пушит и репозиторий
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // System.out.println("⏳ suppA: init");

            var m = accessoryService.supply(ThreadLocalRandom.current().nextInt(0, 10000000));
            accessoryService.store(m);

            // System.out.println("suppA: акссесуар создан и отправлен");
        }
    }
}
