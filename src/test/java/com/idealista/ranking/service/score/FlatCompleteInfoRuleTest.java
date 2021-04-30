package com.idealista.ranking.service.score;

import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Picture;
import com.idealista.ranking.model.service.enumeration.AdvertisementTypology;
import com.idealista.ranking.service.score.rule.FlatCompleteInfoRule;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class FlatCompleteInfoRuleTest {

    @Test
    public void executeRule_OK() {
        //Given
        Integer incrementValue = 40;
        FlatCompleteInfoRule rule = new FlatCompleteInfoRule(incrementValue);
        Advertisement ad = Advertisement.builder()
                .typology(AdvertisementTypology.FLAT)
                .description("test desc")
                .pictures(Collections.singletonList(Picture.builder().build()))
                .houseSize(5)
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad.getScore().getCurrent() + incrementValue, result.getScore().getCurrent(), 0.01);
    }

    @Test
    public void executeRule_whenTypologyIsntFlat_omitted_OK() {
        //Given
        Integer incrementValue = 40;
        FlatCompleteInfoRule rule = new FlatCompleteInfoRule(incrementValue);
        Advertisement ad = Advertisement.builder()
                .typology(AdvertisementTypology.CHALET)
                .description("test desc")
                .pictures(Collections.singletonList(Picture.builder().build()))
                .houseSize(5)
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
        FlatCompleteInfoRule rule = new FlatCompleteInfoRule(incrementValue);
        Advertisement ad = Advertisement.builder()
                .typology(AdvertisementTypology.FLAT)
                .pictures(Collections.singletonList(Picture.builder().build()))
                .houseSize(5)
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
        FlatCompleteInfoRule rule = new FlatCompleteInfoRule(incrementValue);
        Advertisement ad = Advertisement.builder()
                .typology(AdvertisementTypology.FLAT)
                .description("test description")
                .houseSize(5)
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
        FlatCompleteInfoRule rule = new FlatCompleteInfoRule(incrementValue);
        Advertisement ad = Advertisement.builder()
                .typology(AdvertisementTypology.FLAT)
                .description("test description")
                .pictures(Collections.singletonList(Picture.builder().build()))
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad, result);
    }
}
