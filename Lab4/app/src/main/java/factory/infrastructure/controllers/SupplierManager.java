package factory.infrastructure.controllers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import factory.core.entities.parts.Accessory;
import factory.core.entities.parts.Body;
import factory.core.entities.parts.Motor;
import factory.core.services.PartServices;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SupplierManager implements Runnable {
    private final PartServices<Motor> motorServices;
    private final int motorPoolSize;
    private final PartServices<Body> bodyServices;
    private final int bodyPoolSize;
    private final PartServices<Accessory> accessoryServices;
    private final int accessoryPoolSize;
    
    @Override
    public void run() {
        ExecutorService motorPool = Executors.newFixedThreadPool(motorPoolSize);
        ExecutorService bodyPool = Executors.newFixedThreadPool(bodyPoolSize);
        ExecutorService accessoryPool = Executors.newFixedThreadPool(accessoryPoolSize);

        SupplierMotor suppM = new SupplierMotor(motorServices);
        SupplierBody suppB = new SupplierBody(bodyServices);
        SupplierAccessory suppA = new SupplierAccessory(accessoryServices);

        motorPool.execute(suppM);
        bodyPool.execute(suppB);
        
        for (int i = 0; i < accessoryPoolSize; i++) {
            accessoryPool.execute(suppA);
        }

        // motorPool.shutdown();
        // bodyPool.shutdown();
        // accessoryPool.shutdown();

        // System.out.println("завершение работы SM");
    }
}
