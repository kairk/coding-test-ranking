package com.idealista.ranking.service.impl;

import com.idealista.ranking.configuration.ScoreConfig;
import com.idealista.ranking.mapper.AdvertisementRepositoryMapper;
import com.idealista.ranking.mapper.AdvertisementServiceMapper;
import com.idealista.ranking.model.repository.AdVO;
import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Picture;
import com.idealista.ranking.repository.InMemoryPersistence;
import com.idealista.ranking.service.AdsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
//TODO: Picture and Ads should be managed by different services
public class AdsServiceDefault implements AdsService {

    private final InMemoryPersistence repository;
    private final AdvertisementServiceMapper serviceMapper;
    private final AdvertisementRepositoryMapper repositoryMapper;
    private final ScoreConfig scoreConfig;

    @Autowired
    public AdsServiceDefault(InMemoryPersistence repository, AdvertisementServiceMapper serviceMapper, AdvertisementRepositoryMapper repoMapper, ScoreConfig scoreConfig) {
        this.repository = repository;
        this.serviceMapper = serviceMapper;
        this.repositoryMapper = repoMapper;
        this.scoreConfig = scoreConfig;
    }

    @Override
    public Collection<Advertisement> getAllAds() {
        List<AdVO> repositoryAds = repository.getAllAds();

        return repositoryAds.stream()
                .map(repoAd -> repositoryMapper.adRepositoryToService(repoAd).toBuilder()
                        .pictures(new ArrayList<>(getPicturesIn(repoAd.getPictures())))
                        .score(repositoryMapper.scoreRepositoryToService(scoreConfig, repoAd.getScore()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Picture> getPicturesIn(Collection<Integer> picturesIds) {
        return (picturesIds == null || picturesIds.isEmpty()) ? Collections.emptyList() :
                repository.getPicturesIn(new ArrayList<>(picturesIds)).stream()
                        .map(repositoryMapper::pictureRepositoryToService)
                        .collect(Collectors.toList());
    }

    @Override
    public void upsertAdvertisements(Collection<Advertisement> advertisements) {
        advertisements.stream()
                .map(ad -> {
                    upsertPictures(ad.getPictures());
                    return serviceMapper.adServiceToRepository(ad);
                })
                .forEach(repository::upsertAd);
    }

    @Override
    public void upsertPictures(Collection<Picture> pictures) {
        pictures.stream()
                .map(serviceMapper::pictureServiceToRepository)
                .forEach(repository::upsertPicture);
    }

    @Override
    public Collection<Advertisement> getAdsFilterByScore(Integer minScore) {
        //Filtering by a property should by a query to the repository
        return getAllAds().stream()
                .filter(ad -> ad.getScore().getCurrent() >= minScore)
                .collect(Collectors.toList());
    }
}
