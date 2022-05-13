package com.marketplace.fsbz_marketplace.services;

import com.marketplace.fsbz_marketplace.db.DatabaseConnection;
import com.marketplace.fsbz_marketplace.exceptions.InsufficientAmountException;
import com.marketplace.fsbz_marketplace.exceptions.InvalidCouponException;
import com.marketplace.fsbz_marketplace.exceptions.WalletExceptions;
import com.marketplace.fsbz_marketplace.model.Coupon;
import com.marketplace.fsbz_marketplace.model.StoreCouponHolder;
import com.marketplace.fsbz_marketplace.model.User;
import com.marketplace.fsbz_marketplace.model.UserHolder;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class WalletServices {


    public static void updateCouponDB(String couponCode){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();

        String updateCouponValidation = "UPDATE coupon_db SET valid=0 WHERE code = '"+couponCode+"';";

        try{

            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(updateCouponValidation);
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public static void withdrawAmount(float amount) throws WalletExceptions{
        if(amount<=UserHolder.getInstance().getUser().getBalance()) {
            float newBalanceValue = UserHolder.getInstance().getUser().getBalance()-amount;
            int currentUserAccountId = UserHolder.getInstance().getUser().getAcountId();
            UserHolder.getInstance().getUser().setBalance(newBalanceValue);
            UserServices.updateUserBalance(newBalanceValue,currentUserAccountId);
        }
        else{
            throw new InsufficientAmountException("Insufficient funds for withdraw.");
        }
    }

    public static void verifyCoupon(String userCode) throws WalletExceptions {
        ArrayList<Coupon> storeCoupons = StoreCouponHolder.getInstance().getStoreCouponList();
        for(int i=0;i<storeCoupons.size();i++){
            if(storeCoupons.get(i).getCode().equals(userCode)==true){
                if(storeCoupons.get(i).isValid()==true){
                    StoreCouponHolder.getInstance().getStoreCouponList().get(i).setValid(false);
                    updateCouponDB(userCode);
                    float newBalanceValue = storeCoupons.get(i).getCouponValue() + UserHolder.getInstance().getUser().getBalance();
                    int currentUserAccountId = UserHolder.getInstance().getUser().getAcountId();
                    UserHolder.getInstance().getUser().setBalance(newBalanceValue);
                    UserServices.updateUserBalance(newBalanceValue,currentUserAccountId);
                    return;
                }else{
                    throw new InvalidCouponException("This coupon is used!");
                }
            }
        }

        throw new InvalidCouponException("Coupon doesn't exist!");
    }

    public static void loadStoreCoupons(ArrayList<Coupon> retrivedStoreItems) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();

        String retriveStoreItems = "SELECT * FROM coupon_db";

        try{

            Statement statement = connectionDB.createStatement();
            ResultSet queryResult = statement.executeQuery(retriveStoreItems);

            while (queryResult.next()) {

                Coupon tempCoupon = new Coupon();

                tempCoupon.setCode(queryResult.getString("code"));
                tempCoupon.setValid(queryResult.getBoolean("valid"));
                tempCoupon.setValue_id(queryResult.getInt("value_id"));

                retrivedStoreItems.add(tempCoupon);
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }
}
