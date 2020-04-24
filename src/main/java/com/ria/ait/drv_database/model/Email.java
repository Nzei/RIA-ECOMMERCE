package com.ria.ait.drv_database.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Emails")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer emailId;
    @ApiModelProperty(notes = "The title of the email")
    private String title;
    @ApiModelProperty(notes = "The body of the email")
    private String body;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
