package com.georgeisaev.fillthegaps.immutableobjectorder.service.impl;

import com.georgeisaev.fillthegaps.immutableobjectorder.model.Item;
import com.georgeisaev.fillthegaps.immutableobjectorder.model.Order;
import com.georgeisaev.fillthegaps.immutableobjectorder.model.PaymentInfo;
import com.georgeisaev.fillthegaps.immutableobjectorder.model.Status;
import com.georgeisaev.fillthegaps.immutableobjectorder.model.mutable.MutableOrder;
import com.georgeisaev.fillthegaps.immutableobjectorder.service.OrderService;
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
public class MutableOrderServiceImpl implements OrderService {

    Map<Long, MutableOrder> currentOrders = new HashMap<>();
    long nextId = 0L;

    private synchronized long nextId() {
        return nextId++;
    }

    @Override
    public synchronized long createOrder(List<Item> items) {
        long id = nextId();
        MutableOrder order = new MutableOrder(items);
        order.setId(id);
        currentOrders.put(id, order);
        return id;
    }

    @Override
    public synchronized void updatePaymentInfo(long cartId, PaymentInfo paymentInfo) {
        currentOrders.get(cartId).setPaymentInfo(paymentInfo);
        if (currentOrders.get(cartId).checkStatus()) {
            deliver(currentOrders.get(cartId));
            currentOrders.get(cartId).setStatus(Status.DELIVERED);
        }
    }

    @Override
    public synchronized void setPacked(long cartId) {
        currentOrders.get(cartId).setPacked(true);
        if (currentOrders.get(cartId).checkStatus()) {
            deliver(currentOrders.get(cartId));
        }
    }

    @Override
    public synchronized void deliver(Order order) {
        log.info("Order {} delivered", order.getId());
    }

}
