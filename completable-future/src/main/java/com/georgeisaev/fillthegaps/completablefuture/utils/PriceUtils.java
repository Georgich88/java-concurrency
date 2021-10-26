package com.georgeisaev.fillthegaps.completablefuture.utils;

import lombok.experimental.UtilityClass;

import static java.util.Objects.nonNull;

@UtilityClass
public class PriceUtils {

    /**
     * Returns {@code true} if this {@code Double} price is a number
     * (not null, not a NaN, not an Infinite), {@code false} otherwise.
     *
     * @return {@code true} if the value represented by this object is a number; {@code false} otherwise.
     */
    public static boolean isNumber(Double value) {
        return nonNull(value) && !value.isNaN() && !value.isInfinite();
    }

}
