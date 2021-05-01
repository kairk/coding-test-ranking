package com.idealista.ranking.service.factory;

import com.idealista.ranking.common.service.factory.AbstractFactory;
import com.idealista.ranking.configuration.RuleConstraintConfig;
import com.idealista.ranking.configuration.RuleValueConfig;
import com.idealista.ranking.exception.AdsServiceException;
import com.idealista.ranking.model.service.enumeration.RuleType;
import com.idealista.ranking.service.score.rule.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Creates different rules configuration for score rules
 */
@Service
@Slf4j
public class ScoreRuleCollectionFactory implements AbstractFactory<Collection<BaseScoreRule>, RuleType> {
    private final RuleValueConfig ruleValues;
    private final RuleConstraintConfig ruleConstraints;

    @Autowired
    public ScoreRuleCollectionFactory(RuleValueConfig ruleValues, RuleConstraintConfig ruleConstraints) {
        this.ruleValues = ruleValues;
        this.ruleConstraints = ruleConstraints;
    }

    @Override
    public Collection<BaseScoreRule> create(RuleType selector) throws AdsServiceException {
        Collection<BaseScoreRule> result;

        if (selector.equals(RuleType.SCORE)) {
            result = getScoreRuleCollection();
        } else {
            throw new AdsServiceException("Couldn't create rule collection from selector: " + selector.name());
        }

        return result;
    }

    private List<BaseScoreRule> getScoreRuleCollection() {
        return Arrays.asList(
                new NoPictureRule(ruleValues.getNoPicture()),
                new EachPictureQualityRule(ruleValues.getHdPicture(), ruleValues.getDefaultQualityPicture()),
                new HasDescriptionRule(ruleValues.getHasDescription()),
                new FlatDescriptionLengthRule(ruleValues.getFlatDescriptionLowerBound(), ruleValues.getFlatDescriptionUpperBound(), ruleConstraints.getFlatDescriptionLowerBound(), ruleConstraints.getFlatDescriptionUpperBound()),
                new ChaletDescriptionLengthRule(ruleValues.getChaletDescriptionLowerBound(), ruleConstraints.getChaletDescriptionLowerBound()),
                new WordOccurrenceInDescriptionRule(ruleValues.getWordOccurence(), ruleConstraints.getWordOccurrence()),
                new FlatCompleteInfoRule(ruleValues.getCompleteInfo()),
                new ChaletCompleteInfoRule(ruleValues.getCompleteInfo()),
                new GarageCompleteInfoRule(ruleValues.getCompleteInfo())
        );
    }
}

