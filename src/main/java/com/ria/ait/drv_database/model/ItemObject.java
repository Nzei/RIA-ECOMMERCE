package com.ria.ait.drv_database.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ItemObject {
    private Integer itemId;
    @Size(min = 5, message = "The name should be at least 5 in length")
    @ApiModelProperty(notes = "The name of the item")
    private String itemName;
    @ApiModelProperty(notes = "A brief description of the item")
    private String itemDescription;
    @Min(value = 10, message = "An item should have a price")
    @ApiModelProperty(notes = "The price of an item")
    private Double itemPrice;
}
