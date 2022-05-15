package com.marketplace.fsbz_marketplace.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.collections.ObservableList;

public final class SelectedItemsHolder {
    private ObservableList<Item> selectedItemsUserInventory;
    private ObservableList<Item> selectedItemsStoreInventory;
    private SimpleFloatProperty totalValueUserItems = new SimpleFloatProperty(this, "totalValueUserItems");
    private SimpleFloatProperty totalValueStoreItems = new SimpleFloatProperty(this, "totalValueStoreItems");
    private final static com.marketplace.fsbz_marketplace.model.SelectedItemsHolder INSTANCE = new com.marketplace.fsbz_marketplace.model.SelectedItemsHolder();

    private SelectedItemsHolder() {
    }

    public static com.marketplace.fsbz_marketplace.model.SelectedItemsHolder getInstance() {
        return INSTANCE;
    }

    public ObservableList<Item> getSelectedItemsUserInventory() {
        return selectedItemsUserInventory;
    }

    public void setSelectedItemsUserInventory(ObservableList<Item> selectedItemsUserInventory) {
        this.selectedItemsUserInventory = selectedItemsUserInventory;
    }

    public ObservableList<Item> getSelectedItemsStoreInventory() {
        return selectedItemsStoreInventory;
    }

    public void setSelectedItemsStoreInventory(ObservableList<Item> selectedItemsStoreInventory) {
        this.selectedItemsStoreInventory = selectedItemsStoreInventory;
    }

    public float getTotalValueUserItems() {
        return totalValueUserItems.get();
    }

    public SimpleFloatProperty totalValueUserItemsProperty() {
        return totalValueUserItems;
    }

    public void setTotalValueUserItems(float totalValueUserItems) {
        this.totalValueUserItems.set(totalValueUserItems);
    }

    public float getTotalValueStoreItems() {
        return totalValueStoreItems.get();
    }

    public SimpleFloatProperty totalValueStoreItemsProperty() {
        return totalValueStoreItems;
    }

    public void setTotalValueStoreItems(float totalValueStoreItems) {
        this.totalValueStoreItems.set(totalValueStoreItems);
    }
}


