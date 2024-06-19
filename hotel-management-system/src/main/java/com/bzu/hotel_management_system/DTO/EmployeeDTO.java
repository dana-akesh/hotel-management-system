package com.bzu.hotel_management_system.DTO;


import com.bzu.hotel_management_system.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private Long employeeId;
    private String name;
    private String phone;
    private String DOB;
    private List<Task> tasks;
}
