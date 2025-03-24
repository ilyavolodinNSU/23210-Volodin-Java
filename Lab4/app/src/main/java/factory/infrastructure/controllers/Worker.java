package factory.infrastructure.controllers;

import factory.core.services.AccessoryServices;
import factory.core.services.BodyServices;
import factory.core.services.CarServices;
import factory.core.services.MotorServices;

public class Worker {
    private final MotorServices motorService;
    private final BodyServices bodyServices;
    private final AccessoryServices accessoryServices;
    private final CarServices carServices;

    public Worker(MotorServices motorService, BodyServices bodyServices, AccessoryServices accessoryServices, CarServices carServices) {
        this.motorService = motorService;
        this.bodyServices = bodyServices;
        this.accessoryServices = accessoryServices;
        this.carServices = carServices;
    }

    // берет части из репозиториев, собирает машину, пушит машину в репозиторий
    public void run() {
        carServices.store(
            carServices.assembleCar(motorService.retrieve(),
                                    bodyServices.retrieve(),
                                    accessoryServices.retrieve())
        );
    }
}
