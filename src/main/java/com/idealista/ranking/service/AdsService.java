package com.idealista.ranking.service;

import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Picture;
import com.idealista.ranking.service.score.executor.ScoreRuleExecutor;

import java.util.Collection;
import java.util.function.BiFunction;

public interface AdsService {
    /**
     * Gets all the Advertisements from persistence transformed into Service model
     */
    Collection<Advertisement> getAllAds();

    Collection<Advertisement> calculateScore(ScoreRuleExecutor scoreExecutor, Collection<Advertisement> ads);

    Collection<Picture> getPicturesIn(Collection<Integer> picturesIds);

    void upsertAdvertisements(Collection<Advertisement> advertisements);

    void upsertPictures(Collection<Picture> pictures);

    /**
     * Returns a collection of Advertisements filtered by applying the given function to the Ad filterScore
     *
     * @param filterFunction a function that compares the given filterScore to the Ad current score (filterFunction(currentScore, filterScore))
     */
    Collection<Advertisement> getAdsFilterByScore(Integer filterScore, BiFunction<Integer, Integer, Boolean> filterFunction);
}
