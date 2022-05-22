package com.marketplace.fsbz_marketplace.model;


import java.sql.Date;
import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private int inventoryId;
    private String userOffer;
    private String storeOffer;
    private float totalPrice;
    private String transactionType;
    private Timestamp transactionDate;
    private String status;

    public Transaction(){

    }
    public Transaction(int transactionId, int inventoryId, String userOffer, String storeOffer, float totalPrice, String transactionType, Timestamp transactionDate, String status) {
        this.transactionId = transactionId;
        this.inventoryId = inventoryId;
        this.userOffer = userOffer;
        this.storeOffer = storeOffer;
        this.totalPrice = totalPrice;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.status = status;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getUserOffer() {
        return userOffer;
    }

    public void setUserOffer(String userOffer) {
        this.userOffer = userOffer;
    }

    public String getStoreOffer() {
        return storeOffer;
    }

    public void setStoreOffer(String storeOffer) {
        this.storeOffer = storeOffer;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transaction_id=" + transactionId +
                ", inventory_id=" + inventoryId +
                ", userItems='" + userOffer + '\'' +
                ", storeItems='" + storeOffer + '\'' +
                ", totalPrice=" + totalPrice +
                ", transactionType='" + transactionType + '\'' +
                ", transactionDate=" + transactionDate +
                ", status='" + status + '\'' +
                '}';
    }
}
