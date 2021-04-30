package com.idealista.ranking.service.score.rule;

import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Score;

/**
 * If the Advertisement has no photos the score decrements X points
 */
public class NoPictureRule extends BaseScoreRule {
    private final Integer RULE_DECREMENT_VALUE;

    public NoPictureRule(Integer ruleDecrementValue) {
        RULE_DECREMENT_VALUE = ruleDecrementValue;
    }

    @Override
    protected Boolean ruleApplies(Advertisement ad) {
        return ad.getPictures() == null || ad.getPictures().isEmpty();
    }

    @Override
    protected Score updateScore(Advertisement ad) {
        return ad.getScore().subtract(RULE_DECREMENT_VALUE);
    }
}
