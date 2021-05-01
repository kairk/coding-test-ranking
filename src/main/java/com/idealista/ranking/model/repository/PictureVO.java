package com.idealista.ranking.model.repository;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class PictureVO {

    private Integer id;
    private String url;
    private String quality;
}
