package cyclechronicles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

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


    // Test, if the Pending Order is empty -> by getting an Order and checking if the Order exists or not
    @Test
    public void testShop_CheckIfThePendingListIsEmpty(){
        Optional<Order> order = shop.repair();
        assertFalse(order.isPresent());
    }

    // Test, if the Orders get added into the Pending List
    @Test
    public void testShop_CheckIfTheOrdersGetAddedToPendingList(){

        // First add a new Order
        when(order.customer()).thenReturn("CustomerOne");
        when(order.bicycleType()).thenReturn(Type.FIXIE);
        shop.accept(order);

        // Now get the Order from the Pending List
        Optional<Order> order = shop.repair();
        assertTrue(order.isPresent());
    }

    // Test, if a specific Customer will get his bike delivered, by calling the delivered method with the customer name.
    @Test
    public void testShop_CustomerGetTheBikeDelivered(){

        // First add a new Order
        when(order.customer()).thenReturn("CustomerOne");
        when(order.bicycleType()).thenReturn(Type.FIXIE);
        shop.accept(order);

        // Now repair the bike
        shop.repair();

        // Now get the repaired bike
        Optional<Order> repairedBike = shop.deliver("CustomerOne");
        assertTrue(repairedBike.isPresent());
    }
}
