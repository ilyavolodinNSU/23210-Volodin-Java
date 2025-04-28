package factory.infrastructure.controllers.managers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import factory.GUI.MainFrame;
import factory.core.services.CarServices;
import factory.infrastructure.FactoryState;
import factory.infrastructure.controllers.Dealer;
import factory.infrastructure.controllers.repository.CarRepoController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DealerManager implements Runnable {
    private final int poolSize;
    private final CarServices carServices;
    private final CarRepoController repoController;
    private final FactoryState factoryState;

    @Override
    public void run() {
        ExecutorService pool = Executors.newFixedThreadPool(poolSize);

        Dealer dealer = new Dealer(carServices, repoController, factoryState);

        for (int i = 0; i < poolSize; i++) {
            pool.execute(dealer);
        }
    }
}