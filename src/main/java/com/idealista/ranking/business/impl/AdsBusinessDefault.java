package com.idealista.ranking.business.impl;

import com.idealista.ranking.business.AdsBusiness;
import com.idealista.ranking.common.service.executor.RuleExecutor;
import com.idealista.ranking.exception.AdsServiceException;
import com.idealista.ranking.exception.GenericAdsException;
import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.enumeration.RuleType;
import com.idealista.ranking.service.AdsService;
import com.idealista.ranking.service.factory.RuleExecutorFactory;
import com.idealista.ranking.service.score.executor.ScoreRuleExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdsBusinessDefault implements AdsBusiness {
    private final RuleExecutorFactory ruleExecutorFactory;
    private final AdsService service;

    @Autowired
    public AdsBusinessDefault(RuleExecutorFactory ruleExecutorFactory, AdsService service) {
        this.ruleExecutorFactory = ruleExecutorFactory;
        this.service = service;
    }

    @Override
    public void calculateScore() {
        ScoreRuleExecutor executor;
        try {
            executor = getCastedScoreExecutor();
        } catch (AdsServiceException e) {
            log.error("There was an error obtaining the Rule executor, cancelling call...");
            throw new GenericAdsException("Error obtaining rule executor", e);
        }

        //This query could be huge, it should be done in batches
        Collection<Advertisement> allAds = service.getAllAds();

        List<Advertisement> updatedScoreAds = allAds.stream().map(ad -> ad.toBuilder()
                .score(executor.getResult(ad))
                .build()).collect(Collectors.toList());

        service.upsertAdvertisements(updatedScoreAds);
    }

    private ScoreRuleExecutor getCastedScoreExecutor() throws AdsServiceException {
        RuleExecutor<?, ?> ruleExecutor = ruleExecutorFactory.create(RuleType.SCORE);

        if (ruleExecutor instanceof ScoreRuleExecutor) {
            return (ScoreRuleExecutor) ruleExecutor;
        } else {
            throw new GenericAdsException("Unexpected executor type returned from factory: " + ruleExecutor.getClass());
        }
    }
}
