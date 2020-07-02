package com.ria.ait.drv_database.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "Items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;
    @Size(min = 5, message = "Name should be at least 10 characters")
    @ApiModelProperty(notes = "The name of the item")
    private String itemName;
    @Min(value = 10, message = "Price should be at least 10")
    @ApiModelProperty(notes = "The price of an item")
    private Double itemPrice;
    @ApiModelProperty(notes = "The brief description of the item")
    private String description;
}
