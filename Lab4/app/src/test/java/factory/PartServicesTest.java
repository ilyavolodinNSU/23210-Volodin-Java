package factory;

import factory.core.entities.parts.Motor;
import factory.core.repository.Repository;
import factory.core.services.PartServices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PartServicesTest {

    private PartServices<Motor> partServices;
    private StubRepository<Motor> repository;
    private List<Motor> storedParts;

    @BeforeEach
    void setUp() {
        storedParts = new ArrayList<>();
        repository = new StubRepository<>(storedParts);
        partServices = new PartServices<>(repository, Motor::new);
    }

    @Test
    void testSupply() {
        Motor motor = partServices.supply(42);
        assertNotNull(motor);
        assertEquals(42, motor.getId());
    }

    @Test
    void testStore() {
        Motor motor = new Motor(42);
        partServices.store(motor);
        assertEquals(1, storedParts.size());
        assertEquals(motor, storedParts.get(0));
    }

    @Test
    void testRetrieve() {
        Motor motor = new Motor(42);
        storedParts.add(motor);
        Motor retrieved = partServices.retrieve();
        assertEquals(motor, retrieved);
        assertTrue(storedParts.isEmpty());
    }

    @Test
    void testRetrieve_EmptyRepository() {
        Motor retrieved = partServices.retrieve();
        assertNull(retrieved);
    }

    private static class StubRepository<T> implements Repository<T> {
        private final List<T> storage;

        StubRepository(List<T> storage) {
            this.storage = storage;
        }

        @Override
        public void push(T entity) {
            storage.add(entity);
        }

        @Override
        public T pop() {
            return storage.isEmpty() ? null : storage.remove(storage.size() - 1);
        }

        @Override
        public long size() {
            return 0;
        }

        @Override
        public long getCapacity() {
            return 0;
        }
    }
}