package Coupon;

import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.leq;
import static org.mockito.Mockito.*;

public class MockitoMockTest {

    @Test
    void createAndStubMock(){
        Coupon couponMock = mock(Coupon.class);

        when(couponMock.getName()).thenReturn("mocked result");
        assertAll("Mock Test",
                ()-> assertEquals("mocked result", couponMock.getName()),
                ()-> assertEquals(0, couponMock.getDiscountPercent())
        );

        when(couponMock.getDiscountPercent()).thenReturn(20);
        doReturn(10).when(couponMock).getDiscountPercent();
        assertEquals(10, couponMock.getDiscountPercent());

        when(couponMock.getDiscountPercent())
                .thenReturn(5)
                .thenReturn(15)
                .thenReturn(10);

        assertEquals(5, couponMock.getDiscountPercent());
        assertEquals(15, couponMock.getDiscountPercent());
        assertEquals(10, couponMock.getDiscountPercent());
    }

    @Test
    void createAndStubSpy(){
        Coupon couponMock = spy(Coupon.class);
        Item itemMock = mock(Item.class);

        assertEquals(null, couponMock.getName());
        assertEquals(0, couponMock.getDiscountPercent());
        assertFalse(couponMock.isValid());
        assertFalse(couponMock.isAppliable(itemMock));

        itemMock.setCategoryName("Item Mock");
        itemMock.productName = "레고 스타워즈 I";
        itemMock.productPrice = 25000;
        assertEquals("Item Mock", itemMock.getCategoryName());
        assertEquals(25000, itemMock.getPrice());
        assertEquals("레고 스타워즈 I", itemMock.productName);

        when(itemMock.getCategoryName()).thenReturn("Item Mock");
        assertEquals("Item Mock", itemMock.getCategoryName());

        Item itemSpy = spy(new Item("LegoStarwars1","레고 스타워즈 I",25000));
        itemSpy.setCategoryName("Spy Item");
        assertEquals("Spy Item", itemSpy.getCategoryName());
        assertEquals(25000, itemSpy.getPrice());
        assertEquals("레고 스타워즈 I", itemSpy.productName);

        when(itemSpy.getPrice()).thenReturn(23000);
        assertEquals(23000, itemSpy.getPrice());
        doReturn(27000).when(itemSpy).getPrice();
        doReturn("New Spy Item").when(itemSpy).getCategoryName();
        assertEquals("New Spy Item", itemSpy.getCategoryName());
        assertEquals(27000, itemSpy.getPrice());

    }

    @Mock
    Coupon thanksgivingCoupon;
    @Mock
    Coupon newyearCoupon;
    @InjectMocks
    PriceCalculator priceCalculator;

    @Test
    void mockAnotationTest(){
        MockitoAnnotations.openMocks(this);

        when(thanksgivingCoupon.getName()).thenReturn("추석맞이 쿠폰");
        assertEquals("추석맞이 쿠폰", thanksgivingCoupon.getName());

        newyearCoupon.doExpire();
        Item itemMock = mock(Item.class);
        priceCalculator = new PriceCalculator();
        priceCalculator.getOrderPrice(itemMock, newyearCoupon);

        verify(itemMock, atLeastOnce()).getPrice();
        verify(newyearCoupon, times(1)).isValid();
        verify(newyearCoupon, atLeastOnce()).doExpire();
        verify(newyearCoupon, never()).getDiscountPercent();

        verify(thanksgivingCoupon, never()).doExpire();
        verify(thanksgivingCoupon, never()).getDiscountPercent();
    }

    @Test
    void testInorderForMultipleMocks(){
        MockitoAnnotations.openMocks(this);
        Item itemMock = mock(Item.class);

        when(thanksgivingCoupon.isAppliable(itemMock)).thenReturn(true);
        when(thanksgivingCoupon.isValid()).thenReturn(true);

        priceCalculator = new PriceCalculator();
        priceCalculator.getOrderPrice(itemMock, thanksgivingCoupon);

        InOrder inOrder = inOrder(itemMock,thanksgivingCoupon);

        inOrder.verify(thanksgivingCoupon).isValid();
        inOrder.verify(thanksgivingCoupon).isAppliable(itemMock);
        inOrder.verify(itemMock).getPrice();

    }
}

class ThrowingExceptionTest{

    @Test
    void testThrowingException_thenThrow(){
        Coupon couponMock = mock(Coupon.class);

        when(couponMock.getName()).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, ()->couponMock.getName());
        when(couponMock.isAppliable(any())).thenReturn(true);
        assertDoesNotThrow(() -> couponMock.isAppliable(new Item("itemCode0123","itemName", 23400)));
        assertTrue(couponMock.isAppliable(new Item("itemCode0000","itemName0000", 20000)));

    }

    @Test
    void testThrowingException_thenThrow2(){
        Coupon couponMock = mock(Coupon.class);

        when(couponMock.getName())
                .thenReturn("mocked result")
                .thenThrow(IllegalArgumentException.class);

        assertEquals("mocked result", couponMock.getName());
        assertThrows(IllegalArgumentException.class, ()->couponMock.getName());
    }

    @Test
    void testThrowingException_doThrow(){
        Coupon couponMock = mock(Coupon.class);
        doThrow(IllegalArgumentException.class).when(couponMock).doExpire();
        assertThrows(IllegalArgumentException.class, ()->couponMock.doExpire());

        Item itemMock = mock(Item.class);
        doThrow(IllegalArgumentException.class).when(itemMock).setCategoryName(anyString());
        assertThrows(IllegalArgumentException.class, ()->itemMock.setCategoryName("1"));
    }
}

class MatcherTest {

    class Person {
        private String fullName;
        private int age;
        final int ADULT_AGE = 19;

        public Person(String fullName){
            this.fullName = fullName;
        }

        public void setAge(int age){
            this.age = age;
        }
        public int getAge(){
            return age;
        }

        public boolean isAdult(int age){
            return age >= ADULT_AGE;
        }

    }

    @Test
    void testMatchers() {
        Person personMock = mock(Person.class);

        doThrow(IllegalArgumentException.class).when(personMock).setAge(leq(19));
        assertDoesNotThrow(() -> personMock.setAge(20));
        assertThrows(IllegalArgumentException.class, () -> personMock.setAge(18));
    }

    @Test
    void testCustomerMatchers() {
        Person personMock = mock(Person.class);

        doThrow(IllegalArgumentException.class).when(personMock).setAge(intThat(MyMatchers.isLessThanOrEqualTo(5)));

        assertDoesNotThrow(() -> personMock.setAge(6));
        assertThrows(IllegalArgumentException.class, () -> personMock.setAge(3));

    }
}

class MyMatchers{
    static ArgumentMatcher<Integer> isLessThanOrEqualTo(int value){
        System.out.println("isLessThatnOrEqualTo" + value);
        return new LessThanOrEqualToMatcher(value);
    }
}

class LessThanOrEqualToMatcher implements ArgumentMatcher<Integer> {
    int value;

    LessThanOrEqualToMatcher(int value){
        this.value = value;
    }

    @Override
    public boolean matches(Integer argument){
        System.out.println(argument);
        return argument <= value;
    }
}