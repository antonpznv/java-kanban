package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

public class inMemoryTaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    void taskManagerInitializing() {
        taskManager = Managers.getDefault();
    }

    //6. Проверьте, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id;

    @Test
    void managerShouldAddAndFindDifferentTaskTypes() {
        Task task = new Task("Таск", "Описание", TaskStatus.NEW);
        Epic epic = new Epic("Эпик", "Описание");
        taskManager.createTask(task);
        taskManager.createEpic(epic);

        SubTask subTask = new SubTask("Сабтаск", "Описание", TaskStatus.NEW, epic.getTaskId());
        taskManager.createSubTask(subTask);

        assertEquals(task, taskManager.getTaskById(task.getTaskId()), "Находим Таск");
        assertEquals(epic, taskManager.getEpicById(epic.getTaskId()), "Находим Эпик");
        assertEquals(subTask, taskManager.getSubTaskById(subTask.getTaskId()), "Находим Сабтаск");
    }
}