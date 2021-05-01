package com.idealista.ranking.model.service.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum RuleType {
    SCORE,
    UNKNOWN;

    public static Optional<RuleType> fromString(String quality) {
        Optional<RuleType> result = Optional.empty();
        List<RuleType> values = Arrays.asList(RuleType.values());
        if (quality != null) {
            result = values.stream().filter(a -> a.name().equals(quality.toUpperCase())).findAny();
        }

        return result;
    }
}
