package factory.infrastructure.db.repositories.mysql;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import factory.core.entities.parts.Body;
import factory.core.repository.Repository;
import factory.infrastructure.db.entities.BodyData;
import factory.infrastructure.db.mapper.BodyMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
public class BodyRepositoryMySQL implements Repository<Body> {
    private SessionFactory sessionFactory;
    @Getter
    private final long capacity;

    @Override
    public long size() {
        long size = 0;

        try (Session session = this.sessionFactory.openSession()) {
            Transaction transition = session.beginTransaction();

            try {
                size = session.createQuery("SELECT COUNT(*) FROM BodyData", Long.class).getSingleResult();
                transition.commit();
            } catch (Exception e) {
                transition.rollback();
                e.printStackTrace();
            }
        }

        return size;
    }

    @Override
    public synchronized void push(Body Body) {
        BodyData BodyDto = BodyMapper.INSTANCE.toDto(Body);

        try (Session session = this.sessionFactory.openSession()) {
            Transaction transition = session.beginTransaction();

            try {
                session.persist(BodyDto);
                transition.commit();
            } catch (Exception e) {
                transition.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized Body pop() {
        Body Body = null;

        try (Session session = this.sessionFactory.openSession()) {
            Transaction transition = session.beginTransaction();

            try {
                var BodyDto = session.createQuery("FROM BodyData", BodyData.class).setMaxResults(1).uniqueResult();
                session.remove(BodyDto);

                transition.commit();

                Body = BodyMapper.INSTANCE.toEntity(BodyDto);
            } catch (Exception e) {
                transition.rollback();
                e.printStackTrace();
            }
        }

        return Body;
    }
}