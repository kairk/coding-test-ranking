package com.idealista.ranking.controller;

import com.idealista.ranking.business.AdsBusiness;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdsControllerTest {

    @Mock
    AdsBusiness businessMock;

    AdsController controller;

    @Test
    public void calculateScore_OK() {
        //Given
        doNothing().when(businessMock).calculateScore();
        controller = new AdsController(businessMock);

        //When
        ResponseEntity<Void> result = controller.calculateScore();

        //Then
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        verify(businessMock, times(1)).calculateScore();
    }


}
