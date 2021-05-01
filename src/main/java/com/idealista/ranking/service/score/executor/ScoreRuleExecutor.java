package com.idealista.ranking.service.score.executor;

import com.idealista.ranking.common.service.executor.RuleExecutor;
import com.idealista.ranking.exception.AdsServiceException;
import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Score;
import com.idealista.ranking.service.score.rule.BaseScoreRule;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class ScoreRuleExecutor extends RuleExecutor<Score, BaseScoreRule> {
    public ScoreRuleExecutor(Collection<BaseScoreRule> rules) throws AdsServiceException {
        super(rules);
    }

    @Override
    protected Score executeRules(Collection<BaseScoreRule> rules, Advertisement ad) {
        log.info("Executing rules for: " + ad);
        Score identityVal = ad.getScore().resetScore();

        return rules.stream()
                .map(a -> a.executeRule(ad).getScore())
                .reduce(identityVal, Score::add);
    }
}
