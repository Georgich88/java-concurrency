package com.georgeisaev.fillthegaps.immutableobjectorder.model;

import com.georgeisaev.fillthegaps.immutableobjectorder.model.annotation.Immutable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Immutable
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Item {

    String title;

}
