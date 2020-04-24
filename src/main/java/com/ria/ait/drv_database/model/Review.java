package com.ria.ait.drv_database.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;
    private String reviewText;
    private Date reviewDate;
    private Integer rating ;
    private Date dateTime;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
