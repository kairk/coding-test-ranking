package com.idealista.ranking.service.score.rule;

import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.enumeration.AdvertisementTypology;
import com.idealista.ranking.service.score.rule.ChaletDescriptionLengthRule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChaletDescriptionLengthRuleTest {

    @Test
    public void executeRule_descriptionBiggerThanMinBound_OK() {
        //Given
        Integer ruleIncrementMinValue = 20;
        Integer minBound = 50;
        ChaletDescriptionLengthRule rule = new ChaletDescriptionLengthRule(ruleIncrementMinValue, minBound);
        Advertisement ad = Advertisement.builder()
                .typology(AdvertisementTypology.CHALET)
                .description("tristique risus nec feugiat in fermentum posuere urna nec tincidunt praesent semper feugiat nibh sed pulvinar proin gravida hendrerit lectus")
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad.getScore().getCurrent() + ruleIncrementMinValue, result.getScore().getCurrent(), 0.01);
    }

    @Test
    public void executeRule_descriptionLowerThanMinBound_omitted_OK() {
        //Given
        Integer ruleIncrementMinValue = 20;
        Integer minBound = 50;
        ChaletDescriptionLengthRule rule = new ChaletDescriptionLengthRule(ruleIncrementMinValue, minBound);
        Advertisement ad = Advertisement.builder()
                .typology(AdvertisementTypology.CHALET)
                .description("tristique risus nec feugiat")
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad, result);
    }

    @Test
    public void executeRule_typologyNotChalet_omitted_OK() {
        //Given
        Integer ruleIncrementMinValue = 20;
        Integer minBound = 50;
        ChaletDescriptionLengthRule rule = new ChaletDescriptionLengthRule(ruleIncrementMinValue, minBound);
        Advertisement ad = Advertisement.builder()
                .typology(AdvertisementTypology.FLAT)
                .description("tristique risus nec feugiat in fermentum posuere urna nec tincidunt praesent semper feugiat nibh sed pulvinar proin gravida hendrerit lectus")
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad, result);
    }

}

