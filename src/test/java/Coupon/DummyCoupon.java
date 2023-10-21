package Coupon;

import Coupon.Coupon;

public class DummyCoupon implements Coupon {
    public String getName() {
//        return null;
        throw new UnsupportedOperationException("아직 정의되지 않았음.");
    }

    public boolean isValid()  {
        return false;
    }

    public int getDiscountPercent() {
        return 0;
    }

    public boolean isAppliable(Item item) {
        return false;
    }

    public void doExpire() { }
}
