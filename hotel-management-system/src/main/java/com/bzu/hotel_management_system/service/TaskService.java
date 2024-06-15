package com.bzu.hotel_management_system.service;

import com.bzu.hotel_management_system.DTO.TaskDTO;

import java.util.List;

public interface TaskService {

    TaskDTO addTask(TaskDTO taskDTO);

    TaskDTO updateTask(TaskDTO taskDTO);

    TaskDTO getTaskById(Long id);

    List<TaskDTO> getTaskByEmployeeId(Long employeeId);

    //TaskDTO getTaskByEmployeeId(Long id);

    void deleteTask(Long id);
}
