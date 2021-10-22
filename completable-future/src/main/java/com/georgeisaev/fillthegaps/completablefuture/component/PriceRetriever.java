package com.georgeisaev.fillthegaps.completablefuture.component;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class PriceRetriever {

    private static final int MAX_DELAY_MILLIS = 3_000;
    private static final int MAX_ITEM_PRICE = 10_000;

    /**
     * Retrieve price for an item (imitate HTTP-request with a delay)
     *
     * @param itemId item id
     * @param shopId shop id
     * @return the item price
     */
    @SneakyThrows
    public double getPrice(long itemId, long shopId) {
        int delayMillis = ThreadLocalRandom.current().nextInt(MAX_DELAY_MILLIS);
        Thread.sleep(delayMillis);
        return ThreadLocalRandom.current().nextDouble(MAX_ITEM_PRICE);
    }

}