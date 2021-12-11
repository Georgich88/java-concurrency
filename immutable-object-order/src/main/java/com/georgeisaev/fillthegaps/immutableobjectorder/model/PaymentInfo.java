package com.georgeisaev.fillthegaps.immutableobjectorder.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class PaymentInfo {

    String title;

}
