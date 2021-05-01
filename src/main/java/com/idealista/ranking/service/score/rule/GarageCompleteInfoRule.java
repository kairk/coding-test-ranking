package com.idealista.ranking.service.score.rule;

import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Score;
import com.idealista.ranking.model.service.enumeration.AdvertisementTypology;

/**
 * If the garage information is considered completed adds X points
 */
public class GarageCompleteInfoRule extends BaseScoreRule {
    private final Integer RULE_INCREMENT_VALUE;

    public GarageCompleteInfoRule(Integer ruleIncrementValue) {
        this.RULE_INCREMENT_VALUE = ruleIncrementValue;
    }

    @Override
    protected Boolean ruleApplies(Advertisement ad) {
        return (ad.getTypology() != null && ad.getTypology().equals(AdvertisementTypology.GARAGE))
                && (ad.getPictures() != null && !ad.getPictures().isEmpty());
    }

    @Override
    protected Score updateResult(Advertisement ad) {
        return ad.getScore().add(RULE_INCREMENT_VALUE);
    }

}
