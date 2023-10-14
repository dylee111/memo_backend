package com.example.memo.todo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.jfr.Name;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_MEMO")
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TODO_ID")
    private Long todoId;

    @Column(name = "TO_DO")
    private String todo;

    @Column(name = "TO_DO_DTTM")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:00", timezone = "Asia/Seoul")
    private LocalDateTime toDoDttm;

    @Column(name = "MAIN_TO_DO")
    private String mainTodo;

    @Builder
    public TodoEntity(String todo, LocalDateTime toDoDttm, String mainTodo) {
        this.todo = todo;
        this.toDoDttm = toDoDttm;
        this.mainTodo = mainTodo;
    }

    public void setMainTodo(String mainTodo) {
        this.mainTodo = mainTodo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public void setToDoDttm(LocalDateTime toDoDttm) {
        this.toDoDttm = toDoDttm;
    }
}
