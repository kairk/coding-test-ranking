package com.idealista.ranking.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleValueConfig {
    private static final String CONFIG_ROOT = "service.rule.value.";

    @Value("${" + CONFIG_ROOT + "noPicture:10}")
    private Integer noPicture;
    @Value("${" + CONFIG_ROOT + "hdPicture:20}")
    private Integer hdPicture;
    @Value("${" + CONFIG_ROOT + "defaultQualityPicture:10}")
    private Integer defaultQualityPicture;
    @Value("${" + CONFIG_ROOT + "hasDescription:5}")
    private Integer hasDescription;
    @Value("${" + CONFIG_ROOT + "flatDescriptionLowerBound:10}")
    private Integer flatDescriptionLowerBound;
    @Value("${" + CONFIG_ROOT + "flatDescriptionUpperBound:30}")
    private Integer flatDescriptionUpperBound;
    @Value("${" + CONFIG_ROOT + "chaletDescriptionLowerBound:20}")
    private Integer chaletDescriptionLowerBound;
    @Value("${" + CONFIG_ROOT + "wordOccurence:5}")
    private Integer wordOccurence;
    @Value("${" + CONFIG_ROOT + "completeInfo:40}")
    private Integer completeInfo;
}
