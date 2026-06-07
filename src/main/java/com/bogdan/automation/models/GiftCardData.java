package com.bogdan.automation.models;

public record GiftCardData(
        String name,
        String category,
        String type,
        Double price,
        Boolean requiresRecipientName,
        Boolean requiresRecipientEmail,
        Boolean requiresSenderName,
        Boolean requiresSenderEmail,
        String availability,
        String supportedAction,
        String note) {
}