package com.marketplace.fsbz_marketplace.model;

public class Coupon {
    private String code;
    boolean valid;
    int value_id;


    public Coupon(){

    }

    public float getCouponValue(){

        float value;

        switch (this.value_id){
            case(1):
                value=500;
                break;
            case(2):
                value=1000;
                break;
            case(3):
                value=2000;
                break;
            case(4):
                value=5000;
                break;
            case(5):
                value=10000;
                break;
            default:
                value=0;
                break;
        }

        return value;

    }

    public Coupon(String code, boolean valid, int value_id) {
        this.code = code;
        this.valid = valid;
        this.value_id = value_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public int getValue_id() {
        return value_id;
    }

    public void setValue_id(int value_id) {
        this.value_id = value_id;
    }
}
