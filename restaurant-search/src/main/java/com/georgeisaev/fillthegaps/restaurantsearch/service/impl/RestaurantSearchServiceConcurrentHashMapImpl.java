package com.georgeisaev.fillthegaps.restaurantsearch.service.impl;

import com.georgeisaev.fillthegaps.restaurantsearch.model.Restaurant;
import com.georgeisaev.fillthegaps.restaurantsearch.service.RestaurantSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static org.slf4j.helpers.MessageFormatter.format;

/**
 * Restaurant search service backed by {@link ConcurrentHashMap}
 *
 * @author Georgy Isaev
 */
@Slf4j
@Service
public class RestaurantSearchServiceConcurrentHashMapImpl implements RestaurantSearchService {

    /**
     * Stat info template
     */
    private static final String MSG_INFO_RESTAURANT_STAT = "{} - {}";

    /**
     * Contains restaurant stats grouped by restaurant name
     */
    private final Map<String, Long> stat;

    public RestaurantSearchServiceConcurrentHashMapImpl() {
        this.stat = new ConcurrentHashMap<>();
    }

    /**
     * Finds a restaurant by name
     *
     * @param restaurantName name of a restaurant to find
     * @return the found restaurant
     */
    @Override
    public Restaurant getByName(String restaurantName) {
        addToStat(restaurantName);
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantName);
        return restaurant;
    }

    /**
     * Updates a restaurant stat
     *
     * @param restaurantName name of a restaurant
     */
    @Override
    public void addToStat(String restaurantName) {
        stat.merge(restaurantName, 0L, Long::sum);
    }

    /**
     * Prints restaurant stats
     *
     * @return stat
     */
    @Override
    public Set<String> printStat() {
        return stat.entrySet()
                .stream()
                .map(statEntry -> format(MSG_INFO_RESTAURANT_STAT, statEntry.getKey(), statEntry.getValue())
                        .getMessage())
                .collect(Collectors.toUnmodifiableSet());
    }

}
