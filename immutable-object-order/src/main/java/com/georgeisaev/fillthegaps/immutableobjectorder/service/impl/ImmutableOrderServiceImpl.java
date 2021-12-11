package com.georgeisaev.fillthegaps.immutableobjectorder.service.impl;

import com.georgeisaev.fillthegaps.immutableobjectorder.model.Item;
import com.georgeisaev.fillthegaps.immutableobjectorder.model.Order;
import com.georgeisaev.fillthegaps.immutableobjectorder.model.PaymentInfo;
import com.georgeisaev.fillthegaps.immutableobjectorder.model.Status;
import com.georgeisaev.fillthegaps.immutableobjectorder.model.immutalbe.ImmutableOrder;
import com.georgeisaev.fillthegaps.immutableobjectorder.service.OrderService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImmutableOrderServiceImpl implements OrderService {

    Map<Long, Order> currentOrders;
    AtomicLong nextId;

    public ImmutableOrderServiceImpl() {
        nextId = new AtomicLong();
        currentOrders = new ConcurrentHashMap<>();
    }

    private long nextId() {
        return nextId.incrementAndGet();
    }

    @Override
    public long createOrder(List<Item> items) {
        long id = nextId();
        Order order = ImmutableOrder.newOrder(id, items);
        currentOrders.put(id, order);
        return id;
    }

    @Override
    public void updatePaymentInfo(long cartId, PaymentInfo paymentInfo) {
        currentOrders.computeIfPresent(cartId, (id, order) -> {
            Order changedPaymentOrder = ImmutableOrder.changePaymentInfo(order, paymentInfo);
            if (changedPaymentOrder.checkStatus()) {
                deliver(changedPaymentOrder);
                return ImmutableOrder.changeStatus(order, Status.DELIVERED);
            }
            return changedPaymentOrder;
        });
    }

    @Override
    public void setPacked(long cartId) {
        currentOrders.computeIfPresent(cartId, (id, order) -> {
            Order packedOrder = ImmutableOrder.changePacked(order, true);
            if (packedOrder.checkStatus()) {
                deliver(packedOrder);
            }
            return packedOrder;
        });
    }

    @Override
    public void deliver(Order order) {
        log.info("Order {} delivered", order.getId());
    }

}
