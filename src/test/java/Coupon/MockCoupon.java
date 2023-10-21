package Coupon;

import java.util.Random;

public class MockCoupon implements Coupon{
    int discountPercent;
    public String getName() {
        return "신규 가입 축하 쿠폰";
    }

    public boolean isValid() {
        return true;
    }

    public int getDiscountPercent() {
        if (discountPercent > 0)
            return discountPercent;
        Random discountGenerator = new Random();
        return discountGenerator.nextInt(20);
    }

    public boolean isAppliable(Item item) {
        if(item.getCategoryName().contains("크리스마스")) {
            setDiscountPercent(25);
            return true;
        }
        return false;
    }

    public void doExpire() {
    }

    private void setDiscountPercent(int discountPercent){
        this.discountPercent = discountPercent;
    }

}
