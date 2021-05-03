package com.idealista.ranking.common.service.executor;

import com.idealista.ranking.common.service.executor.RuleExecutor;
import com.idealista.ranking.exception.AdsServiceException;
import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.service.score.rule.BaseScoreRule;
import com.idealista.ranking.service.score.rule.HasDescriptionRule;
import com.idealista.ranking.service.score.rule.NoPictureRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertEquals;

public class RuleExecutorTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void getResult_OK() throws AdsServiceException {
        //Given
        Advertisement ad = Advertisement.builder().build();
        RuleExecutor<Integer, BaseScoreRule> testExecutor = new TestRuleExecutor(Arrays.asList(new NoPictureRule(1), new HasDescriptionRule(2)));

        //When
        Integer result = testExecutor.getResult(ad);

        //Then
        assertEquals(2, result, 0.01);

    }

    @Test
    public void init_whenEmptyRulesList_KO() throws AdsServiceException {
        //Given
        exceptionRule.expect(isA(AdsServiceException.class));
        exceptionRule.expectMessage("Rules for executor can't be null or empty");

        //When
        new TestRuleExecutor(Collections.emptyList());

        //Then exception should be caught

    }

    @Test
    public void init_whenNullRulesList_KO() throws AdsServiceException {
        //Given
        exceptionRule.expect(isA(AdsServiceException.class));
        exceptionRule.expectMessage("Rules for executor can't be null or empty");

        //When
        new TestRuleExecutor(null);

        //Then exception should be caught

    }


    private static class TestRuleExecutor extends RuleExecutor<Integer, BaseScoreRule> {
        public TestRuleExecutor(Collection<BaseScoreRule> rules) throws AdsServiceException {
            super(rules);
        }

        @Override
        protected Integer executeRules(Collection<BaseScoreRule> rules, Advertisement ad) {
            return rules.size();
        }
    }
}
