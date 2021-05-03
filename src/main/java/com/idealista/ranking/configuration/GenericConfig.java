package com.idealista.ranking.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericConfig {
    private static final String CONFIG_ROOT = "service.";

    @Value("${" + CONFIG_ROOT + "minScorePublicAds:40}")
    private Integer minScorePublicAds;
}
