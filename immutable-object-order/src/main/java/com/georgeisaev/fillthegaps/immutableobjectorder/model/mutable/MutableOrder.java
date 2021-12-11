package com.georgeisaev.fillthegaps.immutableobjectorder.model.mutable;

import com.georgeisaev.fillthegaps.immutableobjectorder.model.annotation.Mutable;
import com.georgeisaev.fillthegaps.immutableobjectorder.model.Item;
import com.georgeisaev.fillthegaps.immutableobjectorder.model.Order;
import com.georgeisaev.fillthegaps.immutableobjectorder.model.PaymentInfo;
import com.georgeisaev.fillthegaps.immutableobjectorder.model.Status;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Mutable
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MutableOrder implements Order {

    Long id;
    List<Item> items;
    PaymentInfo paymentInfo;
    boolean isPacked;
    Status status;

    public MutableOrder(List<Item> items) {
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
