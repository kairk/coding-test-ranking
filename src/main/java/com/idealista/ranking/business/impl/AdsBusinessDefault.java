package com.idealista.ranking.business.impl;

import com.idealista.ranking.business.AdsBusiness;
import com.idealista.ranking.common.service.executor.RuleExecutor;
import com.idealista.ranking.configuration.GenericConfig;
import com.idealista.ranking.exception.AdsNoContentException;
import com.idealista.ranking.exception.AdsServiceException;
import com.idealista.ranking.exception.GenericAdsException;
import com.idealista.ranking.mapper.AdvertisementServiceMapper;
import com.idealista.ranking.model.api.response.PublicAdResponse;
import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.enumeration.RuleType;
import com.idealista.ranking.service.AdsService;
import com.idealista.ranking.service.factory.RuleExecutorFactory;
import com.idealista.ranking.service.score.executor.ScoreRuleExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdsBusinessDefault implements AdsBusiness {
    private final RuleExecutorFactory ruleExecutorFactory;
    private final AdsService adsService;
    private final AdvertisementServiceMapper mapper;
    private final GenericConfig config;

    @Autowired
    public AdsBusinessDefault(GenericConfig config, RuleExecutorFactory ruleExecutorFactory, AdsService adsService, AdvertisementServiceMapper mapper) {
        this.config = config;
        this.ruleExecutorFactory = ruleExecutorFactory;
        this.adsService = adsService;
        this.mapper = mapper;
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
        Collection<Advertisement> allAds = adsService.getAllAds();

        List<Advertisement> updatedScoreAds = allAds.stream().map(ad -> ad.toBuilder()
                .score(executor.getResult(ad))
                .build()).collect(Collectors.toList());

        adsService.upsertAdvertisements(updatedScoreAds);
    }

    @Override
    /**
     * Returns a paginated list of Ads filtered and ordered by its quality score.
     * If the page * size is bigger than the actual list size it will return page 0
     */
    public List<PublicAdResponse> getPublicListing(int page, int size) {
        List<Advertisement> serviceAds = new ArrayList<>(adsService.getAdsFilterByScore(config.getMinScorePublicAds()));


        //By default PagedListHolder returns to page 0 when page * size is bigger than the actual list size.
        PagedListHolder<Advertisement> adsPage = new PagedListHolder<>(serviceAds);

        adsPage.setSort(new MutableSortDefinition("score.current", true, false));
        //PagedListHolder does not sort source on initialization
        adsPage.resort();

        adsPage.setPage(page);
        adsPage.setPageSize(size);


        List<PublicAdResponse> mappedList = adsPage.getPageList().stream().map(mapper::adServiceToResponseMapper)
                .collect(Collectors.toList());

        if (mappedList.isEmpty()) {
            throw new AdsNoContentException("There are no public Ads matching criteria");
        }

        return mappedList;
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
