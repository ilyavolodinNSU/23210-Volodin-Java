package factory;

import factory.core.repository.IAccessoryRepository;
import factory.core.repository.IBodyRepository;
import factory.core.repository.ICarRepository;
import factory.core.repository.IMotorRepository;
import factory.core.services.AccessoryServices;
import factory.core.services.BodyServices;
import factory.core.services.CarServices;
import factory.core.services.MotorServices;
import factory.infrastructure.controllers.Dealer;
import factory.infrastructure.controllers.SupplierAccessory;
import factory.infrastructure.controllers.SupplierBody;
import factory.infrastructure.controllers.SupplierMotor;
import factory.infrastructure.controllers.Worker;
import factory.infrastructure.db.repositories.AccessoryRepositoryStorage;
import factory.infrastructure.db.repositories.BodyRepositoryStorage;
import factory.infrastructure.db.repositories.CarRepositoryStorage;
import factory.infrastructure.db.repositories.MotorRepositoryStorage;

public class FactoryManager {
    //private IMotorRepository rep = new MotorRepositorySQL(), MotorRepositoryMongoDB(), etc...

    // repositories
    private IMotorRepository motorRep = new MotorRepositoryStorage();
    private IBodyRepository bodyRep = new BodyRepositoryStorage();
    private IAccessoryRepository accessoryRep = new AccessoryRepositoryStorage();
    private ICarRepository carRep = new CarRepositoryStorage();

    // services
    CarServices cs = new CarServices(carRep);
    MotorServices ms = new MotorServices(motorRep);
    BodyServices bs = new BodyServices(bodyRep);
    AccessoryServices as = new AccessoryServices(accessoryRep);

    // controllers
    private SupplierMotor sm = new SupplierMotor(ms);
    private SupplierBody sb = new SupplierBody(bs);
    private SupplierAccessory sa = new SupplierAccessory(as);
    private Worker w = new Worker(ms, bs, as, cs);
    private Dealer d = new Dealer(cs);



}
