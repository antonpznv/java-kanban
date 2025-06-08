package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.TaskStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryHistoryManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    void taskManagerInitializing() {
        taskManager = Managers.getDefault();
    }

    @Test
    void historyShouldKeepLastTaskStateOnRetrieval() {
        Task task = new Task("Таск", "Описание", TaskStatus.NEW);
        taskManager.createTask(task);
        taskManager.getTaskById(task.getTaskId());
        task.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(task);
        taskManager.getTaskById(task.getTaskId());

        List<Task> history = taskManager.getHistory();

        assertEquals(2, history.size());
        assertEquals(TaskStatus.NEW, history.get(0).getStatus());
        assertEquals(TaskStatus.IN_PROGRESS, history.get(1).getStatus());
    }
}