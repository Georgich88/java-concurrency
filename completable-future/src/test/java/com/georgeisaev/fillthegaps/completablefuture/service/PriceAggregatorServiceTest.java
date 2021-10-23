package com.georgeisaev.fillthegaps.completablefuture.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

import static org.mockito.Mockito.when;

class PriceAggregatorServiceTest {

    protected static final double EXPECTED_LOWEST_PRICE = 2.88d;

    PriceRetrieverService priceRetrieverService;
    PriceAggregatorService priceAggregatorService;

    @Test
    void shouldRetrieveLowestPrice() {
        priceRetrieverService = Mockito.mock(PriceRetrieverService.class);
        LongStream.rangeClosed(1, 100).forEach(shopId -> {
            when(priceRetrieverService.findPrice(1L, shopId))
                    .thenReturn(ThreadLocalRandom.current().nextDouble(3d, 10_000d));
        });
        when(priceRetrieverService.findPrice(1L, 3))
                .thenReturn(EXPECTED_LOWEST_PRICE);
        priceAggregatorService = new PriceAggregatorService(priceRetrieverService);
        Assertions.assertEquals(EXPECTED_LOWEST_PRICE, priceAggregatorService.findMinPrice(1L));
    }

}