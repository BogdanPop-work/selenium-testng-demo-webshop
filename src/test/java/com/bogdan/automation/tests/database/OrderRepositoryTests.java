package com.bogdan.automation.tests.database;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.bogdan.automation.base.BaseTest;
import com.bogdan.automation.repositories.OrderRepository;
import com.bogdan.automation.services.OrderLifecycleService;

public class OrderRepositoryTests extends BaseTest {

    @Test
    public void verifyCompletedOrderCanBeSavedInDatabase() {

        OrderLifecycleService orderService = new OrderLifecycleService();

        String orderNumber = "TEST-ORDER-1001";

        orderService.saveCompletedOrder(
                orderNumber,
                "John",
                "Doe",
                "john.doe@test.com",
                "Credit Card",
                "Ground");
       // new OrderRepository().printOrders();
        Assert.assertTrue(
                orderService.orderExists(orderNumber),
                "Order was not saved in the database");
        
    }
   
}