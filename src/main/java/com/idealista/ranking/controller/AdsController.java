package com.idealista.ranking.controller;

import com.idealista.ranking.business.AdsBusiness;
import com.idealista.ranking.model.api.response.PublicAdResponse;
import com.idealista.ranking.model.api.response.QualityAdResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(Consts.API_VERSION + Consts.ADS_ENDPOINT)
@Api(tags = "Advertisement")
@Slf4j
public class AdsController {

    private final AdsBusiness adsBusiness;

    @Autowired
    public AdsController(AdsBusiness adsBusiness) {
        this.adsBusiness = adsBusiness;
    }

    @PostMapping(path = "/calculate-score", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Calculate score for all Ads")
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Process finished successfully"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error processing request")
    }
    )
    public ResponseEntity<Void> calculateScore() {
        log.info("Calculate score call received, processing...");
        // This could be a heavy process and make the client wait for the response could not be the best option.
        // we could change this to a fire and forget call (running the calculateScore() in other thread)
        // and produce status messages to a queue if we want to monitor the process status
        adsBusiness.calculateScore();
        log.info("Scores for all advertisements calculated correctly");
        return ResponseEntity.ok().build();
    }
/*
Yo como encargado de calidad quiero que los usuarios no vean anuncios irrelevantes para que el usuario siempre vea contenido de calidad en idealista. Un anuncio se considera irrelevante si tiene una puntación inferior a 40 puntos.
 Yo como usuario de idealista quiero poder ver los anuncios ordenados de mejor a peor para encontrar fácilmente mi vivienda.
 */

    //TODO añade url del endpoint
    public ResponseEntity<List<PublicAdResponse>> publicListing() {
        //TODO rellena el cuerpo del método
        return ResponseEntity.notFound().build();
    }

    /*
    Yo como encargado de calidad quiero poder ver los anuncios irrelevantes y desde que fecha lo son para medir la calidad media del contenido del portal.
     */
    //TODO añade url del endpoint
    public ResponseEntity<List<QualityAdResponse>> qualityListing() {
        //TODO rellena el cuerpo del método
        return ResponseEntity.notFound().build();
    }

}
