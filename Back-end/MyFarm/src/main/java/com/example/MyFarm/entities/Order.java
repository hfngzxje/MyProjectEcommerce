package com.example.MyFarm.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "\"Order\"")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @Column(name = "OrderDate")
    private LocalDateTime orderDate;

    @Column(name = "Status")
    private Integer status;

}
