package com.georgeisaev.fillthegaps.completablefuture.component;

import com.georgeisaev.fillthegaps.completablefuture.exception.PriceNotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
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

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class PriceAggregator {

    private static final int SHOP_COUNT = 100;
    private static final int ACCEPTED_TIMEOUT = 3_000;
    PriceRetriever priceRetriever;
    Set<Long> shopIds;

    public PriceAggregator(PriceRetriever priceRetriever) {
        this.priceRetriever = priceRetriever;
        shopIds = LongStream.generate(() -> ThreadLocalRandom.current().nextLong())
                .limit(SHOP_COUNT)
                .boxed()
                .collect(Collectors.toSet());
    }

    public double getMinPrice(long itemId) {
        // здесь будет ваш код
        @SuppressWarnings("unchecked")
        CompletableFuture<Double>[] shopRetrievalTasks = shopIds.stream()
                .map(id -> CompletableFuture.supplyAsync(() -> priceRetriever.getPrice(itemId, id))
                        .completeOnTimeout(null, ACCEPTED_TIMEOUT, TimeUnit.MILLISECONDS))
                .toArray(CompletableFuture[]::new);

        CompletableFuture<Void> shopTask = CompletableFuture.allOf(shopRetrievalTasks);
        OptionalDouble price = shopTask.thenApply(unused -> Arrays.stream(shopRetrievalTasks)
                        .filter(Objects::nonNull)
                        .mapToDouble(CompletableFuture::join)
                        .min())
                .join();
        return price.orElseThrow(PriceNotFoundException.supplierForItemId(itemId));
    }

}
