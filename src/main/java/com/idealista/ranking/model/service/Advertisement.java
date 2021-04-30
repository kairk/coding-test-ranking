package com.idealista.ranking.model.service;

import com.idealista.ranking.model.service.enumeration.AdvertisementTypology;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder(toBuilder = true)
public class Advertisement {

    private Integer id;
    @Builder.Default
    private final AdvertisementTypology typology = AdvertisementTypology.UNKNOWN;
    private String description;
    @Builder.Default
    private final List<Picture> pictures = new ArrayList<>();
    private Integer houseSize;
    private Integer gardenSize;
    @Builder.Default
    private final Score score = Score.builder().build();
    private Date irrelevantSince;
}
