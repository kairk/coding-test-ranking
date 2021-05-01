package com.idealista.ranking.service.factory;

import com.idealista.ranking.common.service.executor.RuleExecutor;
import com.idealista.ranking.exception.AdsServiceException;
import com.idealista.ranking.model.service.enumeration.RuleType;
import com.idealista.ranking.service.score.executor.ScoreRuleExecutor;
import com.idealista.ranking.service.score.rule.BaseScoreRule;
import com.idealista.ranking.service.score.rule.NoPictureRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RuleExecutorFactoryTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Mock
    private ScoreRuleCollectionFactory scoreRulesFactory;

    @Test
    public void create_OK() throws AdsServiceException {
        //Given
        RuleType selector = RuleType.SCORE;
        List<BaseScoreRule> rules = Collections.singletonList(new NoPictureRule(1));
        when(scoreRulesFactory.create(selector)).thenReturn(rules);

        RuleExecutorFactory ruleExecutorFactory = new RuleExecutorFactory(scoreRulesFactory);

        //When
        RuleExecutor<?, ?> ruleExecutor = ruleExecutorFactory.create(selector);

        //Then
        assertEquals(ruleExecutor.getClass(), ScoreRuleExecutor.class);

    }

    @Test
    public void create_whenUnknownSelector_KO() throws AdsServiceException {
        //Given
        RuleType selector = RuleType.UNKNOWN;
        exceptionRule.expect(isA(AdsServiceException.class));
        exceptionRule.expectMessage("Couldn't create Executor from selector: " + selector.name());

        RuleExecutorFactory ruleExecutorFactory = new RuleExecutorFactory(scoreRulesFactory);

        //When
        ruleExecutorFactory.create(selector);

        //Then
        verify(scoreRulesFactory, never()).create(any());

    }
}
