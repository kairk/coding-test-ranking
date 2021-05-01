package com.idealista.ranking.service.mapper;

import com.idealista.ranking.configuration.ScoreConfig;
import com.idealista.ranking.model.repository.AdVO;
import com.idealista.ranking.model.repository.PictureVO;
import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Picture;
import com.idealista.ranking.model.service.Score;
import com.idealista.ranking.model.service.enumeration.AdvertisementTypology;
import com.idealista.ranking.model.service.enumeration.PictureQuality;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface AdvertisementMapper {

    @Mapping(target = "pictures", ignore = true)
    @Mapping(target = "score", ignore = true)
    @Mapping(source = "typology", target = "typology", qualifiedByName = "typologyMapper")
    Advertisement adRepositoryToService(AdVO data);

    @Mapping(source = "quality", target = "quality", qualifiedByName = "qualityMapper")
    Picture pictureRepositoryToService(PictureVO data);

    @Mapping(target = "pictures", source = "pictures", qualifiedByName = "pictureToIntMapper")
    @Mapping(target = "score", source = "score", qualifiedByName = "scoreToIntMapper")
    AdVO adServiceToRepository(Advertisement ad);

    PictureVO pictureServiceToRepository(Picture pic);

    default Score scoreRepositoryToService(ScoreConfig scoreConfig, Integer currentScore) {
        return Score.builder()
                .max(scoreConfig.getMax())
                .min(scoreConfig.getMin())
                .current(currentScore)
                .build();
    }

    @Named("typologyMapper")
    default AdvertisementTypology typologyRepositoryToService(String typology) {
        return AdvertisementTypology.fromString(typology).orElse(AdvertisementTypology.UNKNOWN);
    }

    @Named("qualityMapper")
    default PictureQuality qualityRepositoryToService(String quality) {
        return PictureQuality.fromString(quality).orElse(PictureQuality.UNKNOWN);

    }

    @Named("pictureToIntMapper")
    default List<Integer> pictureToInt(List<Picture> pictures) {
        return pictures.stream().map(Picture::getId).collect(Collectors.toList());
    }

    @Named("scoreToIntMapper")
    default Integer scoreToInt(Score score) {
        return score.getCurrent();
    }
}
