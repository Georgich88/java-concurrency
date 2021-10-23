package com.georgeisaev.fillthegaps.completablefuture.controller;

import com.georgeisaev.fillthegaps.completablefuture.service.PriceAggregatorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(PriceController.BASE_ENDPOINT)
public class PriceController {

    protected static final String BASE_ENDPOINT = "/api/v1";

    PriceAggregatorService priceAggregatorService;

    @GetMapping("/items/{itemId}/prices/lowest")
    public ResponseEntity<Double> findLowestPrice(
            @PathVariable Long itemId) {
        log.info("GET: {}/items/{}/prices/lowest", BASE_ENDPOINT, itemId);
        return ResponseEntity.ok(priceAggregatorService.findMinPrice(itemId));
    }

}
