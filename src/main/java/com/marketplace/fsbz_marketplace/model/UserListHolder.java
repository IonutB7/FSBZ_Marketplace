package com.marketplace.fsbz_marketplace.model;

import java.util.ArrayList;

public final class UserListHolder {

    private ArrayList<User> userList;

    private int lastUserId;
    private final static com.marketplace.fsbz_marketplace.model.UserListHolder INSTANCE = new com.marketplace.fsbz_marketplace.model.UserListHolder();

    private UserListHolder() {}

    public static com.marketplace.fsbz_marketplace.model.UserListHolder getInstance() {
        return INSTANCE;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public ArrayList<User>  getUserList() {
        return this.userList;
    }

    public int getLastUserId() {
        return lastUserId;
    }

    public void setLastUserId(int lastUserId) {
        this.lastUserId = lastUserId;
    }
}
