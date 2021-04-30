package com.idealista.ranking.model.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScoreTest {

    @Test
    public void add_OK() {
        //Given
        Integer increment = 10;
        Score score = Score.builder()
                .MAX(100)
                .MIN(0)
                .current(0)
                .build();
        //When
        Score result = score.add(increment);

        //Then
        assertEquals(score.getCurrent() + increment, result.getCurrent(), 0.001);
    }

    @Test
    public void add_maxExceeded_OK() {
        //Given
        Integer increment = 10;
        Score score = Score.builder()
                .MAX(100)
                .MIN(0)
                .current(99)
                .build();
        //When
        Score result = score.add(increment);

        //Then
        assertEquals(score.getMAX(), result.getCurrent(), 0.001);
    }

    @Test
    public void subtract_OK() {
        //Given
        Integer decrement = 10;
        Score score = Score.builder()
                .MAX(100)
                .MIN(0)
                .current(50)
                .build();
        //When
        Score result = score.subtract(decrement);

        //Then
        assertEquals(score.getCurrent() - decrement, result.getCurrent(), 0.001);
    }

    @Test
    public void subtract_minReceded_OK() {
        //Given
        Integer decrement = 10;
        Score score = Score.builder()
                .MAX(100)
                .MIN(0)
                .current(1)
                .build();
        //When
        Score result = score.subtract(decrement);

        //Then
        assertEquals(score.getMIN(), result.getCurrent(), 0.001);
    }


}