package org.example.todo_rest.services;
import org.example.todo_rest.models.Task;
import org.example.todo_rest.repositories.TaskRepository;
import org.example.todo_rest.util.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    public List<Task> findAll(){
        return taskRepository.findAll();
    }
    public List<Task> findByDone(boolean isDone){
        return taskRepository.findTasksByDone(isDone);
    }
    public Task findById(int id){
        return taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
    }

    public void addTask(Task task){
        enrichTask(task);
        taskRepository.save(task);
    }
    public void deleteTask(int id){
        taskRepository.deleteById(id);
    }
    public void updateTask(Task task, int id){
        task.setId(id);
        task.setCreatedAt(taskRepository.findById(id).get().getCreatedAt());
        taskRepository.save(task);
    }
    private void enrichTask(Task task){
        task.setCreatedAt(new Date());
    }
}
