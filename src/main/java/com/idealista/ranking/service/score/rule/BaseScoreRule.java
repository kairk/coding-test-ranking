package com.idealista.ranking.service.score.rule;

import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Score;

/**
 * Base rule service interface, all rules must extend this service.
 */
public abstract class BaseScoreRule {
    /**
     * Executes a rule if the conditions for the current implementation are met.
     *
     * @return current score after rule update. If the conditions for this rule are not met, it returns the unmodified score.
     */
    public final Advertisement executeRule(Advertisement ad) {
        Advertisement result = ad;

        if (ruleApplies(ad)) {
            result = ad.toBuilder()
                    .score(updateScore(ad))
                    .build();
        }

        return result;
    }

    /**
     * Specifies when a rule must be executed. By default a rule is always executed.
     */
    protected Boolean ruleApplies(Advertisement ad) {
        return true;
    }

    /**
     * Takes the current score and applies the current rule updates.
     *
     * @return the score after applying the rule update
     */
    protected abstract Score updateScore(Advertisement ad);

}
