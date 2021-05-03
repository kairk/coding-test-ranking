package com.idealista.ranking.service.score.rule;

import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Score;

/**
 * If the Advertisement has description adds X points
 */
public class HasDescriptionRule extends BaseScoreRule {
    private final Integer RULE_INCREMENT_VALUE;

    public HasDescriptionRule(Integer ruleIncrementValue) {
        RULE_INCREMENT_VALUE = ruleIncrementValue;
    }

    @Override
    protected Boolean ruleApplies(Advertisement ad) {
        return ad.getDescription() != null && !ad.getDescription().isEmpty();
    }

    @Override
    protected Score updateResult(Advertisement ad) {
        return ad.getScore().add(RULE_INCREMENT_VALUE);
    }
}
