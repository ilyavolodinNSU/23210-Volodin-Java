package factory.infrastructure.controllers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import factory.core.entities.Car;
import factory.core.entities.parts.Accessory;
import factory.core.entities.parts.Body;
import factory.core.entities.parts.Motor;
import factory.core.services.CarServices;
import factory.core.services.PartServices;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class Worker implements Callable<Boolean> {
    private final PartServices<Motor> motorService;
    private final PartServices<Body> bodyServices;
    private final PartServices<Accessory> accessoryServices;
    private final CarServices carServices;
    //private final AtomicInteger activeTasksCounter;

    @Override
    public Boolean call() {
        try {
            Thread.sleep(1000);
    
            // System.out.println("⏳ W: init");
    
            Car newCar = carServices.assembleCar(
                motorService.retrieve(),
                bodyServices.retrieve(),
                accessoryServices.retrieve(),
                ThreadLocalRandom.current().nextInt(
                    0,
                    10000000
                    )
                );
            
            carServices.store(newCar);

            //activeTasksCounter.decrementAndGet();
    
            System.out.println("✅ W: машина поставлена");  
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
