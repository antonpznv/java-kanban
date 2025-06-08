package tasks;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SubTaskTest {
    private TaskManager taskManager;

    @BeforeEach
    void taskManagerInitializing() {
        taskManager = Managers.getDefault();
    }

    //2. Проверьте, что наследники класса Task равны друг другу, если равен их id;

    @Test
    void subtasksWithSameIdShouldBeEqual() {
        SubTask subTask1 = new SubTask(1, "Сабтаск 1",
                "Какое-то описание", TaskStatus.NEW, 10);
        SubTask subTask2 = new SubTask(1, "Сабтаск 2",
                "Другое описание", TaskStatus.DONE, 20);

        assertEquals(subTask1, subTask2, "Сабтаски с одинаковым id должны быть равны");
    }

    //4. Проверьте, что объект Subtask нельзя сделать своим же эпиком;

    @Test
    void subtaskCannotBeItsOwnEpic() {
        Epic epic = new Epic("Эпик", "Описание");
        taskManager.createEpic(epic);

        SubTask subTask = new SubTask("Сабтаск", "Описание", TaskStatus.NEW, epic.getTaskId());
        taskManager.createSubTask(subTask);

        SubTask invalidSubTask = new SubTask("Некорректный сабтаск",
                "Некорректный epicId", TaskStatus.NEW, subTask.getTaskId());
        assertNull(taskManager.createSubTask(invalidSubTask), "Подзадача не должна быть своим же эпиком");
    }
}