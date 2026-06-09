package com.bogdan.automation.utils;

import java.util.UUID;

public class PurchaseOrderGenerator {

    public static String generatePurchaseOrderNumber() {
        return "PO-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }
}