package manager;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    // Логика работы с тасками
    Task createTask(Task task);

    Task updateTask(Task task);

    Task deleteTask(Integer taskId);

    void deleteAllTasks();

    List<Task> getTaskList();

    Task getTaskById(Integer taskId);

    // Логика работы с сабтасками
    SubTask createSubTask(SubTask subtask);

    void updateSubTask(SubTask subtask);

    SubTask deleteSubTask(Integer subTaskId);

    void deleteAllSubTasks();

    List<SubTask> getSubtaskList();

    SubTask getSubTaskById(Integer subTaskId);

    List<SubTask> getSubTasksByEpicId(Integer epicId);

    // Логика работы с эпиками
    Epic createEpic(Epic epic);

    void updateEpic(Epic epic);

    Epic deleteEpicById(Integer taskId);

    void deleteAllEpics();

    List<Epic> getEpicList();

    Epic getEpicById(Integer epicId);

    //Логика работы истории просмотров

    List<Task> getHistory();

}
