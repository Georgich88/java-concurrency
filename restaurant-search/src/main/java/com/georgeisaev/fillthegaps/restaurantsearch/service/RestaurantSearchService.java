package com.georgeisaev.fillthegaps.restaurantsearch.service;

import com.georgeisaev.fillthegaps.restaurantsearch.model.Restaurant;

import java.util.Set;

public interface RestaurantSearchService {

    Restaurant getByName(String restaurantName);

    void addToStat(String restaurantName);

    Set<String> printStat();

}
