package com.idealista.ranking.service.factory;

import com.idealista.ranking.configuration.RuleConstraintConfig;
import com.idealista.ranking.configuration.RuleValueConfig;
import com.idealista.ranking.exception.AdsServiceException;
import com.idealista.ranking.model.service.enumeration.RuleType;
import com.idealista.ranking.service.score.rule.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ScoreRuleCollectionFactoryTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void create_OK() throws AdsServiceException {
        //Given
        RuleType selector = RuleType.SCORE;
        RuleValueConfig ruleValues = RuleValueConfig.builder().build();
        RuleConstraintConfig ruleConstraints = RuleConstraintConfig.builder().build();

        List<BaseScoreRule> expected = getExpected(ruleValues, ruleConstraints);

        ScoreRuleCollectionFactory ruleCollectionFactory = new ScoreRuleCollectionFactory(ruleValues, ruleConstraints);

        //When
        Collection<BaseScoreRule> result = ruleCollectionFactory.create(selector);

        //Then
        assertEquals(expected.size(), result.size());
        //Assert that every expected classes are in the result
        assertTrue(expected.stream().allMatch(a -> result.stream().anyMatch(b -> b.getClass().equals(a.getClass()))));

    }

    @Test
    public void create_whenUnknownSelector_KO() throws AdsServiceException {
        //Given
        RuleType selector = RuleType.UNKNOWN;
        RuleValueConfig ruleValues = RuleValueConfig.builder().build();
        RuleConstraintConfig ruleConstraints = RuleConstraintConfig.builder().build();

        exceptionRule.expect(isA(AdsServiceException.class));
        exceptionRule.expectMessage("Couldn't create rule collection from selector: " + selector.name());

        ScoreRuleCollectionFactory ruleCollectionFactory = new ScoreRuleCollectionFactory(ruleValues, ruleConstraints);

        //When
        ruleCollectionFactory.create(selector);

        //Then
        verify(ruleCollectionFactory, never()).create(any());

    }

    private List<BaseScoreRule> getExpected(RuleValueConfig ruleValues, RuleConstraintConfig ruleConstraints) {
        return Arrays.asList(
                new NoPictureRule(ruleValues.getNoPicture()),
                new EachPictureQualityRule(ruleValues.getHdPicture(), ruleValues.getDefaultQualityPicture()),
                new HasDescriptionRule(ruleValues.getHasDescription()),
                new FlatDescriptionLengthRule(ruleValues.getFlatDescriptionLowerBound(), ruleValues.getFlatDescriptionUpperBound(), ruleConstraints.getFlatDescriptionLowerBound(), ruleConstraints.getFlatDescriptionUpperBound()),
                new ChaletDescriptionLengthRule(ruleValues.getChaletDescriptionLowerBound(), ruleConstraints.getChaletDescriptionLowerBound()),
                new WordOccurrenceInDescriptionRule(ruleValues.getWordOccurence(), ruleConstraints.getWordOccurrence()),
                new FlatCompleteInfoRule(ruleValues.getCompleteInfo()),
                new ChaletCompleteInfoRule(ruleValues.getCompleteInfo()),
                new GarageCompleteInfoRule(ruleValues.getCompleteInfo())
        );
    }
}
