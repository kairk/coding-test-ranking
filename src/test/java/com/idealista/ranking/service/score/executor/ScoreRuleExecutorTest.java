package com.idealista.ranking.service.score.executor;

import com.idealista.ranking.common.service.executor.RuleExecutor;
import com.idealista.ranking.exception.AdsServiceException;
import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Score;
import com.idealista.ranking.service.score.rule.BaseScoreRule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ScoreRuleExecutorTest {
    Integer scoreIncrement = 10;


    @Test
    public void executeRules_OK() throws AdsServiceException {
        //Given
        Advertisement ad = Advertisement.builder().id(1).build();
        MockScoreRule mockRule = new MockScoreRule();
        List<BaseScoreRule> scoreRules = Arrays.asList(mockRule, mockRule);
        RuleExecutor<Score, BaseScoreRule> testExecutor = new ScoreRuleExecutor(scoreRules);

        //When
        Score result = testExecutor.getResult(ad);

        //Then
        assertEquals(scoreIncrement * scoreRules.size(), result.getCurrent(), 0.01);

    }

    private class MockScoreRule extends BaseScoreRule {

        @Override
        protected Score updateResult(Advertisement ad) {
            return ad.getScore().add(scoreIncrement);
        }
    }
}
