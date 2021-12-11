package com.georgeisaev.fillthegaps.immutableobjectorder.service;

import com.georgeisaev.fillthegaps.immutableobjectorder.model.Item;
import com.georgeisaev.fillthegaps.immutableobjectorder.model.Order;
import com.georgeisaev.fillthegaps.immutableobjectorder.model.PaymentInfo;
import com.georgeisaev.fillthegaps.immutableobjectorder.model.Status;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderService {

    Map<Long, Order> currentOrders = new HashMap<>();
    long nextId = 0L;

    private synchronized long nextId() {
        return nextId++;
    }

    public synchronized long createOrder(List<Item> items) {
        long id = nextId();
        Order order = new Order(items);
        order.setId(id);
        currentOrders.put(id, order);
        return id;
    }

    public synchronized void updatePaymentInfo(long cartId, PaymentInfo paymentInfo) {
        currentOrders.get(cartId).setPaymentInfo(paymentInfo);
        if (currentOrders.get(cartId).checkStatus()) {
            deliver(currentOrders.get(cartId));
            currentOrders.get(cartId).setStatus(Status.DELIVERED);
        }
    }

    public synchronized void setPacked(long cartId) {
        currentOrders.get(cartId).setPacked(true);
        if (currentOrders.get(cartId).checkStatus()) {
            deliver(currentOrders.get(cartId));
        }
    }

    private synchronized void deliver(Order order) {
        log.info("Order {} delivered", order.getId());
    }

}