package com.example.MyFarm.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Produce")
public class Produce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProduceID")
    private Long produceId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "PlantingDate")
    private LocalDateTime plantingDate;

    @Column(name = "ExpectedHarvestDate")
    private LocalDateTime expectedHarvestDate;

    @Column(name = "ActualHarvestDate")
    private LocalDateTime actualHarvestDate;

    @ManyToOne
    @JoinColumn(name = "GardenID")
    private Garden garden;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "Status")
    private Integer status;

    @Column(name = "Img")
    private String img;

    @Column(name = "Price")
    private Float price;

}
