package factory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import factory.infrastructure.db.entities.AccessoryData;
import factory.infrastructure.db.entities.CarData;
import factory.infrastructure.db.orm.HibernateUtil;

public class FactoryManager {
    public static void main(String[] args) {
        
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession(); 

        CarData carData = session.get(CarData.class, 0);
        System.out.println("Найден аксессуар: ID = " + carData.getId());
    }
}
