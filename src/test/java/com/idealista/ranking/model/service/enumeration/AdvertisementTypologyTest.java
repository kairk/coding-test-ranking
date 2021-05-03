package com.idealista.ranking.model.service.enumeration;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class AdvertisementTypologyTest {

    @Test
    public void fromString_OK() {
        assertEquals(AdvertisementTypology.fromString("flat").get(), AdvertisementTypology.FLAT);
        assertEquals(AdvertisementTypology.fromString("FLAT").get(), AdvertisementTypology.FLAT);
        assertEquals(AdvertisementTypology.fromString("fLAT").get(), AdvertisementTypology.FLAT);
        assertEquals(AdvertisementTypology.fromString("CHALET").get(), AdvertisementTypology.CHALET);
        assertEquals(AdvertisementTypology.fromString("chalet").get(), AdvertisementTypology.CHALET);
        assertEquals(AdvertisementTypology.fromString("cHalET").get(), AdvertisementTypology.CHALET);
        assertEquals(AdvertisementTypology.fromString("GARAGE").get(), AdvertisementTypology.GARAGE);
        assertEquals(AdvertisementTypology.fromString("garage").get(), AdvertisementTypology.GARAGE);
        assertEquals(AdvertisementTypology.fromString("GaRAge").get(), AdvertisementTypology.GARAGE);
    }

    @Test
    public void fromString_incorrectString_OK() {
        assertFalse(AdvertisementTypology.fromString("error").isPresent());
    }

    @Test
    public void fromString_nullObject_OK() {
        assertFalse(PictureQuality.fromString(null).isPresent());
    }
}
