package com.idealista.ranking.service.score;

import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.enumeration.AdvertisementTypology;
import com.idealista.ranking.service.score.rule.FlatDescriptionLengthRule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FlatDescriptionLengthRuleTest {

    @Test
    public void executeRule_descriptionBiggerThanMaxBound_OK() {
        //Given
        Integer ruleIncrementMinValue = 10;
        Integer ruleIncrementMaxValue = 30;
        Integer minBound = 20;
        Integer maxBound = 50;
        FlatDescriptionLengthRule rule = new FlatDescriptionLengthRule(ruleIncrementMinValue, ruleIncrementMaxValue, minBound, maxBound);
        Advertisement ad = Advertisement.builder()
                .typology(AdvertisementTypology.FLAT)
                .description("tristique risus nec feugiat in fermentum posuere urna nec tincidunt praesent semper feugiat nibh sed pulvinar proin gravida hendrerit lectus")
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(30, result.getScore().getCurrent(), 0.01);
    }

    @Test
    public void executeRule_descriptionBetweenBound_OK() {
        //Given
        Integer ruleIncrementMinValue = 10;
        Integer ruleIncrementMaxValue = 30;
        Integer minBound = 20;
        Integer maxBound = 50;
        FlatDescriptionLengthRule rule = new FlatDescriptionLengthRule(ruleIncrementMinValue, ruleIncrementMaxValue, minBound, maxBound);
        Advertisement ad = Advertisement.builder()
                .typology(AdvertisementTypology.FLAT)
                .description("Lorem ipsum dolor sit amet")
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(10, result.getScore().getCurrent(), 0.01);
    }

    @Test
    public void executeRule_descriptionLowerThanMinBound_omitted_OK() {
        //Given
        Integer ruleIncrementMinValue = 10;
        Integer ruleIncrementMaxValue = 30;
        Integer minBound = 20;
        Integer maxBound = 50;
        FlatDescriptionLengthRule rule = new FlatDescriptionLengthRule(ruleIncrementMinValue, ruleIncrementMaxValue, minBound, maxBound);
        Advertisement ad = Advertisement.builder()
                .typology(AdvertisementTypology.FLAT)
                .description("Lorem ipsum")
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad, result);
    }

    @Test
    public void executeRule_descriptionNull_omitted_OK() {
        //Given
        Integer ruleIncrementMinValue = 10;
        Integer ruleIncrementMaxValue = 30;
        Integer minBound = 20;
        Integer maxBound = 50;
        FlatDescriptionLengthRule rule = new FlatDescriptionLengthRule(ruleIncrementMinValue, ruleIncrementMaxValue, minBound, maxBound);
        Advertisement ad = Advertisement.builder()
                .typology(AdvertisementTypology.FLAT)
                .description(null)
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad, result);
    }
    @Test
    public void executeRule_notFlatTypology_omitted_OK() {
        //Given
        Integer ruleIncrementMinValue = 10;
        Integer ruleIncrementMaxValue = 30;
        Integer minBound = 20;
        Integer maxBound = 50;
        FlatDescriptionLengthRule rule = new FlatDescriptionLengthRule(ruleIncrementMinValue, ruleIncrementMaxValue, minBound, maxBound);
        Advertisement ad = Advertisement.builder()
                .typology(AdvertisementTypology.CHALET)
                .description("Lorem ipsum dolor sit amet")
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad, result);
    }

}
