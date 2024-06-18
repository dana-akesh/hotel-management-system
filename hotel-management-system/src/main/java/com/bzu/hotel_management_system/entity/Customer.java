package com.bzu.hotel_management_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor   //automatically generates a constructor with a parameter for each field in your class
@NoArgsConstructor     // generates a constructor with no parameter
@Entity
public class Customer extends User {

    @OneToMany(mappedBy = "customer")
    private List<Reservation> reservations;
}
