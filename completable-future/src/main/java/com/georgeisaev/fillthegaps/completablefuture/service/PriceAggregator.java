package com.georgeisaev.fillthegaps.completablefuture.service;

import com.georgeisaev.fillthegaps.completablefuture.exception.PriceNotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
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
public class PriceAggregator {

    /**
     * Number of available shops
     */
    private static final int SHOP_COUNT = 100;

    /**
     * Max timeout to wait an item price from shops
     */
    private static final int ACCEPTED_TIMEOUT = 3_000;

    /**
     * Shop price retriever
     */
    PriceRetriever priceRetriever;

    /**
     * Available shops to retrieve a price
     */
    Set<Long> shopIds;

    public PriceAggregator(PriceRetriever priceRetriever) {
        this.priceRetriever = priceRetriever;
        shopIds = LongStream.generate(() -> ThreadLocalRandom.current().nextLong())
                .limit(SHOP_COUNT)
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
                .map(id -> CompletableFuture.supplyAsync(() -> priceRetriever.findPrice(itemId, id))
                        .completeOnTimeout(null, ACCEPTED_TIMEOUT, TimeUnit.MILLISECONDS))
                .toArray(CompletableFuture[]::new);
        // Combining tasks and retrieve a min price
        CompletableFuture<Void> shopPriceRetrievalTasks = CompletableFuture.allOf(tasks);
        OptionalDouble price = shopPriceRetrievalTasks.thenApply(unused -> Arrays.stream(tasks)
                        .map(CompletableFuture::join)
                        .filter(Objects::nonNull)
                        .mapToDouble(Double::doubleValue)
                        .min())
                .join();
        return price.orElseThrow(PriceNotFoundException.supplierForItemId(itemId));
    }

}
