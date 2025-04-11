package factory.infrastructure.controllers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import factory.core.entities.Car;
import factory.infrastructure.SyncRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CarRepoController implements Runnable {
    private final BlockingQueue<Integer> tasks;
    private final SyncRepository<Car> repository;
    private final int repoCapacity;
    private final double triggerPercentBorder;
    private ReentrantLock locker = new ReentrantLock();
    private Condition analysis = locker.newCondition();
    private final AtomicInteger activeTasksCounter;

    @Override
    public void run() {
        while (true) {
            try {
                locker.lock();

                int repoSize = (int)repository.size() + activeTasksCounter.get();

                double upperLimit = triggerPercentBorder/100*repoCapacity;

                System.out.println("RC: колич эл в репозитории " + repository.size() +
                                "\nRC: колич задач " + activeTasksCounter.get());

                int difference = (int)upperLimit - repoSize;

                if (repoSize < upperLimit) {
                    tasks.put(difference);
                    activeTasksCounter.addAndGet(difference);
                    System.out.println("➡️  RC: создача задача поставить " + difference);
                }
                analysis.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                locker.unlock();
            }
        }
    }

    public void signal() {
        System.out.println("сигналим RC");
        locker.lock();
        analysis.signal();
        locker.unlock();
    }
}
