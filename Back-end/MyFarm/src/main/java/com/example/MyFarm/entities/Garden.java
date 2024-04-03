package com.example.MyFarm.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@Entity
@Table(name = "Garden")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Garden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GardenID")
    Long gardenId;

    @Column(name = "Name")
    String name;

    @Column(name = "Location")
    String location;

    @Column(name = "Area")
    Float area;

}