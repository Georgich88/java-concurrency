package com.georgeisaev.fillthegaps.restaurantsearch.service.impl;

import com.georgeisaev.fillthegaps.restaurantsearch.model.Restaurant;
import com.georgeisaev.fillthegaps.restaurantsearch.service.RestaurantSearchService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class RestaurantSearchServiceConcurrentHashMapImplTest {

    private static final String WHITE_RABBIT = "White Rabbit";
    private static final String[] MOSCOW_MICHELIN_RESTAURANT_NAMES = {WHITE_RABBIT,
            "Twins Garden",
            "ARTEST - Chef’s Table",
            "Selfie", "Beluga",
            "Grand Cru",
            "Biologie",
            "Sakhalin",
            "Savva",
            "Bjorn"
    };
    RestaurantSearchService restaurantSearchService;

    @BeforeEach
    void setUp() {
        restaurantSearchService = new RestaurantSearchServiceConcurrentHashMapImpl();
    }

    @Test
    void shouldGetByName() {
        Restaurant restaurant = restaurantSearchService.getByName(WHITE_RABBIT);
        assertEquals(WHITE_RABBIT, restaurant.getName());
    }

    @Test
    void shouldAddToStatAndPrintStat() throws BrokenBarrierException, InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(MOSCOW_MICHELIN_RESTAURANT_NAMES.length + 1);
        Arrays.stream(MOSCOW_MICHELIN_RESTAURANT_NAMES).forEach(
                restaurantName -> new Thread(() -> {
                    restaurantSearchService.getByName(restaurantName);
                    restaurantSearchService.getByName(restaurantName);
                    try {
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        log.error("Error waiting barrier", e);
                    }
                }).start());
        // Waiting all tasks to finish
        barrier.await();
        Set<String> stats = restaurantSearchService.printStat();
        Arrays.stream(MOSCOW_MICHELIN_RESTAURANT_NAMES).forEach(
                restaurantName -> Assertions.assertThat(stats)
                        .contains(restaurantName + " - 2"));
    }


}