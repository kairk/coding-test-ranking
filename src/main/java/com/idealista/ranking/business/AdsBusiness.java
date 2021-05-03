package com.idealista.ranking.business;

import com.idealista.ranking.model.api.response.PublicAdResponse;
import com.idealista.ranking.model.api.response.QualityAdResponse;

import java.util.List;

public interface AdsBusiness {
    void calculateScore();

    List<PublicAdResponse> getPublicListing(int page, int size);

    List<QualityAdResponse> getQualityListing(int page, int size);
}
