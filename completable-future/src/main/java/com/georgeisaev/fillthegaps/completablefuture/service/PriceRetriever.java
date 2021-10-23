package com.georgeisaev.fillthegaps.completablefuture.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Retrieve shop item prices
 *
 * @author Georgy Isaev
 */
@Slf4j
@Service
public class PriceRetriever {

    /**
     * Max timeout to wait an item price from a specific shop
     */
    private static final int MAX_DELAY_MILLIS = 3_000;

    /**
     * Max item price to be generated
     */
    private static final int MAX_ITEM_PRICE = 10_000;

    /**
     * Retrieve price for an item (imitate HTTP-request with a delay)
     *
     * @param itemId item id
     * @param shopId shop id
     * @return the item price
     */
    @SneakyThrows
    public double findPrice(long itemId, long shopId) {
        log.debug("Retrieve a price for itemId={} shopId={}", itemId, shopId);
        int delayMillis = ThreadLocalRandom.current().nextInt(MAX_DELAY_MILLIS);
        Thread.sleep(delayMillis);
        return ThreadLocalRandom.current().nextDouble(MAX_ITEM_PRICE);
    }

}