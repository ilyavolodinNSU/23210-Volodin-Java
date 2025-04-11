package factory.infrastructure.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.glassfish.jaxb.runtime.v2.runtime.reflect.Accessor;

import factory.core.entities.parts.Accessory;
import factory.core.entities.parts.Body;
import factory.core.entities.parts.Motor;
import factory.core.services.CarServices;
import factory.core.services.PartServices;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WorkerManager implements Runnable {
    private final BlockingQueue<Integer> tasksQueue;
    private final int poolSize;
    private final PartServices<Motor> motorService;
    private final PartServices<Body> bodyServices;
    private final PartServices<Accessory> accessoryServices;
    private final CarServices carServices;
    private final AtomicInteger activeTasksCounter;

    @Override
    public void run() {
        final ExecutorService pool = Executors.newFixedThreadPool(poolSize);
        List<Callable<Boolean>> workers = new ArrayList<>();

        while (true) {
            try {
                // System.out.println("WM: ожидаем задачу...");
                int order = tasksQueue.take();
                System.out.println("❗ WM: задача: создать " + order + " машин");

                if (order == -1) break;

                for (int i = 0; i < order; ++i) {
                    workers.add(new Worker(motorService, bodyServices, accessoryServices, carServices));
                }

                pool.invokeAll(workers);

                workers.clear();

                activeTasksCounter.addAndGet(-order);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // System.out.println("завершение работы WM");
    }
}
