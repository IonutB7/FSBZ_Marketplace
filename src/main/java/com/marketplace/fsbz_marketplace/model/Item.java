package com.marketplace.fsbz_marketplace.model;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class Item {


    private int itemNumber;
    private int inventoryId;
    private String name;
    private String description;
    private String category;
    private String wear;
    private float price;

    private boolean statTrack;


    public Item() {
    }

    public Item(int itemNumber, int inventoryId, String name, String description, String category, String wear, float price, boolean statTrack){
        this.inventoryId = inventoryId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.wear = wear;
        this.price = price;
        this.statTrack = statTrack;
    }

    public int getItemNumber() {
        return itemNumber;
    }
    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getWear() {
        return wear;
    }

    public void setWear(String wear) {
        this.wear = wear;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isStatTrack() {
        return statTrack;
    }

    public void setStatTrack(boolean statTrack) {
        this.statTrack = statTrack;
    }


    @Override
    public String toString() {
        return "Item{" +
                "itemNumber=" + itemNumber +
                ", inventoryId=" + inventoryId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", wear='" + wear + '\'' +
                ", price=" + price +
                ", statTrak=" + statTrack +
                '}';
    }
}
