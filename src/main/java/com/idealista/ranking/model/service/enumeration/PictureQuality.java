package com.idealista.ranking.model.service.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum PictureQuality {
    SD,
    HD,
    UNKNOWN;

    public static Optional<PictureQuality> fromString(String quality) {
        Optional<PictureQuality> result = Optional.empty();
        List<PictureQuality> values = Arrays.asList(PictureQuality.values());
        if (quality != null) {
            result = values.stream().filter(a -> a.name().equals(quality.toUpperCase())).findAny();
        }

        return result;
    }
}
