package com.bzu.hotel_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor   //automatically generates a constructor with a parameter for each field in your class
@NoArgsConstructor     // generates a constructor with no parameter
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    private String type;
    private int price;
    private String status;
    private String details;
    private int capacity;
    private String size;
    private String features;

    @ManyToOne
    @JoinColumn(name = "facilityId")
    private Facility facility;

}
