package com.bzu.hotel_management_system.service;

import com.bzu.hotel_management_system.DTO.TaskDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    List<TaskDTO> getAllTasks();

    TaskDTO addTask(TaskDTO taskDTO);

    TaskDTO updateTask(TaskDTO taskDTO);

    TaskDTO getTaskById(Long id);

    List<TaskDTO> getTaskByEmployeeId(Long employeeId);

    //TaskDTO getTaskByEmployeeId(Long id);

    void deleteTask(Long id);
}
