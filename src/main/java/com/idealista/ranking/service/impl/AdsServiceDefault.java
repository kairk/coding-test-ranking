package com.idealista.ranking.service.impl;

import com.idealista.ranking.configuration.ScoreConfig;
import com.idealista.ranking.model.repository.AdVO;
import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Picture;
import com.idealista.ranking.repository.InMemoryPersistence;
import com.idealista.ranking.service.AdsService;
import com.idealista.ranking.service.mapper.AdsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdsServiceDefault implements AdsService {

    private final InMemoryPersistence repository;
    private final AdsMapper adsMapper;
    private final ScoreConfig scoreConfig;

    @Autowired
    public AdsServiceDefault(InMemoryPersistence repository, AdsMapper adsMapper, ScoreConfig scoreConfig) {
        this.repository = repository;
        this.adsMapper = adsMapper;
        this.scoreConfig = scoreConfig;
    }

    @Override
    public Collection<Advertisement> getAllAds() {
        List<AdVO> repositoryAds = repository.getAllAds();

        return repositoryAds.stream()
                .map(repoAd -> adsMapper.adRepositoryToService(repoAd).toBuilder()
                        .pictures(getPicturesIn(repoAd.getPictures()))
                        .score(adsMapper.scoreRepositoryToService(scoreConfig, repoAd.getScore()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<Picture> getPicturesIn(List<Integer> picturesIds) {
        return (picturesIds == null || picturesIds.isEmpty()) ? Collections.emptyList() :
                repository.getPicturesIn(picturesIds).stream()
                        .map(adsMapper::pictureRepositoryToService)
                        .collect(Collectors.toList());
    }
}
