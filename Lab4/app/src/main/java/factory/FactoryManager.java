package factory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

import factory.core.entities.Car;
import factory.core.entities.parts.Accessory;
import factory.core.entities.parts.Body;
import factory.core.entities.parts.Motor;
import factory.core.services.CarServices;
import factory.core.services.PartServices;
import factory.infrastructure.SyncRepository;
import factory.infrastructure.controllers.Dealer;
import factory.infrastructure.controllers.DealerManager;
import factory.infrastructure.controllers.CarRepoController;
import factory.infrastructure.controllers.SupplierAccessory;
import factory.infrastructure.controllers.SupplierBody;
import factory.infrastructure.controllers.SupplierManager;
import factory.infrastructure.controllers.SupplierMotor;
import factory.infrastructure.controllers.WorkerManager;
import factory.infrastructure.db.orm.HibernateUtil;
import factory.infrastructure.db.repositories.mysql.AccessoryRepositoryMySQL;
import factory.infrastructure.db.repositories.mysql.BodyRepositoryMySQL;
import factory.infrastructure.db.repositories.mysql.CarRepositoryMySQL;
import factory.infrastructure.db.repositories.mysql.MotorRepositoryMySQL;

public class FactoryManager {
    public static void main(String[] args) {
        var sessionFactory = HibernateUtil.getSessionFactory();

        BlockingQueue<Integer> tasks = new LinkedBlockingQueue<>();

        SyncRepository<Motor> syncMotorRepo = new SyncRepository<>(new MotorRepositoryMySQL(sessionFactory));
        SyncRepository<Body> syncBodyRepo = new SyncRepository<>(new BodyRepositoryMySQL(sessionFactory));
        SyncRepository<Accessory> syncAccessoryRepo = new SyncRepository<>(new AccessoryRepositoryMySQL(sessionFactory));
        SyncRepository<Car> syncCarRepo = new SyncRepository<>(new CarRepositoryMySQL(sessionFactory));

        PartServices<Motor> motorServices = new PartServices<>(syncMotorRepo, Motor::new);
        PartServices<Body> bodyServices = new PartServices<>(syncBodyRepo, Body::new);
        PartServices<Accessory> accessoryServices = new PartServices<>(syncAccessoryRepo, Accessory::new);
        CarServices carServices = new CarServices(syncCarRepo);

        int suppsMotorPoolSize = 1;
        int suppsBodyPoolSize = 1;
        int suppsAccessoryPoolSize = 3;
        int workersPoolSize = 3;
        int dealersPoolSize = 2;

        AtomicInteger activeTasksCounter = new AtomicInteger(0);

        CarRepoController repoController = new CarRepoController(tasks, syncCarRepo, 10, 80, activeTasksCounter);

        SupplierManager supplierManager = new SupplierManager(
            motorServices,
            suppsMotorPoolSize, 
            bodyServices, 
            suppsBodyPoolSize, 
            accessoryServices, 
            suppsAccessoryPoolSize
        );

        WorkerManager workerManager = new WorkerManager(
            tasks, 
            workersPoolSize, 
            motorServices, 
            bodyServices, 
            accessoryServices, 
            carServices,
            activeTasksCounter
        );

        DealerManager dealerManager = new DealerManager(
            dealersPoolSize,
            carServices, 
            repoController
        );

        Thread tRC = new Thread(repoController);
        Thread tSM = new Thread(supplierManager);
        Thread tWM = new Thread(workerManager);
        Thread tDM = new Thread(dealerManager);

        tRC.start();
        tSM.start();
        tWM.start();
        tDM.start();
    }
}
