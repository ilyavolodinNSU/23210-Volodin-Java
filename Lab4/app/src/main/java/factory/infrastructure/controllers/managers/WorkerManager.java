package factory.infrastructure.controllers.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import factory.GUI.MainFrame;
import factory.core.entities.parts.Accessory;
import factory.core.entities.parts.Body;
import factory.core.entities.parts.Motor;
import factory.core.services.CarServices;
import factory.core.services.PartServices;
import factory.infrastructure.FactoryState;
import factory.infrastructure.controllers.Worker;
import factory.infrastructure.controllers.repository.PartsRepoMonitor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WorkerManager implements Runnable {
    private final BlockingQueue<Integer> tasksQueue;
    private final int poolSize;
    private final PartServices<Motor> motorService;
    private final PartServices<Body> bodyServices;
    private final PartServices<Accessory> accessoryServices;
    private final CarServices carServices;
    private final FactoryState factoryState;
    private final PartsRepoMonitor partsRepoMonitor;

    @Override
    public void run() {
        final ExecutorService pool = Executors.newFixedThreadPool(poolSize);
        List<Callable<Boolean>> workers = new ArrayList<>();

        while (true) {
            try {
                int order = tasksQueue.take();

                if (order == -1) break;

                for (int i = 0; i < order; ++i) {
                    workers.add(new Worker(
                        motorService,
                        bodyServices, 
                        accessoryServices,
                        carServices,
                        factoryState,
                        partsRepoMonitor
                    ));
                }

                pool.invokeAll(workers);

                workers.clear();

                factoryState.getActiveTasksCounter().addAndGet(-order);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
