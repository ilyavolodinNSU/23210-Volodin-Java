package factory.infrastructure.controllers;

import java.util.concurrent.ThreadLocalRandom;

import factory.core.entities.parts.Body;
import factory.core.services.PartServices;
import lombok.RequiredArgsConstructor;

// поставщик может: создать, передать на склад, ждать, проверить размер склада


@RequiredArgsConstructor
public class SupplierBody implements Runnable {
    private final PartServices<Body> bodyService;

    // создает часть и пушит и репозиторий
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // System.out.println("⏳ suppB: init");

            var m = bodyService.supply(ThreadLocalRandom.current().nextInt(0, 10000000));
            bodyService.store(m);

            // System.out.println("suppB: корпус создан и отправлен");
        }
    }
}
