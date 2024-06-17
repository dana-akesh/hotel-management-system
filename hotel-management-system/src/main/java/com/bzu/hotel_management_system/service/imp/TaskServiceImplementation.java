package com.bzu.hotel_management_system.service.imp;

import com.bzu.hotel_management_system.DTO.TaskDTO;
import com.bzu.hotel_management_system.entity.Task;
import com.bzu.hotel_management_system.exception.ResourceNotFoundException;
import com.bzu.hotel_management_system.repository.TaskRepository;
import com.bzu.hotel_management_system.service.TaskService;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImplementation implements TaskService {
    private TaskRepository taskRepository;

    public TaskServiceImplementation(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();

        return tasks.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public TaskDTO addTask(TaskDTO taskDTO) {
        Task task = mapToEntity(taskDTO);
        Task newTask = taskRepository.save(task);

        TaskDTO taskResponse = mapToDTO(newTask);
        return taskResponse;
    }

    @Override
    public TaskDTO updateTask(TaskDTO taskDTO) {
        // todo check if the task exists
        Task task = taskRepository.findById(taskDTO.getTaskId()).orElseThrow(
                () -> new ResourceNotFoundException("Task", "id", taskDTO.getTaskId()));

        task.setTaskName(taskDTO.getTaskName());
        task.setTaskDescription(taskDTO.getTaskDescription());
        task.setTaskStatus(taskDTO.getTaskStatus());

        // todo check
        //task.setEmployee(taskDTO.getEmployeeId());
        Task updatedTask = taskRepository.save(task);

        return mapToDTO(updatedTask);
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task", "id", id));

        return mapToDTO(task);
    }

    @Override
    public List<TaskDTO> getTaskByEmployeeId(Long employeeId) {
        List<Task> tasks = taskRepository.findByEmployeeId(employeeId);

        if (tasks.isEmpty()) {
            throw new ResourceNotFoundException("Task", "employeeId", employeeId);
        }

        return tasks.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Task", "id", id));

        taskRepository.delete(task);
    }

    // todo: implement this method
    private TaskDTO mapToDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setTaskId(task.getTaskId());
        taskDTO.setTaskName(task.getTaskName());
        taskDTO.setTaskDescription(task.getTaskDescription());
        taskDTO.setTaskStatus(task.getTaskStatus());

        // todo check
        taskDTO.setEmployeeId(task.getEmployee().getUserId());

        return taskDTO;
    }

    // todo: implement this method
    private Task mapToEntity(TaskDTO taskDTO) {
        Task task = new Task();

        task.setTaskId(taskDTO.getTaskId());
        task.setTaskName(taskDTO.getTaskName());
        task.setTaskDescription(taskDTO.getTaskDescription());
        task.setTaskStatus(taskDTO.getTaskStatus());

        // todo check
        //task.setEmployee(taskDTO.getEmployeeId().);

        return task;
    }
}
