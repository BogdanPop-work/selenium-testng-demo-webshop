package com.bogdan.automation.models;

import java.util.List;

public record ComputerConfiguration(
        String processor,
        String ram,
        String hdd,
        List<String> software,
        double expectedPrice) {
}