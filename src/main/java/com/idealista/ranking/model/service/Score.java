package com.idealista.ranking.model.service;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Builder(toBuilder = true)
@ToString
@Slf4j
@EqualsAndHashCode
public class Score {
    @Builder.Default
    private final Integer MAX = 100;
    @Builder.Default
    private final Integer MIN = 0;
    @Builder.Default
    private final Integer current = 0;

    public Score add(Score score) {
        return this.add(score.getCurrent());
    }

    public Score add(Integer quantity) {
        Integer result;
        if (current + quantity <= MAX) {
            result = current + quantity;
        } else {
            result = getMAX();
            log.warn("couldn't add: " + quantity + " to current score: " + current + ", maximum exceeded: " + MAX);
        }

        return this.toBuilder().current(result).build();
    }

    public Score subtract(Score score) {
        return this.subtract(score.getCurrent());
    }

    public Score subtract(Integer quantity) {
        Integer result;
        if (current - quantity >= MIN) {
            result = current - quantity;
        } else {
            result = getMIN();
            log.warn("couldn't subtract: " + quantity + " to current score: " + current + ", minimum is inferior: " + MIN);
        }

        return this.toBuilder().current(result).build();

    }
}
