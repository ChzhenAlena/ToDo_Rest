package org.example.todo_rest.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequestDTO {
    private String taskName;
    private boolean done;
}
