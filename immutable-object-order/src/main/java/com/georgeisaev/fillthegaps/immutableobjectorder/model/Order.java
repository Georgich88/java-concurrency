package com.georgeisaev.fillthegaps.immutableobjectorder.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    Long id;
    List<Item> items;
    PaymentInfo paymentInfo;
    boolean isPacked;
    Status status;

    public Order(List<Item> items) {
        this.items = items;
    }

    public synchronized boolean checkStatus() {
        if (items != null && !items.isEmpty() && paymentInfo != null && isPacked) {
            status = Status.DELIVERED;
            return true;
        }
        return false;
    }

}
