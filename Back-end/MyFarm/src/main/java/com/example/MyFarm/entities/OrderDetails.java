package com.example.MyFarm.entities;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "OrderDetails")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderDetailID")
    private Long orderDetailId;

    @ManyToOne
    @JoinColumn(name = "OrderID")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "ProduceID")
    private Produce produce;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "TotalPrice")
    private Float totalPrice;

    @Column(name = "Address")
    private String address;

}
