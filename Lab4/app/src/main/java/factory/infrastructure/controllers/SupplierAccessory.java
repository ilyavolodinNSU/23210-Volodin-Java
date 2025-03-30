package factory.infrastructure.controllers;

import factory.core.services.AccessoryServices;

public class SupplierAccessory implements Runnable {
    private final AccessoryServices accessoryService;

    public SupplierAccessory(AccessoryServices accessoryService) {
        this.accessoryService = accessoryService;
    }

    // создает часть и пушит и репозиторий

    @Override
    public void run() {
        accessoryService.store(accessoryService.supply());
    }
}
