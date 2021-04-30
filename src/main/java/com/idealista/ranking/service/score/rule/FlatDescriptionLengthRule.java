package com.idealista.ranking.service.score.rule;

import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Score;
import com.idealista.ranking.model.service.enumeration.AdvertisementTypology;

/**
 * If it is a flat Advertisement adds point considering description length
 */
public class FlatDescriptionLengthRule extends BaseScoreRule {
    private final Integer RULE_INCREMENT_MIN_VALUE;
    private final Integer RULE_INCREMENT_MAX_VALUE;
    private final Integer MIN_BOUND;
    private final Integer MAX_BOUND;

    public FlatDescriptionLengthRule(Integer ruleIncrementMinValue, Integer ruleIncrementMaxValue, Integer minBound, Integer maxBound) {
        this.RULE_INCREMENT_MIN_VALUE = ruleIncrementMinValue;
        this.RULE_INCREMENT_MAX_VALUE = ruleIncrementMaxValue;
        this.MIN_BOUND = minBound;
        this.MAX_BOUND = maxBound;
    }

    @Override
    protected Boolean ruleApplies(Advertisement ad) {
        return (ad.getTypology() != null && ad.getTypology().equals(AdvertisementTypology.FLAT))
                && (ad.getDescription() != null && ad.getDescription().length() >= MIN_BOUND);
    }

    @Override
    protected Score updateScore(Advertisement ad) {
        int descLength = ad.getDescription().length();
        int increment;

        if (descLength >= MIN_BOUND && descLength < MAX_BOUND) {
            increment = RULE_INCREMENT_MIN_VALUE;
        } else if (descLength >= MAX_BOUND) {
            increment = RULE_INCREMENT_MAX_VALUE;
        } else {
            increment = 0;
        }

        return ad.getScore().add(increment);
    }
}
