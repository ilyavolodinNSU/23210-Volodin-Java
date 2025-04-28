package factory.infrastructure.controllers.repository;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import factory.GUI.MainFrame;
import factory.core.entities.Car;
import factory.core.entities.parts.Accessory;
import factory.core.entities.parts.Body;
import factory.core.entities.parts.Motor;
import factory.core.repository.Repository;
import factory.infrastructure.FactoryState;
import factory.infrastructure.db.repositories.SyncRepository;
import factory.infrastructure.db.repositories.mysql.BodyRepositoryMySQL;
import factory.infrastructure.db.repositories.mysql.MotorRepositoryMySQL;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PartsRepoMonitor implements Runnable {
    private final Repository<Motor> motorRepo;
    private final Repository<Body> bodyRepo;
    private final Repository<Accessory> accessoryRepo;
    private final FactoryState factoryState;

    private ReentrantLock locker = new ReentrantLock();
    private Condition condition = locker.newCondition();

    @Override
    public void run() {
        while (true) {
            try {
                locker.lock();

                factoryState.getMotorStorageSize().set(motorRepo.size());
                factoryState.getBodyStorageSize().set(bodyRepo.size());
                factoryState.getAccessoryStorageSize().set(accessoryRepo.size());

                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                locker.unlock();
            }
        }
    }

    public void signal() {
        locker.lock();
        condition.signal();
        locker.unlock();
    }
}
