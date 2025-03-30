package factory.infrastructure.db.repositories.mysql;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import factory.core.entities.Car;
import factory.core.repository.ICarRepository;
import factory.infrastructure.db.entities.CarData;
import factory.infrastructure.db.mapper.CarMapper;

public class CarRepositoryMySQL implements ICarRepository {
    private SessionFactory sessionFactory;

    public CarRepositoryMySQL(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public synchronized void push(Car Car) {
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
    public synchronized Car pop() {
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