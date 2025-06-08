package tasks;

import static org.junit.jupiter.api.Assertions.*;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TaskTest {
    private TaskManager taskManager;

    @BeforeEach
    void taskManagerInitializing() {
        taskManager = Managers.getDefault();
    }

    // 1. Проверьте, что экземпляры класса Task равны друг другу, если равен их id;

    @Test
    void tasksWithSameIdShouldBeEqual() {
        Task task1 = new Task(1, "Таск 1", "Какое-то описание", TaskStatus.NEW);
        Task task2 = new Task(1, "Таск 2", "Другое описание", TaskStatus.DONE);

        assertEquals(task1, task2, "Таски с одинаковым id должны быть равны");
    }

    // 7. Проверьте, что задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера;

    @Test
    void tasksWithAssignedAndGeneratedIdsShouldNotConflict() {
        Task taskWithId = new Task(100, "Таск с заданным id", "Описание", TaskStatus.NEW);
        taskManager.createTask(taskWithId);

        Task taskWithoutId = new Task("Таск без заданного ID", "Описание", TaskStatus.NEW);
        taskManager.createTask(taskWithoutId);

        assertNotEquals(taskWithId.getTaskId(), taskWithoutId.getTaskId(), "ID не должны конфликтовать");
        assertNotNull(taskManager.getTaskById(taskWithId.getTaskId()), "Задача с назначенным ID должна быть" +
                " доступна");
        assertNotNull(taskManager.getTaskById(taskWithoutId.getTaskId()), "Задача с сгенерированным ID" +
                " должна быть доступна");
    }

    // 8. Создайте тест, в котором проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер

    @Test
    void taskShouldRemainUnchangedWhenAddedToManager() {
        Task originalTask = new Task(1, "Оригинальный таск", "Описание", TaskStatus.NEW);
        taskManager.createTask(originalTask);

        Task twinTask = taskManager.getTaskById(1);

                assertEquals(originalTask.getTaskId(), twinTask.getTaskId(), "ID должен совпадать");
                assertEquals(originalTask.getName(), twinTask.getName(), "Название должно совпадать");
                assertEquals(originalTask.getDescription(), twinTask.getDescription(), "Описание" +
                        " должно совпадать");
                assertEquals(originalTask.getStatus(), twinTask.getStatus(), "Статус должен совпадать");
    }
}