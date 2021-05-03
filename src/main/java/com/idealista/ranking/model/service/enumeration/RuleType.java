package com.idealista.ranking.model.service.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Selectors for factories. Ideally, each factory should have its own selector,
 * but as a simplified version I've used this one for RuleExecutor and RuleCollection factories
 */
public enum RuleType {
    SCORE,
    UNKNOWN;

    public static Optional<RuleType> fromString(String type) {
        Optional<RuleType> result = Optional.empty();
        List<RuleType> values = Arrays.asList(RuleType.values());
        if (type != null) {
            result = values.stream().filter(a -> a.name().equals(type.toUpperCase())).findAny();
        }

        return result;
    }
}
