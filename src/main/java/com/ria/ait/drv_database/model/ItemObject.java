package com.ria.ait.drv_database.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemObject {
    private Integer itemId;
    private String itemName;
    private String itemDescription;
    private Double itemPrice;
}
