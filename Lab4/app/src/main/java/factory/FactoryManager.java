package factory;

import factory.core.entities.parts.Motor;
import factory.core.services.AccessoryServices;
import factory.core.services.BodyServices;
import factory.core.services.CarServices;
import factory.core.services.MotorServices;
import factory.infrastructure.controllers.Dealer;
import factory.infrastructure.controllers.SupplierAccessory;
import factory.infrastructure.controllers.SupplierBody;
import factory.infrastructure.controllers.SupplierMotor;
import factory.infrastructure.controllers.Worker;
import factory.infrastructure.db.orm.HibernateUtil;
import factory.infrastructure.db.repositories.mysql.AccessoryRepositoryMySQL;
import factory.infrastructure.db.repositories.mysql.BodyRepositoryMySQL;
import factory.infrastructure.db.repositories.mysql.CarRepositoryMySQL;
import factory.infrastructure.db.repositories.mysql.MotorRepositoryMySQL;

public class FactoryManager {
    public static void main(String[] args) {
        var sessionFactory = HibernateUtil.getSessionFactory();

        MotorRepositoryMySQL motorRepMySQL = new MotorRepositoryMySQL(sessionFactory);
        BodyRepositoryMySQL bodyRepMySQL = new BodyRepositoryMySQL(sessionFactory);
        AccessoryRepositoryMySQL accessoryRepositoryMySQL = new AccessoryRepositoryMySQL(sessionFactory);
        CarRepositoryMySQL carRepMySQL = new CarRepositoryMySQL(sessionFactory);

        MotorServices motorServices = new MotorServices(motorRepMySQL);
        BodyServices bodyServices = new BodyServices(bodyRepMySQL);
        AccessoryServices accessoryServices = new AccessoryServices(accessoryRepositoryMySQL);
        CarServices carServices = new CarServices(carRepMySQL);

        SupplierMotor supplierMotor = new SupplierMotor(motorServices);
        SupplierBody supplierBody = new SupplierBody(bodyServices);
        SupplierAccessory supplierAccessory = new SupplierAccessory(accessoryServices);
        Dealer dealer = new Dealer(carServices);
        Worker worker = new Worker(motorServices, bodyServices, accessoryServices, carServices);

        supplierMotor.run();
        supplierBody.run();
        supplierAccessory.run();
        worker.run();
        dealer.run();
    }
}
