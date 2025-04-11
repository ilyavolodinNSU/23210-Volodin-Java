package factory.infrastructure.controllers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import factory.core.services.CarServices;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DealerManager implements Runnable {
    private final int poolSize;
    private final CarServices carServices;
    private final CarRepoController repoController;

    @Override
    public void run() {
        ExecutorService pool = Executors.newFixedThreadPool(poolSize);

        Dealer dealer = new Dealer(carServices, repoController);

        for (int i = 0; i < poolSize; i++) {
            pool.execute(dealer);
        }
    }
}