package factory;

import factory.core.entities.Car;
import factory.core.entities.parts.Accessory;
import factory.core.entities.parts.Body;
import factory.core.entities.parts.Motor;
import factory.core.repository.Repository;
import factory.core.services.CarServices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CarServicesTest {

    private CarServices carServices;
    private StubRepository<Car> repository;
    private List<Car> storedCars;

    @BeforeEach
    void setUp() {
        storedCars = new ArrayList<>();
        repository = new StubRepository<>(storedCars);
        carServices = new CarServices(repository);
    }

    @Test
    void testAssembleCar() {
        Motor motor = new Motor(1);
        Body body = new Body(2);
        Accessory accessory = new Accessory(3);
        Car car = carServices.assembleCar(motor, body, accessory, 42);

        assertNotNull(car);
        assertEquals(motor, car.getMotor());
        assertEquals(body, car.getBody());
        assertEquals(accessory, car.getAccessory());
        assertEquals(42, car.getId());
    }

    @Test
    void testStore() {
        Car car = new Car(new Body(1), new Motor(2), new Accessory(3), 42);
        carServices.store(car);
        assertEquals(1, storedCars.size());
        assertEquals(car, storedCars.get(0));
    }

    @Test
    void testRetrieve() {
        Car car = new Car(new Body(1), new Motor(2), new Accessory(3), 42);
        storedCars.add(car);
        Car retrieved = carServices.retrieve();
        assertEquals(car, retrieved);
        assertTrue(storedCars.isEmpty());
    }

    @Test
    void testRetrieve_EmptyRepository() {
        Car retrieved = carServices.retrieve();
        assertNull(retrieved);
    }

    @Test
    void testSaleCar() {
        Car car = new Car(new Body(1), new Motor(2), new Accessory(3), 42);
        carServices.saleCar(car);
        assertNull(car.getBody());
        assertNull(car.getMotor());
        assertNull(car.getAccessory());
        assertEquals(0, car.getId());
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