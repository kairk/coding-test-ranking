package com.idealista.ranking.model.service.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum AdvertisementTypology {
    FLAT,
    CHALET,
    GARAGE,
    UNKNOWN;

    public static Optional<AdvertisementTypology> fromString(String quality) {
        Optional<AdvertisementTypology> result = Optional.empty();
        List<AdvertisementTypology> values = Arrays.asList(AdvertisementTypology.values());
        if (quality != null) {
            result = values.stream().filter(a -> a.name().equals(quality.toUpperCase())).findAny();
        }

        return result;
    }
}
