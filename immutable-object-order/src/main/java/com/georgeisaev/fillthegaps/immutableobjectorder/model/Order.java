package com.georgeisaev.fillthegaps.immutableobjectorder.model;

import java.util.List;

public interface Order {

    boolean checkStatus();

    Long getId();

    List<Item> getItems();

    PaymentInfo getPaymentInfo();

    boolean isPacked();

    Status getStatus();

}
