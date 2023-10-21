package Coupon;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class UserTest {
    @Test
    public void testAddCoupon() throws Exception{
        User user = new User("itsme2020");

        assertEquals(0, user.getTotalCouponCount());
        Coupon coupon = new DummyCoupon();
        user.addCoupon(coupon);

        assertEquals(1,user.getTotalCouponCount());
    }

    @Test
    public void testGetLastOccupiedCouponUsingStubCoupon() throws Exception{
        User user = new User("itsme2020");
        Coupon eventCoupon = new StubCoupon();
        user.addCoupon(eventCoupon);
        Coupon lastCoupon = user.getLastOccupiedCoupon();

        assertEquals(10,lastCoupon.getDiscountPercent());
        assertEquals("신규 가입 축하 쿠폰", lastCoupon.getName());

    }

    @Test
    public void testGetLastOccupiedCouponUsingMockCoupon() throws Exception{
        User user = new User("itsme2020");
        Coupon eventCoupon = new MockCoupon();
        PriceCalculator calculator = new PriceCalculator();

        user.addCoupon(eventCoupon);
        Coupon lastCoupon = user.getLastOccupiedCoupon();

        assertEquals("신규 가입 축하 쿠폰", lastCoupon.getName());
        assertTrue(lastCoupon.getDiscountPercent() <= 20);
        assertEquals(10,lastCoupon.getDiscountPercent());

    }

    @Test
    public void testGetOrderPriceDiscountableItemUsingStubCoupon() throws Exception{
        Item item = new Item("KakaoFrDiary","카카오 프렌즈 다이어리",15000);
        Coupon coupon = new StubCoupon();
        PriceCalculator calculator = new PriceCalculator();

        assertEquals(13500,calculator.getSimpleOrderPrice(item,coupon));
    }

    @Test
    public void testGetOrderPriceDiscountableItemUsingMockCoupon() throws Exception{
        Item itemDiary = new Item("KakaoFrDiary","카카오 프렌즈 다이어리",15000);
        Coupon coupon = new MockCoupon();
        PriceCalculator calculator = new PriceCalculator();

        itemDiary.setCategoryName("크리스마스");
        assertEquals(11250,calculator.getOrderPrice(itemDiary,coupon));
        itemDiary.setCategoryName("문구류");
        assertEquals(15000,calculator.getOrderPrice(itemDiary,coupon));
    }


    @Test
    public void testGetOrderPriceundiscountableItem() throws Exception{
        Item item = new Item("Watermelon10+","수박10kg이상",8000);
        Coupon coupon = new StubCoupon();
        PriceCalculator calculator = new PriceCalculator();

        assertEquals(8000,calculator.getSimpleOrderPrice(item,coupon));
    }

    @Test
    public void testGetOrderPriceUsingFakeCoupon() throws Exception{
        Coupon coupon = new FakeCoupon();
        PriceCalculator calculator = new PriceCalculator();

        Item itemLego = new Item("LegoWatchStarwars","레고 손목시계 스타워즈",25000);
        itemLego.setCategoryName("장난감");
        assertEquals(23750,calculator.getOrderPrice(itemLego,coupon));

        Item itemIlly = new Item("illyCoffMach2020Ed","일리 커피머신 2020 에디션",120000);
        itemIlly.setCategoryName("주방가전");
        assertEquals(120000,calculator.getOrderPrice(itemIlly,coupon));

    }

    @Test
    public void testGetOrderPriceUsingSpyCoupon() throws Exception{
        Coupon coupon = new SpyCoupon();
        PriceCalculator calculator = new PriceCalculator();

        Item itemLego = new Item("LegoWatchStarwars","레고 손목시계 스타워즈",25000);
        itemLego.setCategoryName("장난감");
        assertEquals(23750,calculator.getOrderPrice(itemLego,coupon));
        assertEquals(1,((SpyCoupon)coupon).getIsAppliableCallCount());

        Item itemIlly = new Item("illyCoffMach2020Ed","일리 커피머신 2020 에디션",120000);
        itemIlly.setCategoryName("주방가전");
        assertEquals(120000,calculator.getOrderPrice(itemIlly,coupon));
        assertEquals(2,((SpyCoupon)coupon).getIsAppliableCallCount());
    }

    @Mock
    Coupon mockCoupon;
    @Test
    public void testGetOrderPriceUsingMockito() throws Exception {
        PriceCalculator calculator = new PriceCalculator();
        Item itemLego = new Item("LegoWatchStarwars", "레고 손목시계 스타워즈", 25000);
        itemLego.setCategoryName("장난감");
        Item itemIlly = new Item("illyCoffMach2020Ed", "일리 커피머신 2020 에디션", 120000);
        itemIlly.setCategoryName("주방가전");

        MockitoAnnotations.openMocks(this);
        Mockito.when(mockCoupon.isAppliable(itemLego)).thenReturn(true);
        Mockito.when(mockCoupon.getDiscountPercent()).thenReturn(20);
        Mockito.when(mockCoupon.isValid()).thenReturn(true);

        assertEquals(20000,calculator.getOrderPrice(itemLego,mockCoupon));

        verify(mockCoupon, times(1)).isAppliable(itemLego);
        verify(mockCoupon, times(1)).getDiscountPercent();
        verify(mockCoupon, times(1)).isValid();
    }
}
