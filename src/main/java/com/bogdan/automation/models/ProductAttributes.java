package com.bogdan.automation.models;

import java.util.List;

public record ProductAttributes(
        List<ProductOption> processors,
        List<ProductOption> ramOptions,
        List<ProductOption> hddOptions,
        List<ProductOption> osOptions,
        List<ProductOption> softwareOptions) {
}