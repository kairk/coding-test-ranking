package com.idealista.ranking.business;

import com.idealista.ranking.model.api.response.PublicAdResponse;

import java.util.List;

public interface AdsBusiness {
    void calculateScore();

    List<PublicAdResponse> getPublicListing(int page, int size);
}
