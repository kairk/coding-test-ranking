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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/public-listing", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Returns a paginated list of Ads filtered for public listing (score above a minimum)")
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Advertisements found"),
            @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "No advertisements found that match criteria"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error processing request")
    }
    )
    public ResponseEntity<List<PublicAdResponse>> publicListing(
            @RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(adsBusiness.getPublicListing(page, size));
    }

    @GetMapping(path = "/quality-listing", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Returns a paginated list of Ads filtered for quality listing (score below the minimum set)")
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Advertisements found"),
            @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "No advertisements found that match criteria"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal error processing request")
    }
    )
    public ResponseEntity<List<QualityAdResponse>> qualityListing(
            @RequestParam("page") int page, @RequestParam("size") int size
    ) {
        return ResponseEntity.ok(adsBusiness.getQualityListing(page, size));
    }

}
