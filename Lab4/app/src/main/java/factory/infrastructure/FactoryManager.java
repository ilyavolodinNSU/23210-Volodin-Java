package factory.infrastructure;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import factory.core.entities.Car;
import factory.core.entities.parts.Accessory;
import factory.core.entities.parts.Body;
import factory.core.entities.parts.Motor;
import factory.core.repository.Repository;
import factory.core.services.CarServices;
import factory.core.services.PartServices;
import factory.infrastructure.controllers.managers.DealerManager;
import factory.infrastructure.controllers.managers.SupplierManager;
import factory.infrastructure.controllers.managers.WorkerManager;
import factory.infrastructure.controllers.repository.CarRepoController;
import factory.infrastructure.controllers.repository.PartsRepoMonitor;
import factory.infrastructure.db.orm.HibernateUtil;
import factory.infrastructure.db.repositories.SyncRepository;
import factory.infrastructure.db.repositories.mysql.*;

public class FactoryManager {
    private final PartServices<Motor> motorServices;
    private final PartServices<Body> bodyServices;
    private final PartServices<Accessory> accessoryServices;
    private final CarServices carServices;
    private final BlockingQueue<Integer> tasks;

    // репозитории
    Repository<Motor> motorRepo;
    Repository<Body> bodyRepo;
    Repository<Accessory> accessoryRepo;
    Repository<Car> carRepo;

    // контроллеры
    private final Thread tPM;
    private final Thread tRC;
    private final Thread tSM;
    private final Thread tWM;
    private final Thread tDM;

    // текущее состояние фабрики
    private FactoryState currentState; 

    // вместимость
    private long motorStorageCapacity;
    private long bodyStorageCapacity;
    private long accessoryStorageCapacity;
    private long carStorageCapacity;

    // пулы 
    private int suppsMotorPoolSize;
    private int suppsBodyPoolSize;
    private int suppsAccessoryPoolSize;
    private int workersPoolSize;
    private int dealersPoolSize;

    public FactoryManager(String configPath) {
        var sessionFactory = HibernateUtil.getSessionFactory();

        loadConfig(configPath);

        motorRepo = new SyncRepository<>(new MotorRepositoryMySQL(sessionFactory, motorStorageCapacity));
        bodyRepo = new SyncRepository<>(new BodyRepositoryMySQL(sessionFactory, bodyStorageCapacity));
        accessoryRepo = new SyncRepository<>(new AccessoryRepositoryMySQL(sessionFactory, accessoryStorageCapacity));
        carRepo = new SyncRepository<>(new CarRepositoryMySQL(sessionFactory, carStorageCapacity));

        motorServices = new PartServices<>(motorRepo, Motor::new);
        bodyServices = new PartServices<>(bodyRepo, Body::new);
        accessoryServices = new PartServices<>(accessoryRepo, Accessory::new);
        carServices = new CarServices(carRepo);

        currentState = new FactoryState();

        tasks = new LinkedBlockingQueue<>();

        System.out.println(suppsAccessoryPoolSize);

        PartsRepoMonitor partsRepoMonitor = new PartsRepoMonitor(
            motorRepo, 
            bodyRepo, 
            accessoryRepo, 
            currentState
        );

        CarRepoController repoController = new CarRepoController(
            tasks, 
            carRepo, 
            10, 
            80, 
            currentState
        );

        SupplierManager supplierManager = new SupplierManager(
            motorServices,
            suppsMotorPoolSize, 
            bodyServices, 
            suppsBodyPoolSize, 
            accessoryServices,
            suppsAccessoryPoolSize,
            currentState,
            partsRepoMonitor
        );

        WorkerManager workerManager = new WorkerManager(
            tasks, 
            workersPoolSize, 
            motorServices, 
            bodyServices, 
            accessoryServices, 
            carServices,
            currentState,
            partsRepoMonitor
        );

        DealerManager dealerManager = new DealerManager(
            dealersPoolSize,
            carServices, 
            repoController,
            currentState
        );

        tPM = new Thread(partsRepoMonitor);
        tRC = new Thread(repoController);
        tSM = new Thread(supplierManager);
        tWM = new Thread(workerManager);
        tDM = new Thread(dealerManager);
    }

    public void start() {
        tPM.start();
        tRC.start();
        tSM.start();
        tWM.start();
        tDM.start();
    }

    public void stop() {
        tPM.interrupt();
        tRC.interrupt();
        tSM.interrupt();
        tWM.interrupt();
        tDM.interrupt();
    }

    public void changeDealersDelay(int delay) {
        currentState.getDealersDelay().set(delay);
    }

    public void changeMotorSuppsDelay(int delay) {
        currentState.getSuppMDelay().set(delay);
    }

    public void changeBodySuppsDelay(int delay) {
        currentState.getSuppBDelay().set(delay);
    }

    public void changeAccessorySuppsDelay(int delay) {
        currentState.getSuppsADelay().set(delay);
    }

    public FactoryStateData getStateDto() {
        return FactoryStateMapper.INSTANCE.toDto(currentState);
    }

    private void loadConfig(String path) {
        try {
            Properties config = new Properties();
            config.load(new FileReader(path));

            suppsMotorPoolSize = 1;
            suppsBodyPoolSize = 1;
            suppsAccessoryPoolSize = Integer.parseInt(config.getProperty("AccessorySuppliers"));
            workersPoolSize = Integer.parseInt(config.getProperty("Workers"));
            dealersPoolSize = Integer.parseInt(config.getProperty("Dealers"));
            motorStorageCapacity = Integer.parseInt(config.getProperty("StorageMotorSize"));
            bodyStorageCapacity = Integer.parseInt(config.getProperty("StorageBodySize"));
            accessoryStorageCapacity = Integer.parseInt(config.getProperty("StorageAccessorySize"));
            carStorageCapacity = Integer.parseInt(config.getProperty("StorageAutoSize"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
