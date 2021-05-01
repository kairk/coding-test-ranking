package com.idealista.ranking.service.score.rule;

import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Picture;
import com.idealista.ranking.model.service.enumeration.AdvertisementTypology;
import com.idealista.ranking.service.score.rule.GarageCompleteInfoRule;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class GarageCompleteInfoRuleTest {

    @Test
    public void executeRule_OK() {
        //Given
        Integer incrementValue = 40;
        GarageCompleteInfoRule rule = new GarageCompleteInfoRule(incrementValue);
        Advertisement ad = Advertisement.builder()
                .typology(AdvertisementTypology.GARAGE)
                .pictures(Collections.singletonList(Picture.builder().build()))
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad.getScore().getCurrent() + incrementValue, result.getScore().getCurrent(), 0.01);
    }

    @Test
    public void executeRule_whenTypologyIsntGarage_omitted_OK() {
        //Given
        Integer incrementValue = 40;
        GarageCompleteInfoRule rule = new GarageCompleteInfoRule(incrementValue);
        Advertisement ad = Advertisement.builder()
                .typology(AdvertisementTypology.FLAT)
                .pictures(Collections.singletonList(Picture.builder().build()))
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
        GarageCompleteInfoRule rule = new GarageCompleteInfoRule(incrementValue);
        Advertisement ad = Advertisement.builder()
                .typology(AdvertisementTypology.GARAGE)
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad, result);
    }

}
