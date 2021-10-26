package com.georgeisaev.fillthegaps.completablefuture.service;

import com.georgeisaev.fillthegaps.completablefuture.exception.PriceNotFoundException;
import com.georgeisaev.fillthegaps.completablefuture.utils.PriceUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Aggregates price information from shops
 *
 * @author Georgy Isaev
 */
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class PriceAggregatorService {

    /**
     * Number of available shops
     */
    private static final int SHOP_COUNT = 100;

    /**
     * Max timeout to wait an item price from shops
     */
    private static final int ACCEPTED_TIMEOUT = 2_900;

    /**
     * Shop price retriever
     */
    PriceRetrieverService priceRetrieverService;

    /**
     * Available shops to retrieve a price
     */
    Set<Long> shopIds;

    public PriceAggregatorService(PriceRetrieverService priceRetrieverService) {
        this.priceRetrieverService = priceRetrieverService;
        shopIds = LongStream.rangeClosed(1, SHOP_COUNT)
                .boxed()
                .collect(Collectors.toSet());
    }

    /**
     * Retrieves the lowest price from available shops
     *
     * @param itemId item id
     * @return the lowest price
     */
    public double findMinPrice(long itemId) throws PriceNotFoundException {
        log.debug("Retrieve a price for itemId={}", itemId);
        @SuppressWarnings("unchecked")
        CompletableFuture<Double>[] tasks = shopIds.stream()
                .map(id -> CompletableFuture.supplyAsync(() -> priceRetrieverService.findPrice(itemId, id))
                        .completeOnTimeout(Double.NaN, ACCEPTED_TIMEOUT, TimeUnit.MILLISECONDS))
                .toArray(CompletableFuture[]::new);
        // Combining tasks and retrieve a min price
        CompletableFuture<Void> shopPriceRetrievalTasks = CompletableFuture.allOf(tasks);
        return shopPriceRetrievalTasks.thenApply(unused -> Arrays.stream(tasks)
                        .mapToDouble(CompletableFuture::join)
                        .filter(PriceUtils::isNumber)
                        .min()
                        .orElseThrow(PriceNotFoundException.supplierForItemId(itemId))
                )
                .join();
    }

}
