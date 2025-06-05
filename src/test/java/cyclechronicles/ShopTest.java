package cyclechronicles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ShopTest {

    Shop shop;
    Order order;

    @BeforeEach
    public void setUp(){
        order = mock(Order.class);
        shop = new Shop();
    }

    // Add a new Order into the Pending List
    @Test
    public void testShop_AddNewOrderIntoPendingList(){
        when(order.getCustomer()).thenReturn("Customer Name");
        when(order.getBicycleType()).thenReturn(Type.FIXIE);

        assertTrue(shop.accept(order));
    }

    // Test if an existing Customer can add a new Order
    @Test
    public void testShop_CanExistingCustomerAddAnotherOrder(){

        when(order.getCustomer()).thenReturn("Customer Name");
        when(order.getBicycleType()).thenReturn(Type.FIXIE);

        assertTrue(shop.accept(order));

        when(order.getCustomer()).thenReturn("Customer Name");
        when(order.getBicycleType()).thenReturn(Type.RACE);

        assertFalse(shop.accept(order));
    }

    // Test, that a customer can not add a Gravel or an Ebike
    @Test
    public void testShop_CustomerNotAllowedToAddGravelBike(){
        when(order.getCustomer()).thenReturn("Customer One");
        when(order.getBicycleType()).thenReturn(Type.GRAVEL);
        assertFalse(shop.accept(order));
    }

    // Test, that a customer can not add a Gravel or an Ebike
    @Test
    public void testShop_CustomerNotAllowedToAddEBike(){
        when(order.getCustomer()).thenReturn("Customer One");
        when(order.getBicycleType()).thenReturn(Type.EBIKE);
        assertFalse(shop.accept(order));
    }



}
