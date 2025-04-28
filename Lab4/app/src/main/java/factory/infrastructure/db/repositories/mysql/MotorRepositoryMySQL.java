package factory.infrastructure.db.repositories.mysql;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import factory.core.entities.parts.Motor;
import factory.core.repository.Repository;
import factory.infrastructure.db.entities.MotorData;
import factory.infrastructure.db.mapper.MotorMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@AllArgsConstructor
public class MotorRepositoryMySQL implements Repository<Motor> {
    private SessionFactory sessionFactory;
    @Getter
    private final long capacity;

    @Override
    public long size() {
        long size = 0;

        try (Session session = this.sessionFactory.openSession()) {
            Transaction transition = session.beginTransaction();

            try {
                size = session.createQuery("SELECT COUNT(*) FROM MotorData", Long.class).getSingleResult();
                transition.commit();
            } catch (Exception e) {
                transition.rollback();
                e.printStackTrace();
            }
        }

        return size;
    }

    @Override
    public void push(Motor motor) {
        // добавление данных
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
        // достаем данные
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