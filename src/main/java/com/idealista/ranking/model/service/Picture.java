package com.idealista.ranking.model.service;

import com.idealista.ranking.model.service.enumeration.PictureQuality;
import lombok.*;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder(toBuilder = true)
public class Picture {

    private Integer id;
    private String url;
    @Builder.Default
    private final PictureQuality quality = PictureQuality.UNKNOWN;

}
