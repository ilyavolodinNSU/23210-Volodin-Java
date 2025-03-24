package factory.infrastructure.controllers;

import factory.core.services.BodyServices;

public class SupplierBody {
    private final BodyServices bodyService;

    public SupplierBody(BodyServices bodyService) {
        this.bodyService = bodyService;
    }

    // создает часть и пушит и репозиторий
    public void run() {
        bodyService.store(bodyService.supply());
    }
}
