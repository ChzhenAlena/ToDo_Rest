package org.example.todo_rest.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class TaskResponceDTO {
    private String taskName;
    private boolean done;
    private Date createdAt;
}
