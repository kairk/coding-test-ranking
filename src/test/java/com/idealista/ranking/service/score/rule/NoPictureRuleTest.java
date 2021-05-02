package com.idealista.ranking.service.score.rule;

import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Picture;
import com.idealista.ranking.model.service.Score;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class NoPictureRuleTest {

    @Test
    public void executeRule_OK() {
        //Given
        Integer decrementValue = 10;
        NoPictureRule rule = new NoPictureRule(decrementValue);
        Advertisement ad = Advertisement.builder().score(Score.builder().current(10).build()).build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad.getScore().getCurrent() - decrementValue, result.getScore().getCurrent(), 0.01);
    }

    @Test

    public void executeRule_whenThereArePictures_dontExecute_OK() {
        //Given
        Integer decrementValue = 10;
        NoPictureRule rule = new NoPictureRule(decrementValue);
        Advertisement ad = Advertisement.builder()
                .pictures(Collections.singletonList(Picture.builder().build()))
                .build();

        //When
        Advertisement result = rule.executeRule(ad);

        //Then
        assertEquals(ad, result);
    }

}
