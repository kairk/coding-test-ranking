package com.idealista.ranking.service;

import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Picture;

import java.util.Collection;
import java.util.List;

public interface AdsService {
    /**
     * Gets all the Advertisements from persistence transformed into Service model
     */
    Collection<Advertisement> getAllAds();

    List<Picture> getPicturesIn(List<Integer> picturesIds);

    void upsertAdvertisements(List<Advertisement> advertisements);

    void upsertPictures(List<Picture> pictures);
}
