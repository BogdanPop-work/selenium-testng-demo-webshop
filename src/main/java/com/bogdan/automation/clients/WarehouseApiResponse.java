package com.bogdan.automation.clients;

public record WarehouseApiResponse(
        int statusCode,
        String status,
        String warehouseReference,
        String message
) {
}