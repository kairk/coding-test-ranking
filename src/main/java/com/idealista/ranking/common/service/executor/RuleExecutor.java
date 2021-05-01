package com.idealista.ranking.common.service.executor;

import com.idealista.ranking.exception.AdsServiceException;
import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.common.service.rule.BaseRule;

import java.util.Collection;

/**
 * Execute a Collection of rules into an Advertisement and combines the result into a expected type T.
 */
public abstract class RuleExecutor<T, U extends BaseRule> {
    // Creating a final variable with the rules and injecting it in the exposed method
    // forces all extending classes to have a valid collection of rules
    private final Collection<U> RULES;

    public RuleExecutor(Collection<U> rules) throws AdsServiceException {
        if (rules == null || rules.isEmpty()) {
            throw new AdsServiceException("Rules for executor can't be null or empty");
        }

        RULES = rules;
    }

    /**
     * Executes all rules given in initialization and compose the result into the parametrized type
     */
    public final T getResult(Advertisement ad) {
        return executeRules(RULES, ad);
    }

    protected abstract T executeRules(Collection<U> rules, Advertisement ad);
}
