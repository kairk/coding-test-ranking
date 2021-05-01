package com.idealista.ranking.common.service.rule;

/**
 * Base rule service interface, all rules must extend this service.
 *
 * @param <T> Rule target, rules will check and update this subject
 * @param <U> Updated result, this should be a property of the target T or the same type
 */
public abstract class BaseRule<T, U> {
    /**
     * Entry point for external services to call the rules, it should use protected methods to generate the result
     */
    public abstract T executeRule(T ruleSubject);

    /**
     * Specifies when a rule must be executed. By default a rule is always executed.
     */
    protected Boolean ruleApplies(T ruleSubject) {
        return true;
    }

    protected abstract U updateResult(T ruleSubject);

}
