package com.marketplace.fsbz_marketplace.model;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;

public class Item {


    private int itemNumber;
    private int inventoryId;
    private String name;
    private String description;
    private String category;
    private String wear;
    private float price;
    private int quantity;

    public Item() {
    }

    public Item(int itemNumber,int inventoryId, String nume, String description, String category, String wear, float price, int quantity) {
        this.itemNumber=itemNumber;
        this.inventoryId = inventoryId;
        this.name = nume;
        this.description = description;
        this.category = category;
        this.wear = wear;
        this.price = price;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "inventoryId=" + inventoryId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", wear='" + wear + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
