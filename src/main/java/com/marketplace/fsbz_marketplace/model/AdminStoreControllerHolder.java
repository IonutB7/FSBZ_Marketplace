package com.marketplace.fsbz_marketplace.model;

import com.marketplace.fsbz_marketplace.controllers.AdminStoreInventoryController;

public final class AdminStoreControllerHolder {

    private AdminStoreInventoryController myAAIC;
    private final static AdminStoreControllerHolder INSTANCE = new AdminStoreControllerHolder();

    private AdminStoreControllerHolder() {}

    public static AdminStoreControllerHolder getInstance() {
        return INSTANCE;
    }

    public void setAdminStoreInventoryController(AdminStoreInventoryController myAAIC) {
        this.myAAIC = myAAIC;
    }

    public AdminStoreInventoryController getAdminStoreInventoryController() {
        return this.myAAIC;
    }
}