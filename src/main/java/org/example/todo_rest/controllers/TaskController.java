package org.example.todo_rest.controllers;

import org.example.todo_rest.DTO.TaskRequestDTO;
import org.example.todo_rest.DTO.TaskResponceDTO;
import org.example.todo_rest.models.Task;
import org.example.todo_rest.services.TaskService;
import org.example.todo_rest.util.TaskErrorResponce;
import org.example.todo_rest.util.TaskNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TaskController {
    TaskService taskService;
    ModelMapper modelMapper;
    @Autowired
    public TaskController(TaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/tasks")
    public List<TaskResponceDTO> showTasks(@RequestParam(value = "done", required = false, defaultValue = "undefined") String isDone){
        if(isDone.equals("undefined"))
        return taskService.findAll().stream().
                map(this::convertTaskToDto).collect(Collectors.toList());

        else return taskService.findByDone(Boolean.parseBoolean(isDone)).stream().
                map(this::convertTaskToDto).collect(Collectors.toList());

    }

    @GetMapping("/tasks/{id}")
    public TaskResponceDTO showTask(@PathVariable int id){
        Task task = taskService.findById(id);
        return convertTaskToDto(task);
    }
    @PostMapping("/tasks")
    public ResponseEntity<HttpStatus> addTask(@RequestBody TaskRequestDTO taskRequestDTO){
        taskService.addTask(convertDtoToTask(taskRequestDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PutMapping("/tasks/{id}")
    public ResponseEntity<HttpStatus> updateTask(@RequestBody TaskRequestDTO taskRequestDTO, @PathVariable int id){
        taskService.updateTask(convertDtoToTask(taskRequestDTO), id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable int id){
        taskService.deleteTask(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<TaskErrorResponce> handleException(TaskNotFoundException e){
        System.out.println("Handle exception");
        TaskErrorResponce taskErrorResponce = new TaskErrorResponce("Task with this id wasn`t found");
        return new ResponseEntity<>(taskErrorResponce, HttpStatus.NOT_FOUND);
    }
    private Task convertDtoToTask(TaskRequestDTO taskRequestDTO){
        return modelMapper.map(taskRequestDTO, Task.class);
    }
    private TaskResponceDTO convertTaskToDto(Task task){
        return modelMapper.map(task, TaskResponceDTO.class);
    }

}
