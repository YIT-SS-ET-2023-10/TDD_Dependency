package Coupon;

import java.util.ArrayList;
import java.util.List;

public class User {
    String userID;
    List<Coupon> coupons = new ArrayList<Coupon>();

    public User(String userId) {
        userID = userId;
    }

    public void addCoupon(Coupon coupon){
        coupons.add(coupon);
    }

    public int getTotalCouponCount(){
        return coupons.size();
    }

    public Coupon getLastOccupiedCoupon() {
        return coupons.get(coupons.size()-1);
    }
}
