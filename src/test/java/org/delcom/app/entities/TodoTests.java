package org.delcom.app.entities;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TodoTests {
    @Test
    @DisplayName("Memembuat instance dari kelas Todo")
    void testMembuatInstanceTodo() throws Exception {
        // Todo telah selesai
        {
            CashFlow todo = new CashFlow("Testing Title", "Testing Description", false);

            assert (todo.getTitle().equals("Testing Title"));
            assert (todo.getDescription().equals("Testing Description"));
            assert (todo.isFinished() == false);
        }

        // Todo belum selesai
        {
            CashFlow todo = new CashFlow("Another Title", "Another Description", true);

            assert (todo.getTitle().equals("Another Title"));
            assert (todo.getDescription().equals("Another Description"));
            assert (todo.isFinished() == true);
        }

        // Todo dengan nilai default
        {
            CashFlow todo = new CashFlow();

            assert (todo.getId() == null);
            assert (todo.getTitle() == null);
            assert (todo.getDescription() == null);
            assert (todo.isFinished() == false);
        }

        // Todo dengan setNilai
        {
            CashFlow todo = new CashFlow();
            UUID generatedId = UUID.randomUUID();
            todo.setId(generatedId);
            todo.setTitle("Set Title");
            todo.setDescription("Set Description");
            todo.setFinished(true);
            todo.setCover("/cover.png");
            todo.onCreate();
            todo.onUpdate();

            assert (todo.getId().equals(generatedId));
            assert (todo.getTitle().equals("Set Title"));
            assert (todo.getDescription().equals("Set Description"));
            assert (todo.isFinished() == true);
            assert (todo.getCover().equals("/cover.png"));
            assert (todo.getCreatedAt() != null);
            assert (todo.getUpdatedAt() != null);
        }
    }
}