import manager.Managers;
import manager.TaskManager;
import tasks.*;

public class Main {

    public static void main(String[] args) {
        TaskManager inMemoryTaskManager = Managers.getDefault();

        // 1. Создайте две задачи, а также эпик с двумя подзадачами и эпик с одной подзадачей.
        System.out.println("1. Создайте две задачи, а также эпик с двумя подзадачами и эпик с одной подзадачей.");
        System.out.println(" ");
        System.out.println("Создание двух тасков");
        System.out.println(" ");

        Task taskNew1 = new Task("1st task", "описание первого", TaskStatus.NEW);
        Task taskNew2 = new Task("2nd task", "описание второго", TaskStatus.NEW);

        inMemoryTaskManager.createTask(taskNew1);
        inMemoryTaskManager.createTask(taskNew2);

        System.out.println(inMemoryTaskManager.getTaskList());
        System.out.println(" ");
        System.out.println("Создание Эпика с двумя подзадачами");
        System.out.println(" ");

        Epic epicNew1 = new Epic("1st epic", "ну вот и первый эпик");

        inMemoryTaskManager.createEpic(epicNew1);

        SubTask subTaskNew1 = new SubTask("1st subtask", "вот он, работает",
                TaskStatus.NEW, 3);
        SubTask subTaskNew2 = new SubTask("2nd subtask", "вот он, тоже работает",
                TaskStatus.NEW, 3);

        inMemoryTaskManager.createSubTask(subTaskNew1);
        inMemoryTaskManager.createSubTask(subTaskNew2);

        System.out.println(inMemoryTaskManager.getEpicList());
        System.out.println(inMemoryTaskManager.getSubTasksByEpicId(3));
        System.out.println(" ");
        System.out.println("Эпик с одной подзадачей");
        System.out.println(" ");

        Epic epicNew2 = new Epic("2nd epic", "тест эпика с 1 подзадачей");
        inMemoryTaskManager.createEpic(epicNew2);
        SubTask subTaskNew3 = new SubTask("тест второго эпика", "вот он, тоже работает",
                TaskStatus.NEW, 6);
        inMemoryTaskManager.createSubTask(subTaskNew3);

        System.out.println(inMemoryTaskManager.getEpicById(6));
        System.out.println(inMemoryTaskManager.getSubTasksByEpicId(6));
        System.out.println(" ");

        // 2. Распечатайте списки эпиков, задач и подзадач через System.out.println(..).

        System.out.println("2. Распечатайте списки эпиков, задач и подзадач");
        System.out.println(" ");
        System.out.println(inMemoryTaskManager.getTaskList());
        System.out.println(inMemoryTaskManager.getSubtaskList());
        System.out.println(inMemoryTaskManager.getEpicList());
        System.out.println(" ");

        // 3. Измените статусы созданных объектов, распечатайте их. Проверьте, что статус задачи и подзадачи сохранился,
        // а статус эпика рассчитался по статусам подзадач.

        System.out.println("3. Измените статусы созданных объектов, распечатайте их. Проверьте, что статус задачи и " +
                "подзадачи сохранился, а статус эпика рассчитался по статусам подзадач.");
        System.out.println(" ");
        System.out.println("ТАСК БЫЛО:");
        System.out.println(" ");
        System.out.println(inMemoryTaskManager.getTaskById(1));
        System.out.println(" ");
        System.out.println("ТАСК СТАЛО:");
        System.out.println(" ");

        Task taskUpdated = new Task(1, "новый первый таск", "красивый, обновлённый, выполненый",
                TaskStatus.DONE);
        inMemoryTaskManager.updateTask(taskUpdated);

        System.out.println(inMemoryTaskManager.getTaskById(1));
        System.out.println(" ");
        System.out.println("САБТАСК БЫЛО:");
        System.out.println(" ");
        System.out.println(inMemoryTaskManager.getEpicById(3));
        System.out.println(inMemoryTaskManager.getSubTaskById(4));
        System.out.println(inMemoryTaskManager.getSubTaskById(5));
        System.out.println(" ");
        System.out.println("САБТАСК СТАЛО:");
        System.out.println(" ");

        SubTask subTaskUpdated = new SubTask(4, "тест сабтаска продолжается",
                "эпик изменил статус", TaskStatus.IN_PROGRESS , 3);
        inMemoryTaskManager.updateSubTask(subTaskUpdated);
        SubTask subTaskUpdated1 = new SubTask(5, "тест сабтаска продолжается",
                "эпик изменил статус", TaskStatus.DONE , 3);
        inMemoryTaskManager.updateSubTask(subTaskUpdated1);

        System.out.println(inMemoryTaskManager.getEpicById(3));
        System.out.println(inMemoryTaskManager.getSubTaskById(4));
        System.out.println(inMemoryTaskManager.getSubTaskById(5));
        System.out.println(" ");
        System.out.println("А теперь удалим сабтаск, который в статусе IN_PROGRESS");
        System.out.println(" ");

        inMemoryTaskManager.deleteSubTask(4);

        System.out.println(inMemoryTaskManager.getEpicById(3));
        System.out.println(" ");
        System.out.println("Статус эпика изменился на DONE, в нём остался сабтаск с id 5");
        System.out.println(" ");
        System.out.println("4. И, наконец, попробуйте удалить одну из задач и один из эпиков.");
        System.out.println(" ");
        System.out.println("БЫЛО:");
        System.out.println(" ");
        System.out.println(inMemoryTaskManager.getEpicList());
        System.out.println(inMemoryTaskManager.getTaskList());
        System.out.println(inMemoryTaskManager.getSubtaskList());

        inMemoryTaskManager.deleteEpicById(3);
        inMemoryTaskManager.deleteTask(1);

        System.out.println(" ");
        System.out.println("СТАЛО:");
        System.out.println(" ");
        System.out.println(inMemoryTaskManager.getEpicList());
        System.out.println(inMemoryTaskManager.getTaskList());
        System.out.println(inMemoryTaskManager.getSubtaskList());

        System.out.println(" ");
        System.out.println("Эпик с id 3 удалился корректно, вместе с оставшейся подзадачей. " +
                "Таск тоже удалён корректно");
        System.out.println(" ");
        System.out.println("Все тесты пройдены, согласно техническому заданию");
        System.out.println("Ура! Наконец-то!");

        System.out.println(" ");
        System.out.println("Проверяем историю:");

        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getTaskById(2);

        System.out.println(inMemoryTaskManager.getHistory());


    }
}