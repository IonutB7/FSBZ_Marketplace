package com.marketplace.fsbz_marketplace.model;

import java.util.ArrayList;

    public final class StoreCouponHolder {

        private ArrayList<Coupon> storeCoupons;
        private final static com.marketplace.fsbz_marketplace.model.StoreCouponHolder INSTANCE = new com.marketplace.fsbz_marketplace.model.StoreCouponHolder();

        private StoreCouponHolder() {
        }

        public static com.marketplace.fsbz_marketplace.model.StoreCouponHolder getInstance() {
            return INSTANCE;
        }

        public void setStoreCouponList(ArrayList<Coupon> storeCoupons) {
            this.storeCoupons = storeCoupons;
        }

        public ArrayList<Coupon> getStoreCouponList() {
            return this.storeCoupons;
        }
    }