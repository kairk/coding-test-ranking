package com.idealista.ranking.service.score.rule;

import com.idealista.ranking.model.service.Advertisement;
import com.idealista.ranking.model.service.Score;

import java.util.List;

/**
 * Adds X point per occurrence of relevant words. Multiple occurrences of the same word doesn't add more points
 */
public class WordOccurrenceInDescriptionRule extends BaseScoreRule {
    private final Integer RULE_PER_WORD_INCREMENT_VALUE;
    private final List<String> RELEVANT_WORDS;

    public WordOccurrenceInDescriptionRule(Integer ruleIncrementValue, List<String> relevantWords) {
        RULE_PER_WORD_INCREMENT_VALUE = ruleIncrementValue;
        RELEVANT_WORDS = relevantWords;
    }

    @Override
    protected Boolean ruleApplies(Advertisement ad) {
        return (ad.getDescription() != null && !ad.getDescription().isEmpty())
                && (RELEVANT_WORDS != null && !RELEVANT_WORDS.isEmpty())
                && RELEVANT_WORDS.stream().map(String::toUpperCase).anyMatch(ad.getDescription().toUpperCase()::contains);
    }

    @Override
    protected Score updateScore(Advertisement ad) {

        Integer scorePerWord = Math.toIntExact(
                RELEVANT_WORDS.stream().map(String::toUpperCase).filter(ad.getDescription().toUpperCase()::contains).count())
                * RULE_PER_WORD_INCREMENT_VALUE;

        return ad.getScore().add(scorePerWord);
    }
}
