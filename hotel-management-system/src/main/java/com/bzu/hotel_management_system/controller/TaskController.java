package com.bzu.hotel_management_system.controller;

import com.bzu.hotel_management_system.DTO.TaskDTO;
import com.bzu.hotel_management_system.exception.BadRequestException;
import com.bzu.hotel_management_system.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Task Management", description = "Operations related to Task management")
public class TaskController {

    private final Logger log = LoggerFactory.getLogger(TaskController.class);

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // get all tasks
    @Operation(
            summary = "Get all tasks",
            description = "Get all tasks in the system",
            responses = {
                    @ApiResponse(
                            description = "Successful operation",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Bad request, invalid json",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized access",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "No tasks found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDTO.class)
                            )
                    )
            }
    )

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        log.info("Request to get all tasks");
        List<TaskDTO> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    // get task by id
    @Operation
            (
                    summary = "Get task by id",
                    description = "Get task by id",
                    responses = {
                            @ApiResponse(
                                    description = "Success",
                                    responseCode = "200"
                            ),
                            @ApiResponse(
                                    description = "Task not found",
                                    responseCode = "404"
                            ),
                            @ApiResponse(
                                    description = "Internal server error",
                                    responseCode = "500"
                            ),
                            @ApiResponse(
                                    description = "Bad request",
                                    responseCode = "400"
                            )
                    }
            )

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> getTaskById(Long id) {
        log.info("Request to get task by id: {}", id);
        TaskDTO task = taskService.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    // add task
    @Operation(
            summary = "Add a task",
            description = "Add a task to the system",
            responses = {
                    @ApiResponse(
                            description = "Task added successfully",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Invalid input",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Task already exists",
                            responseCode = "409"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    )
            }
    )

    @PostMapping
    public ResponseEntity<TaskDTO> addCustomer(@Valid @RequestBody TaskDTO taskDTO) {
        if (taskDTO.getTaskId() != null) {
            log.error("Cannot have an ID {}", taskDTO.getTaskId());
            throw new BadRequestException(TaskController.class.getSimpleName(), "id");
        }
        TaskDTO task = taskService.addTask(taskDTO);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    //get by employee id
    @Operation(
            summary = "Get task by employee id",
            description = "Get task by employee id",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Task not found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    ),
                    @ApiResponse(
                            description = "Bad request",
                            responseCode = "400"
                    )
            }
    )

    @GetMapping("/tasks/employee/{employeeId}")
    public ResponseEntity<List<TaskDTO>> getTaskByEmployeeId(@PathVariable Long employeeId) {
        log.info("Request to get task by employee id: {}", employeeId);
        List<TaskDTO> tasks = taskService.getTaskByEmployeeId(employeeId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    // update task
    @Operation(
            summary = "Update a task",
            description = "Update a task in the system",
            responses = {
                    @ApiResponse(
                            description = "Task updated successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid input",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Task not found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    )
            }
    )

    @PatchMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> updateTask(@Valid @RequestBody TaskDTO taskDTO, @PathVariable Long id) {
        log.info("Request to update task by id: {}", id);
        TaskDTO task = taskService.updateTask(taskDTO);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    // delete task
    @Operation(
            summary = "Delete a task",
            description = "Delete a task in the system",
            responses = {
                    @ApiResponse(
                            description = "Task deleted successfully",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "Task not found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    )
            }
    )

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        log.info("Request to delete task by id: {}", id);
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
