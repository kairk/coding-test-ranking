package com.idealista.ranking.service.score.rule;

import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Score;
import com.idealista.ranking.model.service.enumeration.AdvertisementTypology;

/**
 * If the chalet information is considered completed adds X points
 */
public class ChaletCompleteInfoRule extends BaseScoreRule {
    private final Integer RULE_INCREMENT_VALUE;

    public ChaletCompleteInfoRule(Integer ruleIncrementValue) {
        this.RULE_INCREMENT_VALUE = ruleIncrementValue;
    }

    @Override
    protected Boolean ruleApplies(Advertisement ad) {
        return (ad.getTypology() != null && ad.getTypology().equals(AdvertisementTypology.CHALET))
                && isCompletedInfo(ad);
    }

    @Override
    protected Score updateScore(Advertisement ad) {
        return ad.getScore().add(RULE_INCREMENT_VALUE);
    }

    /**
     * The flat information is considered completed when it has description, at least 1 picture, houseSize and gardenSize
     */
    private Boolean isCompletedInfo(Advertisement ad) {
        return (ad.getDescription() != null && !ad.getDescription().isEmpty())
                && (ad.getPictures() != null && !ad.getPictures().isEmpty())
                && (ad.getHouseSize() != null && ad.getHouseSize() > 0)
                && (ad.getGardenSize() != null && ad.getGardenSize() > 0);
    }

}
