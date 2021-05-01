package com.idealista.ranking.business;

import com.idealista.ranking.business.impl.AdsBusinessDefault;
import com.idealista.ranking.common.service.executor.RuleExecutor;
import com.idealista.ranking.exception.AdsServiceException;
import com.idealista.ranking.exception.GenericAdsException;
import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Score;
import com.idealista.ranking.model.service.enumeration.RuleType;
import com.idealista.ranking.service.AdsService;
import com.idealista.ranking.service.factory.RuleExecutorFactory;
import com.idealista.ranking.service.score.executor.ScoreRuleExecutor;
import com.idealista.ranking.service.score.rule.BaseScoreRule;
import com.idealista.ranking.service.score.rule.HasDescriptionRule;
import com.idealista.ranking.service.score.rule.NoPictureRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.isA;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdsBusinessDefaultTest {

    @Mock
    RuleExecutorFactory ruleExecutorFactory;

    @Mock
    AdsService service;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private Integer increment = 10;

    @Test
    public void calculateScore_OK() throws AdsServiceException {
        //Given
        ScoreRuleExecutor ruleExecutor = new TestExecutor(Collections.singletonList(new HasDescriptionRule(1)));

        Score score = Score.builder().current(increment).build();
        List<Advertisement> ads = Arrays.asList(
                Advertisement.builder().id(1).build(),
                Advertisement.builder().id(2).build(),
                Advertisement.builder().id(3).build()
        );
        List<Advertisement> expected = Arrays.asList(
                Advertisement.builder().id(1).score(score).build(),
                Advertisement.builder().id(2).score(score).build(),
                Advertisement.builder().id(3).score(score).build()
        );

        doReturn(ads).when(service).getAllAds();
        doNothing().when(service).upsertAdvertisements(anyCollection());
        doReturn(ruleExecutor).when(ruleExecutorFactory).create(eq(RuleType.SCORE));

        AdsBusinessDefault business = new AdsBusinessDefault(ruleExecutorFactory, service);

        //When
        business.calculateScore();

        //Then
        verify(service, times(1)).upsertAdvertisements(expected);

    }

    @Test
    public void calculateScore_whenExecutorIsNotExpectedClass_KO() throws AdsServiceException {
        //Given
        TestFailedExecutor ruleExecutor = new TestFailedExecutor(Collections.singletonList(new NoPictureRule(1)));
        exceptionRule.expect(isA(GenericAdsException.class));
        exceptionRule.expectMessage("Unexpected executor type returned from factory: " + ruleExecutor.getClass());

        doReturn(ruleExecutor).when(ruleExecutorFactory).create(eq(RuleType.SCORE));

        AdsBusinessDefault business = new AdsBusinessDefault(ruleExecutorFactory, service);

        //When
        business.calculateScore();

        //Then exception is thrown

    }

    @Test
    public void calculateScore_whenExecutorFactoryThrowsException_KO() throws AdsServiceException {
        //Given
        exceptionRule.expect(isA(GenericAdsException.class));
        exceptionRule.expectMessage("Error obtaining rule executor");

        doThrow(new AdsServiceException("test error")).when(ruleExecutorFactory).create(eq(RuleType.SCORE));

        AdsBusinessDefault business = new AdsBusinessDefault(ruleExecutorFactory, service);

        //When
        business.calculateScore();

        //Then exception is thrown
    }


    private class TestExecutor extends ScoreRuleExecutor {
        public TestExecutor(Collection<BaseScoreRule> rules) throws AdsServiceException {
            super(rules);
        }

        @Override
        protected Score executeRules(Collection<BaseScoreRule> rules, Advertisement ad) {
            return ad.getScore().add(increment);
        }
    }

    private class TestFailedExecutor extends RuleExecutor {

        public TestFailedExecutor(Collection rules) throws AdsServiceException {
            super(rules);
        }

        @Override
        protected Object executeRules(Collection rules, Advertisement ad) {
            return null;
        }
    }
}
