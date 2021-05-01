package com.idealista.ranking.service.score.rule;

import com.idealista.ranking.common.service.rule.BaseRule;
import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Score;

/**
 * Base rule service for Score calculations in Ads
 */
public abstract class BaseScoreRule extends BaseRule<Advertisement, Score> {
    /**
     * Executes a rule if the conditions for the current implementation are met.
     *
     * @return current score after rule update. If the conditions for this rule are not met, it returns the unmodified score.
     */
    public final Advertisement executeRule(Advertisement ad) {
        Advertisement result = ad;

        if (ruleApplies(ad)) {
            result = ad.toBuilder()
                    .score(updateResult(ad))
                    .build();
        }

        return result;
    }

    /**
     * Takes the current score and applies the current rule updates.
     *
     * @return the score after applying the rule update
     */
    protected abstract Score updateResult(Advertisement ad);

}
