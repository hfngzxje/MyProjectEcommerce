package com.example.MyFarm.entities;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "UserOrder")
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserOrderID")
    private Long userOrderId;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "OrderID")
    private Order order;

}