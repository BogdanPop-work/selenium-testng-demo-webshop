package com.bogdan.automation.models;

public record ProductData(
        String name,
        String category,
        String subcategory,
        Double price,
        String type,
        Double oldPrice,
        String description,
        String availability,
        String supportedAction,
        String note) {
}