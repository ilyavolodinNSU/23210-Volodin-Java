package factory.infrastructure.db.repositories.mysql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import factory.core.entities.Car;
import factory.core.repository.Repository;
import factory.infrastructure.db.entities.CarData;
import factory.infrastructure.db.mapper.CarMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
public class CarRepositoryMySQL implements Repository<Car> {
    private SessionFactory sessionFactory;
    @Getter
    private final long capacity;

    @Override
    public long size() {
        long size = 0;

        try (Session session = this.sessionFactory.openSession()) {
            Transaction transition = session.beginTransaction();

            try {
                size = session.createQuery("SELECT COUNT(*) FROM CarData", Long.class).getSingleResult();
                transition.commit();
            } catch (Exception e) {
                transition.rollback();
                e.printStackTrace();
            }
        }

        return size;
    }

    @Override
    public void push(Car Car) {
        CarData CarDto = CarMapper.INSTANCE.toDto(Car);

        try (Session session = this.sessionFactory.openSession()) {
            Transaction transition = session.beginTransaction();

            try {
                session.persist(CarDto);
                transition.commit();
            } catch (Exception e) {
                transition.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public Car pop() {
        Car Car = null;

        try (Session session = this.sessionFactory.openSession()) {
            Transaction transition = session.beginTransaction();

            try {
                var CarDto = session.createQuery("FROM CarData", CarData.class).setMaxResults(1).uniqueResult();
                session.remove(CarDto);

                transition.commit();

                Car = CarMapper.INSTANCE.toEntity(CarDto);
            } catch (Exception e) {
                transition.rollback();
                e.printStackTrace();
            }
        }

        return Car;
    }
}