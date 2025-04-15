package factory.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FactoryStateData {
    // сигналки
    private boolean activeSuppsMotors;
    private boolean activeSuppsBodies;
    private boolean activeSuppsAccessories;
    private boolean activeWorkers;
    private boolean activeRepoController;
    private boolean activeDealers;

    // счётчики
    private final int activeTasksCounter;
    private final long motorStorageSize;
    private final long bodyStorageSize;
    private final long accessoryStorageSize;
    private final long carStorageSize;
    private final int soldCarsCounter;

    // задержки
    private final int suppMDelay;
    private final int suppBDelay;
    private final int suppsADelay;
    private final int dealersDelay;  
}
