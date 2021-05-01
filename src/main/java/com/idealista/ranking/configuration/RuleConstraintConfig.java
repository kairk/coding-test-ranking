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
public class RuleConstraintConfig {
    private static final String CONFIG_ROOT = "service.rule.constraint.";

    @Value("${" + CONFIG_ROOT + "flatDescriptionLowerBound:20}")
    private Integer flatDescriptionLowerBound;
    @Value("${" + CONFIG_ROOT + "flatDescriptionUpperBound:50}")
    private Integer flatDescriptionUpperBound;
    @Value("${" + CONFIG_ROOT + "chaletDescriptionLowerBound:50}")
    private Integer chaletDescriptionLowerBound;
    @Value("${" + CONFIG_ROOT + "wordOccurrence}")
    private List<String> wordOccurrence;
}
