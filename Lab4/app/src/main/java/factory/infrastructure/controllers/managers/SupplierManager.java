package factory.infrastructure.controllers.managers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import factory.core.entities.parts.Accessory;
import factory.core.entities.parts.Body;
import factory.core.entities.parts.Motor;
import factory.core.services.PartServices;
import factory.infrastructure.FactoryState;
import factory.infrastructure.controllers.SupplierAccessory;
import factory.infrastructure.controllers.SupplierBody;
import factory.infrastructure.controllers.SupplierMotor;
import factory.infrastructure.controllers.repository.PartsRepoMonitor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SupplierManager implements Runnable {
    private final PartServices<Motor> motorServices;
    private final int motorPoolSize;
    private final PartServices<Body> bodyServices;
    private final int bodyPoolSize;
    private final PartServices<Accessory> accessoryServices;
    private final int accessoryPoolSize; 
    private final FactoryState factoryState;
    private final PartsRepoMonitor partsRepoMonitor;

    @Override
    public void run() {
        ExecutorService motorPool = Executors.newFixedThreadPool(motorPoolSize);
        ExecutorService bodyPool = Executors.newFixedThreadPool(bodyPoolSize);
        ExecutorService accessoryPool = Executors.newFixedThreadPool(accessoryPoolSize);

        SupplierMotor suppM = new SupplierMotor(motorServices, factoryState, partsRepoMonitor);
        SupplierBody suppB = new SupplierBody(bodyServices, factoryState, partsRepoMonitor);
        SupplierAccessory suppA = new SupplierAccessory(accessoryServices, factoryState, partsRepoMonitor);

        motorPool.execute(suppM);
        bodyPool.execute(suppB);
        
        for (int i = 0; i < accessoryPoolSize; i++) {
            accessoryPool.execute(suppA);
        }
    }
}
