package com.idealista.ranking.mapper;

import com.idealista.ranking.model.api.response.PublicAdResponse;
import com.idealista.ranking.model.repository.AdVO;
import com.idealista.ranking.model.repository.PictureVO;
import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Picture;
import com.idealista.ranking.model.service.Score;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface AdvertisementServiceMapper {

    @Mapping(target = "pictures", source = "pictures", qualifiedByName = "pictureToIntMapper")
    @Mapping(target = "score", source = "score", qualifiedByName = "scoreToIntMapper")
    AdVO adServiceToRepository(Advertisement ad);

    PictureVO pictureServiceToRepository(Picture pic);

    @Mapping(target = "pictureUrls", source = "pictures", qualifiedByName = "pictureToUrlMapper")
    PublicAdResponse adServiceToResponseMapper(Advertisement ad);

    @Named("pictureToIntMapper")
    default List<Integer> pictureToInt(List<Picture> pictures) {
        return pictures.stream().map(Picture::getId).collect(Collectors.toList());
    }

    @Named("pictureToUrlMapper")
    default List<String> pictureToUrl(List<Picture> pictures) {
        return pictures.stream().map(Picture::getUrl).collect(Collectors.toList());
    }

    @Named("scoreToIntMapper")
    default Integer scoreToInt(Score score) {
        return score.getCurrent();
    }

}
