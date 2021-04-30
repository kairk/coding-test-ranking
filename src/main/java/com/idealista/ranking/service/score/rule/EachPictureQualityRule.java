package com.idealista.ranking.service.score.rule;

import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.enumeration.PictureQuality;
import com.idealista.ranking.model.service.Score;

/**
 * Each HD picture adds X points, each SD picture adds Y points
 */
public class EachPictureQualityRule extends BaseScoreRule {
    private final Integer HD_PICTURE_VALUE;
    private final Integer DEFAULT_PICTURE_VALUE;

    public EachPictureQualityRule(Integer hdPictureValue, Integer defaultPictureValue) {
        this.HD_PICTURE_VALUE = hdPictureValue;
        this.DEFAULT_PICTURE_VALUE = defaultPictureValue;
    }

    @Override
    protected Boolean ruleApplies(Advertisement ad) {
        return ad.getPictures() != null && !ad.getPictures().isEmpty();
    }

    @Override
    protected Score updateScore(Advertisement ad) {
        int totalPicValue = ad.getPictures().stream().mapToInt(pic -> {
            Integer result;

            if (pic.getQuality() == PictureQuality.HD) {
                result = HD_PICTURE_VALUE;
            } else {
                result = DEFAULT_PICTURE_VALUE;
            }

            return result;
        }).sum();

        return ad.getScore().add(totalPicValue);
    }
}
