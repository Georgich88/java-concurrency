package com.georgeisaev.fillthegaps.immutableobjectorder.model.immutalbe;

import com.georgeisaev.fillthegaps.immutableobjectorder.model.annotation.Immutable;
import com.georgeisaev.fillthegaps.immutableobjectorder.model.Item;
import com.georgeisaev.fillthegaps.immutableobjectorder.model.Order;
import com.georgeisaev.fillthegaps.immutableobjectorder.model.PaymentInfo;
import com.georgeisaev.fillthegaps.immutableobjectorder.model.Status;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Immutable
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImmutableOrder implements Order {

    Long id;
    List<Item> items;
    PaymentInfo paymentInfo;
    boolean isPacked;
    Status status;

    public static Order newOrder(Long id, List<Item> items) {
        return new ImmutableOrder(id, items, null, false, null);
    }

    public static Order changePaymentInfo(Order order, PaymentInfo paymentInfo) {
        return new ImmutableOrder(order.getId(), order.getItems(), paymentInfo, order.isPacked(), order.getStatus());
    }

    public static Order changeStatus(Order order, Status status) {
        return new ImmutableOrder(order.getId(), order.getItems(), order.getPaymentInfo(), order.isPacked(), status);
    }

    public static Order changePacked(Order order, boolean isPacked) {
        return new ImmutableOrder(order.getId(), order.getItems(), order.getPaymentInfo(), isPacked, order.getStatus());
    }

    private ImmutableOrder(Long id, List<Item> items, PaymentInfo paymentInfo, boolean isPacked, Status status) {
        this.id = id;
        this.items = Objects.nonNull(items) ? List.copyOf(items) : Collections.emptyList();
        this.paymentInfo = paymentInfo;
        this.isPacked = isPacked;
        if (!this.items.isEmpty() && paymentInfo != null && isPacked) {
            this.status = Status.DELIVERED;
        } else {
            this.status = status;
        }
    }

    public boolean checkStatus() {
        return status == Status.DELIVERED;
    }

}
