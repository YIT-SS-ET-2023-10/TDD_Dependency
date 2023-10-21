package Coupon;

import Coupon.Coupon;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FakeCoupon implements Coupon {
    List<String> categoryList = new ArrayList();
    String couponName;
    LocalDate startDate, endDate;
    int discountPercent;

    public FakeCoupon() {
        addCategories();
        initCoupon();
    }

    private void addCategories(){
        categoryList.add("소형가전");
        categoryList.add("도서");
        categoryList.add("장난감");
    }

    private void initCoupon(){
        couponName = "여름 휴가 쿠폰";
        discountPercent = 5;
        startDate = LocalDate.of(2020,7,1);
        endDate = LocalDate.of(2020,8,31);
    }

    public String getName() {
        return couponName;
    }

    public boolean isValid() {
        LocalDate today = LocalDate.now();
        if (today.isAfter(startDate) && today.isBefore(endDate)) {
            return true;
        }
        return false;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public boolean isAppliable(Item item) {
        if (categoryList.contains(item.getCategoryName())) return true;
        return false;
    }

    public void doExpire() {
    }
}
