package com.idealista.ranking.service.factory;

import com.idealista.ranking.common.service.executor.RuleExecutor;
import com.idealista.ranking.common.service.factory.AbstractFactory;
import com.idealista.ranking.exception.AdsServiceException;
import com.idealista.ranking.model.service.enumeration.RuleType;
import com.idealista.ranking.service.score.executor.ScoreRuleExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RuleExecutorFactory implements AbstractFactory<RuleExecutor<?, ?>, RuleType> {
    private final ScoreRuleCollectionFactory scoreRuleCollectionFactory;

    @Autowired
    public RuleExecutorFactory(ScoreRuleCollectionFactory scoreRuleCollectionFactory) {
        // For the sake of simplicity I've just created an ScoreRuleFactory,
        // if there were too much factories we could create a FactoryProvider that returns us a factory given some selector
        this.scoreRuleCollectionFactory = scoreRuleCollectionFactory;
    }

    @Override
    public RuleExecutor<?, ?> create(RuleType selector) throws AdsServiceException {
        RuleExecutor<?, ?> result;
        if (selector.equals(RuleType.SCORE)) {
            result = new ScoreRuleExecutor(scoreRuleCollectionFactory.create(selector));
        } else {
            throw new AdsServiceException("Couldn't create Executor from selector: " + selector.name());
        }

        return result;
    }
}
