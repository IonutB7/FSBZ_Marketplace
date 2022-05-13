package com.marketplace.fsbz_marketplace.model;

import java.util.ArrayList;

    public final class StoreInventoryHolder {

        private ArrayList<Item> storeInventory;
        private final static com.marketplace.fsbz_marketplace.model.StoreInventoryHolder INSTANCE = new com.marketplace.fsbz_marketplace.model.StoreInventoryHolder();

        private StoreInventoryHolder() {}

        public static com.marketplace.fsbz_marketplace.model.StoreInventoryHolder getInstance() {
            return INSTANCE;
        }

        public void setStoreInventory(ArrayList<Item> inventoryItens) {
            this.storeInventory = inventoryItens;
        }

        public ArrayList<Item> getStoreInventory() {
            return this.storeInventory;
        }
    }

