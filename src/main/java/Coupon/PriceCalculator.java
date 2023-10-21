package Coupon;

public class PriceCalculator {
    public int getSimpleOrderPrice(Item item, Coupon coupon) {
        return (int) (item.getPrice()*(1 -(float)(coupon.getDiscountPercent()/100.0)));
    }

    public int getOrderPrice(Item item, Coupon coupon) {

        if(coupon.isValid() && coupon.isAppliable(item)){
            return (int) (item.getPrice()*(1 -(float)(coupon.getDiscountPercent()/100.0)));
        }
        return item.getPrice();
    }
}
