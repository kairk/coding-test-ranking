package com.idealista.ranking.service.impl;

import com.idealista.ranking.configuration.ScoreConfig;
import com.idealista.ranking.model.repository.AdVO;
import com.idealista.ranking.model.repository.PictureVO;
import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Picture;
import com.idealista.ranking.model.service.Score;
import com.idealista.ranking.model.service.enumeration.AdvertisementTypology;
import com.idealista.ranking.repository.InMemoryPersistence;
import com.idealista.ranking.service.mapper.AdvertisementMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdsServiceDefaultTest {

    @Mock
    InMemoryPersistence repository;

    @Spy
    AdvertisementMapper advertisementMapper = Mappers.getMapper(AdvertisementMapper.class);

    ScoreConfig scoreConfig = ScoreConfig.builder().max(100).min(0).build();

    @Test
    public void getAllAds_parseProperties_ok() {
        //Given
        List<AdVO> repositoryAds = Collections.singletonList(
                AdVO.builder().id(1).typology("CHALET").description("test").gardenSize(5).houseSize(5).irrelevantSince(new Date()).score(50).pictures(new ArrayList<>())
                        .build());
        List<Advertisement> expected = Collections.singletonList(
                Advertisement.builder()
                        .score(Score.builder().current(50).max(scoreConfig.getMax()).min(scoreConfig.getMin()).build())
                        .pictures(new ArrayList<>()).id(1).houseSize(5).gardenSize(5).description("test").typology(AdvertisementTypology.CHALET).irrelevantSince(new Date())
                        .build()
        );

        when(repository.getAllAds()).thenReturn(repositoryAds);

        AdsServiceDefault adsService = new AdsServiceDefault(repository, advertisementMapper, scoreConfig);

        //When
        Collection<Advertisement> result = adsService.getAllAds();

        //Then
        assertEquals(expected, result);
    }

    @Test
    public void getAllAds_nullValues_ok() {
        //Given
        List<AdVO> repositoryAds = getNullPropertiesRepositoryAds();

        when(repository.getAllAds()).thenReturn(repositoryAds);

        AdsServiceDefault adsService = new AdsServiceDefault(repository, advertisementMapper, scoreConfig);

        //When
        Collection<Advertisement> result = adsService.getAllAds();

        //Then
        verify(advertisementMapper, times(repositoryAds.size())).adRepositoryToService(any(AdVO.class));
        assertTrue(result.stream().allMatch(ad ->
                ad.getScore().getMax().equals(scoreConfig.getMax()) && ad.getScore().getMin().equals(scoreConfig.getMin())));
    }

    @Test
    public void getPicturesIn_parseValues_ok() {
        //Given
        List<PictureVO> repositoryPics = getRepositoryPics();
        List<Integer> ids = Arrays.asList(1, 2, 3);
        when(repository.getPicturesIn(ids)).thenReturn(repositoryPics);

        AdsServiceDefault adsService = new AdsServiceDefault(repository, advertisementMapper, scoreConfig);

        //When
        adsService.getPicturesIn(ids);

        //Then
        verify(advertisementMapper, times(repositoryPics.size())).pictureRepositoryToService(any(PictureVO.class));
        verify(advertisementMapper, times(repositoryPics.size())).qualityRepositoryToService(any(String.class));

    }

    @Test
    public void getPicturesIn_emptyList_ok() {
        //Given
        List<Integer> ids = new ArrayList<>();

        AdsServiceDefault adsService = new AdsServiceDefault(repository, advertisementMapper, scoreConfig);

        //When
        adsService.getPicturesIn(ids);

        //Then
        verify(advertisementMapper, never()).pictureRepositoryToService(any(PictureVO.class));
        verify(advertisementMapper, never()).qualityRepositoryToService(any(String.class));

    }

    @Test
    public void upsertAdvertisement_ok() {
        //Given
        List<Advertisement> advertisements = Arrays.asList(
                Advertisement.builder().id(1).build(),
                Advertisement.builder().id(2).build(),
                Advertisement.builder().id(3).build()
        );

        AdsServiceDefault adsService = new AdsServiceDefault(repository, advertisementMapper, scoreConfig);

        //When
        adsService.upsertAdvertisements(advertisements);

        //Then
        verify(advertisementMapper, times(advertisements.size())).adServiceToRepository(any(Advertisement.class));
        verify(repository, times(advertisements.size())).upsertAd(any(AdVO.class));

    }

    @Test
    public void upsertPicture_ok() {
        //Given
        List<Picture> pictures = Arrays.asList(
                Picture.builder().id(1).build(),
                Picture.builder().id(2).build(),
                Picture.builder().id(3).build()
        );

        AdsServiceDefault adsService = new AdsServiceDefault(repository, advertisementMapper, scoreConfig);

        //When
        adsService.upsertPictures(pictures);

        //Then
        verify(advertisementMapper, times(pictures.size())).pictureServiceToRepository(any(Picture.class));
        verify(repository, times(pictures.size())).upsertPicture(any(PictureVO.class));

    }


    private List<PictureVO> getRepositoryPics() {
        return Arrays.asList(
                PictureVO.builder().id(1).quality("HD").url("test").build(),
                PictureVO.builder().id(2).quality("SD").url("test").build(),
                PictureVO.builder().id(3).quality("ERROR").url("test").build()
        );
    }

    private List<AdVO> getNullPropertiesRepositoryAds() {
        return Arrays.asList(
                AdVO.builder().id(1).build(),
                AdVO.builder().id(2).build(),
                AdVO.builder().id(3).build()
        );
    }
}