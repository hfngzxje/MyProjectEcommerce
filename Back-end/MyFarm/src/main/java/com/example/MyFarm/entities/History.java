package com.example.MyFarm.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "History")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HistoryID")
    private Long historyId;

    @Column(name = "Description")
    private String description;

    @Column(name = "Timestamp")
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ProduceID")
    private Produce produce;

}
