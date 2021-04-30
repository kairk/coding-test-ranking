package com.idealista.ranking.service.score;

import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.service.score.rule.WordOccurrenceInDescriptionRule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WordOccurrenceInDescriptionRuleTest {

    @Test
    public void executeRule_OK() {
        //Given
        Integer incrementValue = 5;
        List<String> relevantWords = Arrays.asList("Luminoso", "Nuevo", "Céntrico", "Reformado", "Ático");
        WordOccurrenceInDescriptionRule rule = new WordOccurrenceInDescriptionRule(incrementValue, relevantWords);
        Advertisement ad = Advertisement.builder().description("Luminoso test Ático description Reformado").build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(15, result.getScore().getCurrent(), 0.01);
    }

    @Test
    public void executeRule_multipleTimesSameWord_OK() {
        //Given
        Integer incrementValue = 5;
        List<String> relevantWords = Arrays.asList("Luminoso", "Nuevo", "Céntrico", "Reformado", "Ático");
        WordOccurrenceInDescriptionRule rule = new WordOccurrenceInDescriptionRule(incrementValue, relevantWords);
        Advertisement ad = Advertisement.builder().description("Luminoso test Ático Ático description Reformado").build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(15, result.getScore().getCurrent(), 0.01);
    }

    @Test
    public void executeRule_noOccurrences_omitted_OK() {
        //Given
        Integer incrementValue = 5;
        List<String> relevantWords = Arrays.asList("Luminoso", "Nuevo", "Céntrico", "Reformado", "Ático");
        WordOccurrenceInDescriptionRule rule = new WordOccurrenceInDescriptionRule(incrementValue, relevantWords);
        Advertisement ad = Advertisement.builder().description("test description").build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad, result);
    }

    @Test
    public void executeRule_noWordList_omitted_OK() {
        //Given
        Integer incrementValue = 5;
        List<String> relevantWords = Collections.emptyList();
        WordOccurrenceInDescriptionRule rule = new WordOccurrenceInDescriptionRule(incrementValue, relevantWords);
        Advertisement ad = Advertisement.builder().description("test description").build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad, result);
    }
}
