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
public class ScoreConfig {
    private static final String CONFIG_ROOT = "service.score.";

    @Value("${" + CONFIG_ROOT + "max:100}")
    private Integer max;
    @Value("${" + CONFIG_ROOT + "min:0}")
    private Integer min;
}
