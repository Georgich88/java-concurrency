package com.georgeisaev.fillthegaps.restaurantsearch.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Restaurant {

    String name;

}
