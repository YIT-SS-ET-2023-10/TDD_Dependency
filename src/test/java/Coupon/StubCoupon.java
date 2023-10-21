package Coupon;

import Coupon.Coupon;

public class StubCoupon implements Coupon {
    public String getName() {
        return "신규 가입 축하 쿠폰";
    }

    public boolean isValid() {
        return true;
    }

    public int getDiscountPercent() {
        return 10;
    }

    public boolean isAppliable(Item item) {
        return true;
    }

    public void doExpire() {
    }
}
