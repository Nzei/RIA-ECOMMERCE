package com.ria.ait.drv_database.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;
    private Double cartPrice;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
