package org.delcom.app.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.delcom.app.configs.ApiResponse;
import org.delcom.app.entities.CashFlow;
import org.delcom.app.services.CashFlowService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TodoControllerTests {
    @Test
    @DisplayName("Pengujian untuk controller Todo")
    void testTodoController() throws Exception {
        // Buat random UUID
        UUID todoId = UUID.randomUUID();
        UUID nonexistentTodoId = UUID.randomUUID();

        // Membuat dummy data
        CashFlow todo = new CashFlow("Belajar Spring Boot", "Belajar mock repository di unit test", false);
        todo.setId(todoId);

        // Membuat mock ServiceRepository
        // Buat mock
        CashFlowService todoService = Mockito.mock(CashFlowService.class);

        // Atur perilaku mock
        when(todoService.createTodo(any(String.class), any(String.class))).thenReturn(todo);

        // Membuat instance controller
        CashFlowController todoController = new CashFlowController(todoService);
        assert (todoController != null);

        // Menguji create todo dengan data valid
        {
            ApiResponse<Map<String, UUID>> result = todoController.createTodo(todo);
            assert (result != null);
            assert (result.getStatus().equals("success"));
            assert (result.getData().get("id").equals(todoId));
        }

        // Menguji create todo dengan data tidak valid
        {
            // judul kosong
            CashFlow invalidTodo = new CashFlow("", "Deskripsi tanpa judul", false);
            ApiResponse<Map<String, UUID>> result = todoController.createTodo(invalidTodo);
            assert (result != null);
            assert (result.getStatus().equals("fail"));

            // deskripsi kosong
            invalidTodo = new CashFlow("Judul tanpa deskripsi", "", false);
            result = todoController.createTodo(invalidTodo);
            assert (result != null);
            assert (result.getStatus().equals("fail"));

            // keduanya kosong
            invalidTodo = new CashFlow("", "", false);
            result = todoController.createTodo(invalidTodo);
            assert (result != null);
            assert (result.getStatus().equals("fail"));

            // keduanya null
            invalidTodo = new CashFlow(null, null, false);
            result = todoController.createTodo(invalidTodo);
            assert (result != null);
            assert (result.getStatus().equals("fail"));

            // judul null
            invalidTodo = new CashFlow(null, "Deskripsi dengan judul null", false);
            result = todoController.createTodo(invalidTodo);
            assert (result != null);
            assert (result.getStatus().equals("fail"));

            // deskripsi null
            invalidTodo = new CashFlow("Judul dengan deskripsi null", null, false);
            result = todoController.createTodo(invalidTodo);
            assert (result != null);
            assert (result.getStatus().equals("fail"));
        }

        // Menguji getAllTodos
        {
            List<CashFlow> dummyResponse = List.of(todo);
            when(todoService.getAllTodos("testing")).thenReturn(dummyResponse);
            ApiResponse<Map<String, List<CashFlow>>> result = todoController.getAllTodos(null);
            assert (result != null);
            assert (result.getStatus().equals("success"));
            assert (result.getData().size() == 1);
        }

        // Menguji getTodoById dengan ID yang ada
        {
            when(todoService.getTodoById(todoId)).thenReturn(todo);
            ApiResponse<Map<String, CashFlow>> result = todoController.getTodoById(todoId);
            assert (result != null);
            assert (result.getStatus().equals("success"));
            assert (result.getData().get("todo").getId().equals(todoId));
        }

        // Menguji getTodoById dengan ID yang tidak ada
        {
            when(todoService.getTodoById(nonexistentTodoId)).thenReturn(null);
            ApiResponse<Map<String, CashFlow>> result = todoController.getTodoById(nonexistentTodoId);
            assert (result != null);
            assert (result.getStatus().equals("fail"));
        }

        // Menguji updateTodo dengan data valid
        {
            CashFlow updatedTodo = new CashFlow("Belajar Spring Boot - Updated", "Deskripsi updated", true);
            updatedTodo.setId(todoId);
            when(todoService.updateTodo(any(UUID.class), any(String.class), any(String.class), any(Boolean.class)))
                    .thenReturn(updatedTodo);

            ApiResponse<CashFlow> result = todoController.updateTodo(todoId, updatedTodo);
            assert (result != null);
            assert (result.getStatus().equals("success"));
        }

        // Menguji updateTodo dengan data tidak valid
        {
            // judul kosong
            CashFlow invalidTodo = new CashFlow("", "Deskripsi tanpa judul", false);
            invalidTodo.setId(todoId);
            ApiResponse<CashFlow> result = todoController.updateTodo(todoId, invalidTodo);
            assert (result != null);
            assert (result.getStatus().equals("fail"));

            // deskripsi kosong
            invalidTodo = new CashFlow("Judul tanpa deskripsi", "", false);
            invalidTodo.setId(todoId);
            result = todoController.updateTodo(todoId, invalidTodo);
            assert (result != null);
            assert (result.getStatus().equals("fail"));

            // keduanya kosong
            invalidTodo = new CashFlow("", "", false);
            invalidTodo.setId(todoId);
            result = todoController.updateTodo(todoId, invalidTodo);
            assert (result != null);
            assert (result.getStatus().equals("fail"));

            // keduanya null
            invalidTodo = new CashFlow(null, null, false);
            invalidTodo.setId(todoId);
            result = todoController.updateTodo(todoId, invalidTodo);
            assert (result != null);
            assert (result.getStatus().equals("fail"));

            // judul null
            invalidTodo = new CashFlow(null, "Deskripsi dengan judul null", false);
            invalidTodo.setId(todoId);
            result = todoController.updateTodo(todoId, invalidTodo);
            assert (result != null);
            assert (result.getStatus().equals("fail"));

            // deskripsi null
            invalidTodo = new CashFlow("Judul dengan deskripsi null", null, false);
            invalidTodo.setId(todoId);
            result = todoController.updateTodo(todoId, invalidTodo);
            assert (result != null);
            assert (result.getStatus().equals("fail"));
        }

        // Menguji updateTodo dengan ID yang tidak ada
        {
            when(todoService.updateTodo(any(UUID.class), any(String.class), any(String.class), any(Boolean.class)))
                    .thenReturn(null);
            CashFlow updatedTodo = new CashFlow("Belajar Spring Boot - Updated", "Deskripsi updated", true);
            updatedTodo.setId(nonexistentTodoId);

            ApiResponse<CashFlow> result = todoController.updateTodo(nonexistentTodoId, updatedTodo);
            assert (result != null);
            assert (result.getStatus().equals("fail"));
        }

        // Menguji deleteTodo dengan ID yang ada
        {
            when(todoService.deleteTodo(todoId)).thenReturn(true);
            ApiResponse<String> result = todoController.deleteTodo(todoId);
            assert (result != null);
            assert (result.getStatus().equals("success"));
        }

        // Menguji deleteTodo dengan ID yang tidak ada
        {
            when(todoService.deleteTodo(nonexistentTodoId)).thenReturn(false);
            ApiResponse<String> result = todoController.deleteTodo(nonexistentTodoId);
            assert (result != null);
            assert (result.getStatus().equals("fail"));
        }
    }
}