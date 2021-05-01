package com.idealista.ranking.service.score.rule;

import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Picture;
import com.idealista.ranking.model.service.enumeration.AdvertisementTypology;
import com.idealista.ranking.service.score.rule.ChaletCompleteInfoRule;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class ChaletCompleteInfoRuleTest {

    @Test
    public void executeRule_OK() {
        //Given
        Integer incrementValue = 40;
        ChaletCompleteInfoRule rule = new ChaletCompleteInfoRule(incrementValue);
        Advertisement ad = Advertisement.builder()
                .typology(AdvertisementTypology.CHALET)
                .description("test desc")
                .pictures(Collections.singletonList(Picture.builder().build()))
                .houseSize(5)
                .gardenSize(5)
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad.getScore().getCurrent() + incrementValue, result.getScore().getCurrent(), 0.01);
    }

    @Test
    public void executeRule_whenTypologyIsntChalet_omitted_OK() {
        //Given
        Integer incrementValue = 40;
        ChaletCompleteInfoRule rule = new ChaletCompleteInfoRule(incrementValue);
        Advertisement ad = Advertisement.builder()
                .typology(AdvertisementTypology.FLAT)
                .description("test desc")
                .pictures(Collections.singletonList(Picture.builder().build()))
                .houseSize(5)
                .gardenSize(5)
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad, result);
    }

    @Test
    public void executeRule_whenMissingDesc_omitted_OK() {
        //Given
        Integer incrementValue = 40;
        ChaletCompleteInfoRule rule = new ChaletCompleteInfoRule(incrementValue);
        Advertisement ad = Advertisement.builder()
                .typology(AdvertisementTypology.CHALET)
                .pictures(Collections.singletonList(Picture.builder().build()))
                .houseSize(5)
                .gardenSize(5)
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad, result);
    }

    @Test
    public void executeRule_whenMissingPictures_omitted_OK() {
        //Given
        Integer incrementValue = 40;
        ChaletCompleteInfoRule rule = new ChaletCompleteInfoRule(incrementValue);
        Advertisement ad = Advertisement.builder()
                .typology(AdvertisementTypology.CHALET)
                .description("test description")
                .houseSize(5)
                .gardenSize(5)
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad, result);
    }

    @Test
    public void executeRule_whenMissingHouseSize_omitted_OK() {
        //Given
        Integer incrementValue = 40;
        ChaletCompleteInfoRule rule = new ChaletCompleteInfoRule(incrementValue);
        Advertisement ad = Advertisement.builder()
                .typology(AdvertisementTypology.CHALET)
                .description("test description")
                .pictures(Collections.singletonList(Picture.builder().build()))
                .gardenSize(5)
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad, result);
    }

    @Test
    public void executeRule_whenMissingGardenSize_omitted_OK() {
        //Given
        Integer incrementValue = 40;
        ChaletCompleteInfoRule rule = new ChaletCompleteInfoRule(incrementValue);
        Advertisement ad = Advertisement.builder()
                .typology(AdvertisementTypology.CHALET)
                .description("test description")
                .pictures(Collections.singletonList(Picture.builder().build()))
                .houseSize(5)
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad, result);
    }
}
