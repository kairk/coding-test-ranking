package com.idealista.ranking.service.score;

import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Score;
import com.idealista.ranking.service.score.rule.BaseScoreRule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BaseScoreRuleTest {

    @Test
    public void executeRule_OK() {
        //Given
        TestScoreRule testRule = new TestScoreRule();
        Advertisement testAd = Advertisement.builder()
                .score(Score.builder()
                        .current(10)
                        .build())
                .description("don't skip")
                .build();

        //When
        Advertisement result = testRule.executeRule(testAd);

        //Then

        assertNotEquals(testAd, result);
        assertEquals(testAd.getScore().getCurrent() + 10, result.getScore().getCurrent(), 0.001);
    }

    @Test
    public void executeRule_WhenRuleDontApply_dontExecute_OK() {
        //Given
        TestScoreRule testRule = new TestScoreRule();
        Advertisement testAd = Advertisement.builder()
                .score(Score.builder()
                        .current(10)
                        .build())
                .description("skip")
                .build();

        //When
        Advertisement result = testRule.executeRule(testAd);

        //Then
        assertEquals(testAd, result);
    }

    private class TestScoreRule extends BaseScoreRule {

        @Override
        protected Boolean ruleApplies(Advertisement ad) {
            return !ad.getDescription().equals("skip");
        }

        @Override
        protected Score updateScore(Advertisement ad) {
            return Score.builder().current(ad.getScore().getCurrent() + 10).build();
        }
    }
}
