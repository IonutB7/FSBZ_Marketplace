package com.marketplace.fsbz_marketplace.model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public final class WeaponsTableHolder {

    private ArrayList<WeaponInformation> weaponsTable;

    private final static com.marketplace.fsbz_marketplace.model.WeaponsTableHolder INSTANCE = new com.marketplace.fsbz_marketplace.model.WeaponsTableHolder();

    private WeaponsTableHolder() {
    }

    public static com.marketplace.fsbz_marketplace.model.WeaponsTableHolder getInstance() {
        return INSTANCE;
    }


    public ArrayList<WeaponInformation> getWeaponInformations() {
        return weaponsTable;
    }

    public void setWeaponInformations(ArrayList<WeaponInformation> weaponInformations) {
        this.weaponsTable = weaponInformations;
    }


}


