package com.marketplace.fsbz_marketplace.model;

import javafx.collections.ObservableList;

public final class SelectedItemsHolder {
    private ObservableList<Item> selectedItems;
    private final static com.marketplace.fsbz_marketplace.model.SelectedItemsHolder INSTANCE = new com.marketplace.fsbz_marketplace.model.SelectedItemsHolder();

    private SelectedItemsHolder() {
    }

    public static com.marketplace.fsbz_marketplace.model.SelectedItemsHolder getInstance() {
        return INSTANCE;
    }

    public void setSelectedItemsList(ObservableList<Item> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public ObservableList<Item> getSelectedItemsList() {
        return this.selectedItems;
    }
}


