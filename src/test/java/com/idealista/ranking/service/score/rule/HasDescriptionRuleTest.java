package com.idealista.ranking.service.score.rule;

import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.service.score.rule.HasDescriptionRule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HasDescriptionRuleTest {

    @Test
    public void executeRule_OK() {
        //Given
        Integer incrementValue = 5;
        HasDescriptionRule rule = new HasDescriptionRule(incrementValue);
        Advertisement ad = Advertisement.builder().description("test description").build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad.getScore().getCurrent() + incrementValue, result.getScore().getCurrent(), 0.01);
    }

    @Test
    public void executeRule_whenEmptyDesc_omitted_OK() {
        //Given
        Integer incrementValue = 5;
        HasDescriptionRule rule = new HasDescriptionRule(incrementValue);
        Advertisement ad = Advertisement.builder().description("").build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad, result);
    }

    @Test
    public void executeRule_whenNullDesc_omitted_OK() {
        //Given
        Integer incrementValue = 5;
        HasDescriptionRule rule = new HasDescriptionRule(incrementValue);
        Advertisement ad = Advertisement.builder().description(null).build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad, result);
    }
}
