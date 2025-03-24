package factory.infrastructure.controllers;

import factory.core.services.AccessoryServices;

public class SupplierAccessory {
    private final AccessoryServices accessoryService;

    public SupplierAccessory(AccessoryServices accessoryService) {
        this.accessoryService = accessoryService;
    }

    // создает часть и пушит и репозиторий
    public void run() {
        accessoryService.store(accessoryService.supply());
    }
}
