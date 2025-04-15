package factory.infrastructure;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FactoryState {
    // сигналки
    private boolean activeSuppsMotors = false;
    private boolean activeSuppsBodies = false;
    private boolean activeSuppsAccessories = false;
    private boolean activeWorkers = false;
    private boolean activeRepoController = false;
    private boolean activeDealers = false;

    // счётчики
    private final AtomicInteger activeTasksCounter = new AtomicInteger(0);
    private final AtomicLong motorStorageSize = new AtomicLong(0);
    private final AtomicLong bodyStorageSize = new AtomicLong(0);
    private final AtomicLong accessoryStorageSize = new AtomicLong(0);
    private final AtomicLong carStorageSize = new AtomicLong(0);
    private final AtomicInteger soldCarsCounter = new AtomicInteger(0);

    // задержки
    private final AtomicInteger suppMDelay = new AtomicInteger(0);
    private final AtomicInteger suppBDelay = new AtomicInteger(0);
    private final AtomicInteger suppsADelay = new AtomicInteger(0);
    private final AtomicInteger dealersDelay = new AtomicInteger(0);
}
