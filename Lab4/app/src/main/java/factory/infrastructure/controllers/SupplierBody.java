package factory.infrastructure.controllers;

import factory.core.services.BodyServices;

public class SupplierBody implements Runnable {
    private final BodyServices bodyService;

    public SupplierBody(BodyServices bodyService) {
        this.bodyService = bodyService;
    }

    // создает часть и пушит и репозиторий

    @Override
    public void run() {
        bodyService.store(bodyService.supply());
    }
}
