package com.idealista.ranking.service.score.rule;

import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Score;
import com.idealista.ranking.model.service.enumeration.AdvertisementTypology;

/**
 * If it is a chalet Advertisement adds point considering description length
 */
public class ChaletDescriptionLengthRule extends BaseScoreRule {
    private final Integer RULE_INCREMENT_VALUE;
    private final Integer MIN_BOUND;

    public ChaletDescriptionLengthRule(Integer ruleIncrementValue, Integer minBound) {
        this.RULE_INCREMENT_VALUE = ruleIncrementValue;
        this.MIN_BOUND = minBound;
    }

    @Override
    protected Boolean ruleApplies(Advertisement ad) {
        return (ad.getTypology() != null && ad.getTypology().equals(AdvertisementTypology.CHALET))
                && (ad.getDescription() != null && ad.getDescription().length() > MIN_BOUND);
    }

    @Override
    protected Score updateScore(Advertisement ad) {
        return ad.getScore().add(RULE_INCREMENT_VALUE);
    }
}
