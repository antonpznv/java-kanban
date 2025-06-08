package tasks;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EpicTest {
    private TaskManager taskManager;

    @BeforeEach
    void taskManagerInitializing() {
        taskManager = Managers.getDefault();
    }

    //2. проверьте, что наследники класса Task равны друг другу, если равен их id;

    @Test
    void EpicWithSameIdShouldBeEqual() {
        Epic epic1 = new Epic( "Эпик 1", "Какое-то описание");
        Epic epic2 = new Epic("Эпик 2", "Другое описание");

        assertEquals(epic1, epic2, "Эпики с одинаковым id должны быть равны");
    }

    //3. Проверьте, что объект Epic нельзя добавить в самого себя в виде подзадачи;

    @Test
    void epicCannotBeAddedToItself() {
        Epic epic = new Epic("Эпик", "Описание");
        taskManager.createEpic(epic);


        SubTask subTask = new SubTask("Сабтаск", "Попытаемся сделать подзадачей сам эпик",
                TaskStatus.NEW, epic.getTaskId());
        subTask.setTaskId(epic.getTaskId());

        assertNull(taskManager.createSubTask(subTask), "Эпик не должен быть добавлен в самого себя");
    }
}