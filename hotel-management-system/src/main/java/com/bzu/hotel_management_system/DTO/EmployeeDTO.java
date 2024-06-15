package com.bzu.hotel_management_system.DTO;


import com.bzu.hotel_management_system.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO extends UserDTO {

    private Long employeeId;
    private List<Task> tasks;
}
