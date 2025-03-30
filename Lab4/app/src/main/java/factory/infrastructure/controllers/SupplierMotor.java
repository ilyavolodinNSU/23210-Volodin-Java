package factory.infrastructure.controllers;

import factory.core.services.MotorServices;

public class SupplierMotor implements Runnable {
    private final MotorServices motorService;

    public SupplierMotor(MotorServices motorService) {
        this.motorService = motorService;
    }

    // создает часть и пушит и репозиторий

    @Override
    public void run() {
        motorService.store(motorService.supply());
    }
}
