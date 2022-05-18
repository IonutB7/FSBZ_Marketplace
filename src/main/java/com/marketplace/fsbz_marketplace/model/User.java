package com.marketplace.fsbz_marketplace.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

import java.util.*;

public class User {
    private int acountId;
    private int inventoryId;
    private String email;
    private String username;

    private boolean warned;

    private boolean banned;

    private SimpleFloatProperty balance = new SimpleFloatProperty(this, "balance");
    private ArrayList<Item> userInventory;

    private ArrayList<Transaction> userTransationList;

    public User() {
    }

    public int getAcountId() {
        return acountId;
    }

    public void setAcountId(int acountId) {
        this.acountId = acountId;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
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

    public float getBalance() {
        return balance.get();
    }

    public void setBalance(float balance) {
        this.balance.set(balance);
    }

    public FloatProperty balanceProperty(){
        return balance;
    }

    public ArrayList<Item> getUserInventory() {
        return userInventory;
    }

    public void setUserInventory(ArrayList<Item> userInventory) {
        this.userInventory = userInventory;
    }

    public ArrayList<Transaction> getUserTransationList() {
        return userTransationList;
    }

    public void setUserTransationList(ArrayList<Transaction> userTransationList) {
        this.userTransationList = userTransationList;
    }

    public boolean isWarned() {
        return warned;
    }

    public void setWarned(boolean warned) {
        this.warned = warned;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    @Override
    public String toString() {
        return "User{" +
                "acountId=" + acountId +
                ", inventoryId=" + inventoryId +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", warned=" + warned +
                ", banned=" + banned +
                ", balance=" + balance +
                ", userInventory=" + userInventory +
                ", userTransationList=" + userTransationList +
                '}';
    }
}
