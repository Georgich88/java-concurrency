package com.georgeisaev.fillthegaps.completablefuture.exception;

import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

/**
 * Исключение выбрасывается, если случай не найден
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PriceNotFoundException extends RuntimeException {

    static final String REASON = "Невозможно найти заявку на аванс";

    public PriceNotFoundException(String message) {
        super(message);
    }

    public static Supplier<PriceNotFoundException> supplierForItemId(Long itemId) {
        return () -> new PriceNotFoundException(MessageFormatter.format(REASON + " {}", itemId).getMessage());
    }

}
