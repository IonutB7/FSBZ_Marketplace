package com.marketplace.fsbz_marketplace.model;

public class Admin {
    private int adminId;
    private String email;
    private String username;

    public Admin(){

    }

    public Admin(int adminId, String email, String username) {
        this.adminId = adminId;
        this.email = email;
        this.username = username;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
