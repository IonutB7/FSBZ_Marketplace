package com.marketplace.fsbz_marketplace.model;

public final class AdminHolder {

    private Admin admin;
    private final static AdminHolder INSTANCE = new AdminHolder();

    private AdminHolder() {}

    public static AdminHolder getInstance() {
        return INSTANCE;
    }

    public void setAdmin(Admin a) {
        this.admin = a;
    }

    public Admin getAdmin() {
        return this.admin;
    }
}
