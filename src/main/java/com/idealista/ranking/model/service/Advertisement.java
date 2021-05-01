package com.idealista.ranking.model.service;

import com.idealista.ranking.model.service.enumeration.AdvertisementTypology;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Advertisement {

    private Integer id;
    @Builder.Default
    private AdvertisementTypology typology = AdvertisementTypology.UNKNOWN;
    private String description;
    @Builder.Default
    private List<Picture> pictures = new ArrayList<>();
    private Integer houseSize;
    private Integer gardenSize;
    @Builder.Default
    private Score score = Score.builder().build();
    private Date irrelevantSince;
}
