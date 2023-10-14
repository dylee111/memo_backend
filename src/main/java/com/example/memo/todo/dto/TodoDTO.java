package com.example.memo.todo.dto;

import com.example.memo.todo.entity.TodoEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TodoDTO {
    private Long id;
    private String todo;
    private LocalDateTime toDoDttm;
    private String mainTodo;

    @Builder
    public TodoDTO(Long id, String todo, String mainTodo, LocalDateTime toDoDttm) {
        this.id = id;
        this.todo = todo;
        this.mainTodo = mainTodo;
        this.toDoDttm = toDoDttm;
    }

    public TodoDTO convertEntityToDTO(TodoEntity entity) {
         TodoDTO todoDTO = TodoDTO.builder()
                .id(entity.getTodoId())
                .todo(entity.getTodo())
                .mainTodo(entity.getMainTodo())
                .toDoDttm(entity.getToDoDttm())
                .build();
        return todoDTO;
    }
}
