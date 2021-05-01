package com.idealista.ranking.model.service;

import com.idealista.ranking.model.service.enumeration.PictureQuality;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Picture {

    private Integer id;
    private String url;
    @Builder.Default
    private PictureQuality quality = PictureQuality.UNKNOWN;

}
