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

@Mapper
public interface AdsMapper {

    @Mapping(target = "pictures", ignore = true)
    @Mapping(target = "score", ignore = true)
    @Mapping(source = "typology", target = "typology", qualifiedByName = "typologyMapper")
    Advertisement adRepositoryToService(AdVO data);

    @Mapping(source = "quality", target = "quality", qualifiedByName = "qualityMapper")
    Picture pictureRepositoryToService(PictureVO data);


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
}
