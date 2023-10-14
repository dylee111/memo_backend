package com.example.memo.todo.service;

import com.example.memo.todo.dto.TodoDTO;
import com.example.memo.todo.entity.TodoEntity;
import com.example.memo.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class TodoService {
    private final TodoRepository todoRepository;

    @Transactional(readOnly = true)
    public List<TodoEntity> retrieveAllTodoList() {

        return todoRepository.findAll();
    }

    public String saveMemo(TodoDTO todoDTO) throws Exception {
        String result = "";
        log.debug("DTO ==== {}", todoDTO);

        if(null == todoDTO.getToDoDttm()) {
            todoDTO.setToDoDttm(LocalDateTime.now());
        }

        try {
            if (null == todoDTO.getTodo() || "".equals(todoDTO.getTodo())) {
                throw new Exception("메모가 없습니다.");
            }

            TodoEntity entity = TodoEntity.builder()
                .todo(todoDTO.getTodo())
                .toDoDttm(todoDTO.getToDoDttm())
                .mainTodo("0")
                .build();
            todoRepository.save(entity);
            result = "SUCCESS";
        } catch (Exception e) {
            log.error(e.getMessage());
            result = e.getMessage();
        }

        return result;
    }

    public String deleteMemo(Long memoId) {
        String result = "";
        try {
            if (null == memoId) {
                throw new Exception("메모 ID는 필수입니다.");
            }

            List<TodoEntity> entity = todoRepository.findByTodoId(memoId);

            if (entity.isEmpty()) {
                throw new Exception("존재하지 않는 메모입니다.");
            }

            todoRepository.deleteById(memoId);
            result = "SUCCESS";
        } catch (Exception e) {
            log.error(e.getMessage());
            result = "FAIL : " + e.getMessage();
        }

        return result;
    }

    public TodoDTO setMainMemo(Long memoId) {
        TodoDTO todoDTO = new TodoDTO();

        List<TodoEntity> entities = todoRepository.findByTodoId(memoId);

        List<TodoEntity> mainToDoEntities = entities
                .stream()
                .filter(e -> "1".equals(e.getMainTodo()))
                .collect(Collectors.toList());

        if(mainToDoEntities.isEmpty()) {

            TodoEntity mainEntity = todoRepository.findByMainTodo("1");

            if (!Objects.isNull(mainEntity)) {
                mainEntity.setMainTodo("0");
                todoRepository.save(mainEntity);
            }

            TodoEntity entity = entities.get(0);

            entity.setMainTodo("1");
            todoRepository.save(entity);

            return todoDTO.convertEntityToDTO(entity);

        } else {
            TodoEntity mainEntity = entities.get(0);

            return todoDTO.convertEntityToDTO(mainEntity);
        }
    }

    public String modifyMemo(TodoDTO dto) {
        String result = "";

        try {
            Optional<TodoEntity> entity = Optional.ofNullable(todoRepository.findById(dto.getId()).orElse(null));

            if (entity.isPresent()) {
                TodoEntity todoEntity = entity.get();
                todoEntity.setTodo(dto.getTodo());
                todoEntity.setToDoDttm(dto.getToDoDttm());
                todoRepository.save(todoEntity);
                result = "SUCCESS";
            }

        } catch (Exception e) {
            result = "FAIL";
        }

        return result;
    }

}
