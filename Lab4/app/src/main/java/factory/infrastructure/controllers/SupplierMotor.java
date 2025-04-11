package factory.infrastructure.controllers;

import java.util.concurrent.ThreadLocalRandom;

import factory.core.entities.parts.Motor;
import factory.core.services.PartServices;
import lombok.RequiredArgsConstructor;

// поставщик может: создать, передать на склад, ждать, проверить размер склада

@RequiredArgsConstructor
public class SupplierMotor implements Runnable {
    private final PartServices<Motor> motorService;

    // создает часть и пушит и репозиторий
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // System.out.println("⏳ suppM: init");

            var m = motorService.supply(ThreadLocalRandom.current().nextInt(0, 10000000));
            motorService.store(m);

            // System.out.println("suppM: мотор создан и отправлен");
        }
    }
}
