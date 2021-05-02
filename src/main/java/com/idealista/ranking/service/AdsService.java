package com.idealista.ranking.service;

import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Picture;

import java.util.Collection;

public interface AdsService {
    /**
     * Gets all the Advertisements from persistence transformed into Service model
     */
    Collection<Advertisement> getAllAds();

    Collection<Picture> getPicturesIn(Collection<Integer> picturesIds);

    void upsertAdvertisements(Collection<Advertisement> advertisements);

    void upsertPictures(Collection<Picture> pictures);

    Collection<Advertisement> getAdsFilterByScore(Integer minScore);
}
