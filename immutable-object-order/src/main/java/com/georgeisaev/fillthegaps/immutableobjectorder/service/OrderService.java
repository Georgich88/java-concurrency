package com.georgeisaev.fillthegaps.immutableobjectorder.service;

import com.georgeisaev.fillthegaps.immutableobjectorder.model.Item;
import com.georgeisaev.fillthegaps.immutableobjectorder.model.Order;
import com.georgeisaev.fillthegaps.immutableobjectorder.model.PaymentInfo;

import java.util.List;


public interface OrderService {

    long createOrder(List<Item> items);

    void updatePaymentInfo(long cartId, PaymentInfo paymentInfo);

    void setPacked(long cartId);

    void deliver(Order order);

}
