package com.idealista.ranking.model.service.enumeration;

import com.idealista.ranking.model.service.enumeration.PictureQuality;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PictureQualityTest {

    @Test
    public void fromString_OK() {
        assertEquals(PictureQuality.fromString("hd").get(), PictureQuality.HD);
        assertEquals(PictureQuality.fromString("hD").get(), PictureQuality.HD);
        assertEquals(PictureQuality.fromString("Hd").get(), PictureQuality.HD);
        assertEquals(PictureQuality.fromString("HD").get(), PictureQuality.HD);
        assertEquals(PictureQuality.fromString("sd").get(), PictureQuality.SD);
        assertEquals(PictureQuality.fromString("sD").get(), PictureQuality.SD);
        assertEquals(PictureQuality.fromString("Sd").get(), PictureQuality.SD);
        assertEquals(PictureQuality.fromString("SD").get(), PictureQuality.SD);
    }

    @Test
    public void fromString_incorrectString_OK() {
        assertFalse(PictureQuality.fromString("error").isPresent());
    }

    @Test
    public void fromString_nullObject_OK() {
        assertFalse(PictureQuality.fromString(null).isPresent());
    }
}
