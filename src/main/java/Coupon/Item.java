package Coupon;

public class Item {
    String categoryName;
    String productCode;
    String productName;
    int productPrice;

    public Item(String code, String name, int price) {
        productCode = code;
        productName = name;
        productPrice = price;
    }

    public int getPrice() {
        return productPrice;
    }

    public void setCategoryName(String name){
        categoryName = name;
    }

    public String getCategoryName(){
        return categoryName;
    }
}
