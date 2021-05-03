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
    private final Integer max = 100;
    @Builder.Default
    private final Integer min = 0;
    @Builder.Default
    private final Integer current = 0;

    public Score add(Score score) {
        return this.add(score.getCurrent());
    }

    public Score add(Integer quantity) {
        Integer result;
        if (current + quantity <= max) {
            result = current + quantity;
        } else {
            result = getMax();
            log.warn("couldn't add: " + quantity + " to current score: " + current + ", maximum exceeded: " + max);
        }

        return this.toBuilder().current(result).build();
    }

    public Score subtract(Score score) {
        return this.subtract(score.getCurrent());
    }

    public Score subtract(Integer quantity) {
        Integer result;
        if (current - quantity >= min) {
            result = current - quantity;
        } else {
            result = getMin();
            log.warn("couldn't subtract: " + quantity + " to current score: " + current + ", minimum is inferior: " + min);
        }

        return this.toBuilder().current(result).build();

    }

    /**
     * returns a new instance of Score with its current value set to the actual minimum
     */
    public Score resetScore() {
        return this.toBuilder().current(getMin()).build();
    }
}
