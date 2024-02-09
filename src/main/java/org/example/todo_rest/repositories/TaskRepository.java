package org.example.todo_rest.repositories;

import org.example.todo_rest.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    public List<Task> findTasksByDone(boolean isDone);
}
