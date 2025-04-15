package factory.infrastructure.controllers.repository;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import factory.GUI.MainFrame;
import factory.core.entities.Car;
import factory.core.repository.Repository;
import factory.infrastructure.FactoryState;
import factory.infrastructure.db.repositories.SyncRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CarRepoController implements Runnable {
    private final BlockingQueue<Integer> tasks;
    private final Repository<Car> repository;
    private final int repoCapacity;
    private final double triggerPercentBorder;
    private ReentrantLock locker = new ReentrantLock();
    private Condition analysis = locker.newCondition();
    private final FactoryState factoryState;

    @Override
    public void run() {
        while (true) {
            try {
                locker.lock();

                int repoSize = (int)repository.size() + factoryState.getActiveTasksCounter().get();

                double upperLimit = triggerPercentBorder/100*repoCapacity;

                // System.out.println("RC: колич эл в репозитории " + repository.size() +
                //               "\nRC: колич задач " + factoryState.getActiveTasksCounter().get());

                int difference = (int)upperLimit - repoSize;

                if (repoSize < upperLimit) {
                    tasks.put(difference);
                    factoryState.getActiveTasksCounter().addAndGet(difference);
                    // System.out.println("➡️  RC: создача задача поставить " + difference);
                }

                factoryState.getCarStorageSize().set(repository.size());

                analysis.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                locker.unlock();
            }
        }
    }

    public void signal() {
        //System.out.println("сигналим RC");
        locker.lock();
        analysis.signal();
        locker.unlock();
    }
}
