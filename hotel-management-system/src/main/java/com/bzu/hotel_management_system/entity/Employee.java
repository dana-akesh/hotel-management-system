package com.bzu.hotel_management_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// todo: add inheritance
@Data
@AllArgsConstructor   //automatically generates a constructor with a parameter for each field in your class
@NoArgsConstructor     // generates a constructor with no parameter
@Entity
public class Employee extends User {

    private Long employeeId;
    //@OneToMany(mappedBy = "employee")
    //private List<Task> tasks;
}
