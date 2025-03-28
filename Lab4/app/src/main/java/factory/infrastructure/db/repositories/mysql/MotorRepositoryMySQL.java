package factory.infrastructure.db.repositories.mysql;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import factory.core.entities.parts.Motor;
import factory.core.repository.IMotorRepository;
import factory.infrastructure.db.entities.MotorData;
import factory.infrastructure.db.mapper.MotorMapper;

public class MotorRepositoryMySQL implements IMotorRepository {
    private SessionFactory sessionFactory;

    public MotorRepositoryMySQL(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void push(Motor motor) {
        MotorData motorDto = MotorMapper.INSTANCE.toDto(motor);

        try (Session session = this.sessionFactory.openSession()) {
            Transaction transition = session.beginTransaction();

            try {
                session.persist(motorDto);
                transition.commit();
            } catch (Exception e) {
                transition.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public Motor pop() {
        Motor motor = null;

        try (Session session = this.sessionFactory.openSession()) {
            Transaction transition = session.beginTransaction();

            try {
                var motorDto = session.createQuery("FROM MotorData", MotorData.class).setMaxResults(1).uniqueResult();
                session.remove(motorDto);

                transition.commit();

                motor = MotorMapper.INSTANCE.toEntity(motorDto);
            } catch (Exception e) {
                transition.rollback();
                e.printStackTrace();
            }
        }

        return motor;
    }
}