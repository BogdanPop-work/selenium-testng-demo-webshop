package com.bogdan.automation.clients;

public class WarehouseApiClient {

    public WarehouseApiResponse sendOrderToWarehouse(String orderNumber) {

        String warehouseReference = "WH-" + orderNumber;

        return new WarehouseApiResponse(
                202,
                "ACCEPTED",
                warehouseReference,
                "Order accepted by warehouse system");
    }
}