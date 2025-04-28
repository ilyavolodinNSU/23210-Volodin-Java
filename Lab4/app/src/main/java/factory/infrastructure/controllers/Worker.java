package factory.infrastructure.controllers;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

import factory.GUI.MainFrame;
import factory.core.entities.Car;
import factory.core.entities.parts.Accessory;
import factory.core.entities.parts.Body;
import factory.core.entities.parts.Motor;
import factory.core.services.CarServices;
import factory.core.services.PartServices;
import factory.infrastructure.FactoryState;
import factory.infrastructure.controllers.repository.PartsRepoMonitor;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class Worker implements Callable<Boolean> {
    private final PartServices<Motor> motorService;
    private final PartServices<Body> bodyServices;
    private final PartServices<Accessory> accessoryServices;
    private final CarServices carServices;
    private final FactoryState factoryState;
    private final PartsRepoMonitor partsRepoMonitor;

    @Override
    public Boolean call() {
        try {   
            factoryState.setActiveWorkers(true);
            Thread.sleep(1000);

            Car newCar = carServices.assembleCar(
                motorService.retrieve(),
                bodyServices.retrieve(),
                accessoryServices.retrieve(),
                ThreadLocalRandom.current().nextInt(
                    0,
                    10000000
                )
            );

            partsRepoMonitor.signal();

            factoryState.setActiveWorkers(false); 
            
            carServices.store(newCar);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
