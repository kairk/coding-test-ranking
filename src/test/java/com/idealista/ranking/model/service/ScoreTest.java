package com.idealista.ranking.model.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScoreTest {

    @Test
    public void add_Integer_OK() {
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
    public void add_Score_OK() {
        //Given
        Integer increment = 10;
        Score score = Score.builder()
                .MAX(100)
                .MIN(0)
                .current(0)
                .build();
        //When
        Score result = score.add(Score.builder().current(increment).build());

        //Then
        assertEquals(score.getCurrent() + increment, result.getCurrent(), 0.001);
    }

    @Test
    public void add_integerMaxExceeded_OK() {
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
    public void add_scoreMaxExceeded_OK() {
        //Given
        Integer increment = 10;
        Score score = Score.builder()
                .MAX(100)
                .MIN(0)
                .current(99)
                .build();
        //When
        Score result = score.add(Score.builder().current(increment).build());

        //Then
        assertEquals(score.getMAX(), result.getCurrent(), 0.001);
    }

    @Test
    public void subtract_integer_OK() {
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
    public void subtract_score_OK() {
        //Given
        Integer decrement = 10;
        Score score = Score.builder()
                .MAX(100)
                .MIN(0)
                .current(50)
                .build();
        //When
        Score result = score.subtract(Score.builder().current(decrement).build());

        //Then
        assertEquals(score.getCurrent() - decrement, result.getCurrent(), 0.001);
    }

    @Test
    public void subtract_integerMinReceded_OK() {
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

    @Test
    public void subtract_scoreMinReceded_OK() {
        //Given
        Integer decrement = 10;
        Score score = Score.builder()
                .MAX(100)
                .MIN(0)
                .current(1)
                .build();
        //When
        Score result = score.subtract(Score.builder().current(decrement).build());

        //Then
        assertEquals(score.getMIN(), result.getCurrent(), 0.001);
    }


}