package com.idealista.ranking.common.service.factory;

import com.idealista.ranking.exception.AdsServiceException;

/**
 * Generates a Type T or child given a type U selector
 */
public interface AbstractFactory<T, U> {
    T create(U selector) throws AdsServiceException;
}