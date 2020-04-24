package com.ria.ait.drv_database.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Getter
@Setter
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stockId;
    @Min(value = 1, message = "There should be at least one quantity of an item")
    @ApiModelProperty(notes = "The stock quantity  ")
    private Integer quantity;
    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
