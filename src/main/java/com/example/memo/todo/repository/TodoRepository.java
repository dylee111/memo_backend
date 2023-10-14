package com.example.memo.todo.repository;

import com.example.memo.todo.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

    TodoEntity findByMainTodo(String mainTodo);

    List<TodoEntity> findByTodoId(Long todoId);
}
