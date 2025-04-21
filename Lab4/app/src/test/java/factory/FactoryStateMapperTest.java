package factory;

import org.junit.jupiter.api.Test;

import factory.infrastructure.FactoryState;
import factory.infrastructure.FactoryStateData;
import factory.infrastructure.FactoryStateMapper;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import static org.junit.jupiter.api.Assertions.*;

class FactoryStateMapperTest {

    @Test
    void testToDto() {
        FactoryState state = new FactoryState();
        state.setActiveSuppsMotors(true);
        state.setActiveSuppsBodies(false);
        state.setActiveSuppsAccessories(true);
        state.setActiveWorkers(false);
        state.setActiveRepoController(true);
        state.setActiveDealers(false);
        state.getActiveTasksCounter().set(5);
        state.getMotorStorageSize().set(100);
        state.getBodyStorageSize().set(200);
        state.getAccessoryStorageSize().set(300);
        state.getCarStorageSize().set(50);
        state.getSoldCarsCounter().set(10);
        state.getSuppMDelay().set(1);
        state.getSuppBDelay().set(2);
        state.getSuppsADelay().set(3);
        state.getDealersDelay().set(4);

        FactoryStateData dto = FactoryStateMapper.INSTANCE.toDto(state);

        assertTrue(dto.isActiveSuppsMotors());
        assertFalse(dto.isActiveSuppsBodies());
        assertTrue(dto.isActiveSuppsAccessories());
        assertFalse(dto.isActiveWorkers());
        assertTrue(dto.isActiveRepoController());
        assertFalse(dto.isActiveDealers());
        assertEquals(5, dto.getActiveTasksCounter());
        assertEquals(100, dto.getMotorStorageSize());
        assertEquals(200, dto.getBodyStorageSize());
        assertEquals(300, dto.getAccessoryStorageSize());
        assertEquals(50, dto.getCarStorageSize());
        assertEquals(10, dto.getSoldCarsCounter());
        assertEquals(1, dto.getSuppMDelay());
        assertEquals(2, dto.getSuppBDelay());
        assertEquals(3, dto.getSuppsADelay());
        assertEquals(4, dto.getDealersDelay());
    }

    @Test
    void testToDto_ZeroValues() {
        FactoryState state = new FactoryState();
        state.setActiveSuppsMotors(false);
        state.setActiveSuppsBodies(false);
        state.setActiveSuppsAccessories(false);
        state.setActiveWorkers(false);
        state.setActiveRepoController(false);
        state.setActiveDealers(false);
        state.getActiveTasksCounter().set(0);
        state.getMotorStorageSize().set(0);
        state.getBodyStorageSize().set(0);
        state.getAccessoryStorageSize().set(0);
        state.getCarStorageSize().set(0);
        state.getSoldCarsCounter().set(0);
        state.getSuppMDelay().set(0);
        state.getSuppBDelay().set(0);
        state.getSuppsADelay().set(0);
        state.getDealersDelay().set(0);

        FactoryStateData dto = FactoryStateMapper.INSTANCE.toDto(state);

        assertFalse(dto.isActiveSuppsMotors());
        assertFalse(dto.isActiveSuppsBodies());
        assertFalse(dto.isActiveSuppsAccessories());
        assertFalse(dto.isActiveWorkers());
        assertFalse(dto.isActiveRepoController());
        assertFalse(dto.isActiveDealers());
        assertEquals(0, dto.getActiveTasksCounter());
        assertEquals(0, dto.getMotorStorageSize());
        assertEquals(0, dto.getBodyStorageSize());
        assertEquals(0, dto.getAccessoryStorageSize());
        assertEquals(0, dto.getCarStorageSize());
        assertEquals(0, dto.getSoldCarsCounter());
        assertEquals(0, dto.getSuppMDelay());
        assertEquals(0, dto.getSuppBDelay());
        assertEquals(0, dto.getSuppsADelay());
        assertEquals(0, dto.getDealersDelay());
    }
}