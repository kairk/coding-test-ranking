package com.idealista.ranking.service.score.rule;

import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Picture;
import com.idealista.ranking.model.service.enumeration.PictureQuality;
import com.idealista.ranking.service.score.rule.EachPictureQualityRule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class EachPictureQualityRuleTest {

    @Test
    public void executeRule_OK() {
        //Given
        Integer sdValue = 10;
        Integer hdValue = 20;
        EachPictureQualityRule rule = new EachPictureQualityRule(sdValue, hdValue);
        Advertisement ad = Advertisement.builder()
                .pictures(Arrays.asList(
                        Picture.builder().quality(PictureQuality.HD).build(),
                        Picture.builder().quality(PictureQuality.HD).build(),
                        Picture.builder().quality(PictureQuality.SD).build(),
                        Picture.builder().quality(PictureQuality.SD).build()
                ))
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(60, result.getScore().getCurrent(), 0.01);
    }

    @Test
    public void executeRule_unknownValue_OK() {
        //Given
        Integer defaultValue = 10;
        Integer hdValue = 20;
        EachPictureQualityRule rule = new EachPictureQualityRule(hdValue, defaultValue);
        Advertisement ad = Advertisement.builder()
                .pictures(Arrays.asList(
                        Picture.builder().quality(PictureQuality.HD).build(),
                        Picture.builder().quality(PictureQuality.HD).build(),
                        Picture.builder().quality(PictureQuality.SD).build(),
                        Picture.builder().quality(PictureQuality.SD).build(),
                        Picture.builder().quality(PictureQuality.UNKNOWN).build()
                ))
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(70, result.getScore().getCurrent(), 0.01);
    }

    @Test
    public void executeRule_whenNoPics_omitted_OK() {
        //Given
        Integer defaultValue = 10;
        Integer hdValue = 20;
        EachPictureQualityRule rule = new EachPictureQualityRule(defaultValue, hdValue);
        Advertisement ad = Advertisement.builder()
                .pictures(Collections.emptyList())
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad, result);
    }

    @Test
    public void executeRule_whenNullPics_omitted_OK() {
        //Given
        Integer defaultValue = 10;
        Integer hdValue = 20;
        EachPictureQualityRule rule = new EachPictureQualityRule(defaultValue, hdValue);
        Advertisement ad = Advertisement.builder()
                .pictures(null)
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad, result);
    }
}
