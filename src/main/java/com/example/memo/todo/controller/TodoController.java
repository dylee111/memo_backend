package com.example.memo.todo.controller;

import com.example.memo.todo.dto.TodoDTO;
import com.example.memo.todo.entity.TodoEntity;
import com.example.memo.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
@Slf4j
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/allList")
    public List<TodoEntity> retrieveAllTodoList() {
        return todoService.retrieveAllTodoList();
    }

    @PostMapping("/saveMemo")
    public String saveMemo(@RequestBody List<TodoDTO> todoDTOList) throws Exception{
        String result = "";
        log.debug("TODOLIST ===== {}", todoDTOList);

        for (TodoDTO dto : todoDTOList) {

            result = todoService.saveMemo(dto);
        }

        return result;
    }

    @PostMapping("/deleteMemo")
    public String deleteMemo(@RequestBody List<Long> memoIdList) {
        String result = "";

        for (Long id : memoIdList) {
            result = todoService.deleteMemo(id);
        }
        return result;
    }

    @PutMapping("/mainMemo")
    public TodoDTO setMainMemo(@RequestBody Long memoId) {
        return todoService.setMainMemo(memoId);
    }

    @PutMapping("/modify")
    public String modifyMemo(@RequestBody TodoDTO todoDTO) throws Exception{
        String result = "";

        result = todoService.modifyMemo(todoDTO);

        return result;
    }

}
