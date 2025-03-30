package factory.infrastructure.db.repositories.mysql;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import factory.core.entities.parts.Accessory;
import factory.core.repository.IAccessoryRepository;
import factory.infrastructure.db.entities.AccessoryData;
import factory.infrastructure.db.mapper.AccessoryMapper;

public class AccessoryRepositoryMySQL implements IAccessoryRepository {
    private SessionFactory sessionFactory;

    public AccessoryRepositoryMySQL(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public synchronized void push(Accessory Accessory) {
        AccessoryData AccessoryDto = AccessoryMapper.INSTANCE.toDto(Accessory);

        try (Session session = this.sessionFactory.openSession()) {
            Transaction transition = session.beginTransaction();

            try {
                session.persist(AccessoryDto);
                transition.commit();
            } catch (Exception e) {
                transition.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized Accessory pop() {
        Accessory Accessory = null;

        try (Session session = this.sessionFactory.openSession()) {
            Transaction transition = session.beginTransaction();

            try {
                var AccessoryDto = session.createQuery("FROM AccessoryData", AccessoryData.class).setMaxResults(1).uniqueResult();
                session.remove(AccessoryDto);

                transition.commit();

                Accessory = AccessoryMapper.INSTANCE.toEntity(AccessoryDto);
            } catch (Exception e) {
                transition.rollback();
                e.printStackTrace();
            }
        }

        return Accessory;
    }
}