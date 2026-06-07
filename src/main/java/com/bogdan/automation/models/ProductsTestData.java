package com.bogdan.automation.models;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public record ProductsTestData(
        JsonNode metadata,
        List<ProductData> simpleProducts,
        List<ProductData> downloadableProducts,
        List<GiftCardData> giftCards,
        List<ConfigurableProductData> configurableProducts,
        JsonNode specialHandlingProducts) {
}