package factory.infrastructure.db.repositories.mysql;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import factory.core.entities.parts.Accessory;
import factory.core.repository.Repository;
import factory.infrastructure.db.entities.AccessoryData;
import factory.infrastructure.db.mapper.AccessoryMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
public class AccessoryRepositoryMySQL implements Repository<Accessory> {
    private SessionFactory sessionFactory;
    @Getter
    private final long capacity;

    @Override
    public long size() {
        long size = 0;

        try (Session session = this.sessionFactory.openSession()) {
            Transaction transition = session.beginTransaction();

            try {
                size = session.createQuery("SELECT COUNT(*) FROM AccessoryData", Long.class).getSingleResult();
                transition.commit();
            } catch (Exception e) {
                transition.rollback();
                e.printStackTrace();
            }
        }

        return size;
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