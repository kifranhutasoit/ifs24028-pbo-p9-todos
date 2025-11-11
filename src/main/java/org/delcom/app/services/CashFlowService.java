package org.delcom.app.services;

import java.util.List;
import java.util.UUID;

import org.delcom.app.entities.CashFlow;
import org.delcom.app.repositories.CashFlowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CashFlowService {
    private final CashFlowRepository todoRepository;

    public CashFlowService(CashFlowRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Transactional
    public CashFlow createTodo(String title, String description) {
        CashFlow todo = new CashFlow(title, description, false);
        return todoRepository.save(todo);
    }

    public List<CashFlow> getAllTodos(String search) {
        if (search != null && !search.trim().isEmpty()) {
            return todoRepository.findByKeyword(search);
        }
        return todoRepository.findAll();
    }

    public CashFlow getTodoById(UUID id) {
        return todoRepository.findById(id).orElse(null);
    }

    @Transactional
    public CashFlow updateTodo(UUID id, String title, String description, Boolean isFinished) {
        CashFlow todo = todoRepository.findById(id).orElse(null);
        if (todo != null) {
            todo.setTitle(title);
            todo.setDescription(description);
            todo.setFinished(isFinished);
            return todoRepository.save(todo);
        }
        return null;
    }

    @Transactional
    public boolean deleteTodo(UUID id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}