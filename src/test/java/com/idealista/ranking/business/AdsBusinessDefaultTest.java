package com.idealista.ranking.business;

import com.idealista.ranking.business.impl.AdsBusinessDefault;
import com.idealista.ranking.common.service.executor.RuleExecutor;
import com.idealista.ranking.configuration.GenericConfig;
import com.idealista.ranking.exception.AdsNoContentException;
import com.idealista.ranking.exception.AdsServiceException;
import com.idealista.ranking.exception.GenericAdsException;
import com.idealista.ranking.mapper.AdvertisementServiceMapper;
import com.idealista.ranking.model.api.response.PublicAdResponse;
import com.idealista.ranking.model.api.response.QualityAdResponse;
import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Score;
import com.idealista.ranking.model.service.enumeration.RuleType;
import com.idealista.ranking.service.AdsService;
import com.idealista.ranking.service.factory.RuleExecutorFactory;
import com.idealista.ranking.service.score.executor.ScoreRuleExecutor;
import com.idealista.ranking.service.score.rule.HasDescriptionRule;
import com.idealista.ranking.service.score.rule.NoPictureRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdsBusinessDefaultTest {

    @Mock
    RuleExecutorFactory ruleExecutorFactory;

    @Mock
    AdsService service;

    @Spy
    AdvertisementServiceMapper mapper = Mappers.getMapper(AdvertisementServiceMapper.class);

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private GenericConfig config = GenericConfig.builder().minScorePublicAds(40).build();

    private final Integer increment = 10;

    @Test
    public void calculateScore_OK() throws AdsServiceException {
        //Given
        ScoreRuleExecutor ruleExecutor = new ScoreRuleExecutor(Collections.singletonList(new HasDescriptionRule(1)));

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
        doReturn(expected).when(service).calculateScore(ruleExecutor, ads);
        doNothing().when(service).upsertAdvertisements(anyCollection());
        doReturn(ruleExecutor).when(ruleExecutorFactory).create(eq(RuleType.SCORE));

        AdsBusinessDefault business = new AdsBusinessDefault(config, ruleExecutorFactory, service, mapper);

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

        AdsBusinessDefault business = new AdsBusinessDefault(config, ruleExecutorFactory, service, mapper);

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

        AdsBusinessDefault business = new AdsBusinessDefault(config, ruleExecutorFactory, service, mapper);

        //When
        business.calculateScore();

        //Then exception is thrown
    }

    @Test
    public void getPublicListing_OK() {
        //Given
        int pageSize = 5;

        List<Advertisement> ads = generateAdList(pageSize * 2);

        when(service.getAdsFilterByScore(eq(config.getMinScorePublicAds()), any(BiFunction.class))).thenReturn(ads);

        AdsBusinessDefault business = new AdsBusinessDefault(config, ruleExecutorFactory, service, mapper);

        //When
        List<PublicAdResponse> result = business.getPublicListing(1, pageSize);

        //Then
        assertEquals(pageSize, result.size());
        verify(service, times(1)).getAdsFilterByScore(eq(config.getMinScorePublicAds()), any(BiFunction.class));
        verify(mapper, times(pageSize)).adServiceToResponseMapper(any(Advertisement.class));

    }

    @Test
    public void getPublicListing_onEmptyResult_KO() throws AdsServiceException {
        //Given
        exceptionRule.expect(isA(AdsNoContentException.class));
        exceptionRule.expectMessage("There are no public Ads matching criteria");

        int pageSize = 5;

        AdsBusinessDefault business = new AdsBusinessDefault(config, ruleExecutorFactory, service, mapper);

        //When
        business.getPublicListing(1, pageSize);

        //Then exception is thrown
    }

    @Test
    public void getQualityListing_OK() {
        //Given
        int pageSize = 5;

        List<Advertisement> ads = generateAdList(pageSize * 2);

        when(service.getAdsFilterByScore(eq(config.getMinScorePublicAds()), any(BiFunction.class))).thenReturn(ads);

        AdsBusinessDefault business = new AdsBusinessDefault(config, ruleExecutorFactory, service, mapper);

        //When
        List<QualityAdResponse> result = business.getQualityListing(1, pageSize);

        //Then
        assertEquals(pageSize, result.size());
        verify(service, times(1)).getAdsFilterByScore(eq(config.getMinScorePublicAds()), any(BiFunction.class));
        verify(mapper, times(pageSize)).adServiceToQualityResponseMapper(any(Advertisement.class));

    }

    @Test
    public void getQualityListing_onEmptyResult_KO() throws AdsServiceException {
        //Given
        exceptionRule.expect(isA(AdsNoContentException.class));
        exceptionRule.expectMessage("There are no public Ads matching criteria");

        BiFunction<Integer, Integer, Boolean> filterFunction = (currentScore, minScore) -> currentScore >= minScore;

        int pageSize = 5;

        List<Advertisement> ads = new ArrayList<>();

        AdsBusinessDefault business = new AdsBusinessDefault(config, ruleExecutorFactory, service, mapper);

        //When
        business.getQualityListing(1, pageSize);

        //Then exception is thrown
    }


    private List<Advertisement> generateAdList(Integer id) {
        return IntStream.range(0, id).mapToObj(cId -> Advertisement.builder()
                .id(cId).score(Score.builder().current(10 * cId).build())
                .build())
                .collect(Collectors.toList());
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
