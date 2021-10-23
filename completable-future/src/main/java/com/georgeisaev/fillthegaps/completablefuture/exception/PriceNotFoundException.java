package com.georgeisaev.fillthegaps.completablefuture.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

import static org.slf4j.helpers.MessageFormatter.format;

/**
 * Throws when price cannot be retrieved
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PriceNotFoundException extends RuntimeException {

    static final String REASON = "Cannot find a price";

    public PriceNotFoundException(String message) {
        super(message);
    }

    public static Supplier<PriceNotFoundException> supplierForItemId(Long itemId) {
        return () -> new PriceNotFoundException(format(REASON + "for itemId={}", itemId).getMessage());
    }

}
