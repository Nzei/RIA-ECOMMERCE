package com.ria.ait.drv_database.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "ReviewItems")
public class ReviewItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewItemId;
    @OneToOne
    @JoinColumn(name = "review_id")
    private Review review;
    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
