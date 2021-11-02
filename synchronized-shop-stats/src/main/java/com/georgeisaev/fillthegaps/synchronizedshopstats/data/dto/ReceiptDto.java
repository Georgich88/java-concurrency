package com.georgeisaev.fillthegaps.synchronizedshopstats.data.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Data
public class ReceiptDto {

    Long id;
    Long count;
    Long price;

}
