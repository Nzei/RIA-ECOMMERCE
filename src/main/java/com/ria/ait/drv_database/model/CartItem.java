package com.ria.ait.drv_database.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Getter
@Setter
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartItemId;
    @Min(value = 1, message = "There should be at least one quantity")
    @ApiModelProperty(notes = "The amount of items")
    private Integer quantity;
    @ApiModelProperty(notes = "The price of a unit item here")
    private Double unitPrice;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
