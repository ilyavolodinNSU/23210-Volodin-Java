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
    public synchronized long getSize() {
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
    public synchronized void push(Motor motor) {
        if (getSize() >= 3) {
            try {
                System.err.println("Склад моторов переполнен");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

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

        // запускаем потоки, которые выполняли pop()
        notify();
    }

    @Override
    public synchronized Motor pop() {
        if (getSize() <= 0) {
            try {
                System.err.println("Склад моторов пуст");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

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

        // воскрешаем потоки, которые выполняли push()
        if (motor != null) notify();

        return motor;
    }
}