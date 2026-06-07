package com.bogdan.automation.models;

public record ConfigurableProductData(
        String name,
        String category,
        String subcategory,
        Double basePrice,
        ProductAttributes attributes) {
}