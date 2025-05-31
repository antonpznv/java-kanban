import tasks.*;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private Integer idGenerator = 1;

    public Integer getNextId() {
        return idGenerator++;
    }

    // Логика работы с тасками
    public Task createTask(Task task) {
        task.setTaskId(getNextId());
        tasks.put(task.getTaskId(), task);
        return task;
    }

    public Task updateTask(Task task) {
        tasks.put(task.getTaskId(), task);
        return task;
    }

    public Task deleteTask(Integer taskId) {
        return tasks.remove(taskId);
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public ArrayList<Task> getTaskList() {
        return new ArrayList<>(tasks.values());
    }

    public Task getTaskById(Integer taskId) {
        return tasks.get(taskId);
    }

    // Логика работы с сабтасками
    public SubTask createSubTask(SubTask subtask) {
        Epic epic = epics.get(subtask.getEpicId());
        if (epic == null) {
            return null;
        }

        subtask.setTaskId(getNextId());
        subTasks.put(subtask.getTaskId(), subtask);
        epic.addSubTaskId(subtask.getTaskId());
        updateEpicStatus(epic.getTaskId());
        return subtask;
    }

    public void updateSubTask(SubTask subtask) {
        if (!subTasks.containsKey(subtask.getTaskId())) {
            return;
        }

        SubTask subTask = subTasks.get(subtask.getTaskId());
        if (subTask.getEpicId() != subtask.getEpicId()) {
            return;
        }

        subTasks.put(subtask.getTaskId(), subtask);
        updateEpicStatus(subtask.getEpicId());
    }

    public SubTask deleteSubTask(Integer subTaskId) {
        SubTask removedSubTask = subTasks.remove(subTaskId);
        if (removedSubTask != null) {
            Epic epic = epics.get(removedSubTask.getEpicId());
            if (epic != null) {
                epic.removeSubtaskId(subTaskId);
                updateEpicStatus(epic.getTaskId());
            }
        }
        return removedSubTask;
    }

    public void deleteAllSubTasks() {
        subTasks.clear();

        for (Epic epic : epics.values()) {
            epic.clearSubTasksIds();
            updateEpicStatus(epic.getTaskId());
        }
    }

    public ArrayList<SubTask> getSubtaskList() {
        return new ArrayList<>(subTasks.values());
    }

    public SubTask getSubTaskById(Integer subTaskId) {
        return subTasks.get(subTaskId);
    }

    public ArrayList<SubTask> getSubTasksByEpicId(Integer epicId) {
        ArrayList<SubTask> subTaskList = new ArrayList<>();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            for (Integer subTaskId : epic.getSubTasksIds()) {
                SubTask subTask = subTasks.get(subTaskId);
                if (subTask != null) {
                    subTaskList.add(subTask);
                }
            }
        }
        return subTaskList;
    }

    // Логика работы с эпиками
    public Epic createEpic(Epic epic) {
        epic.setTaskId(getNextId());
        epics.put(epic.getTaskId(), epic);
        return epic;
    }

    public void updateEpic(Epic epic) {
        Epic verifiableEpic = epics.get(epic.getTaskId());
        if (verifiableEpic == null) {
            return;
        }

        verifiableEpic.setName(epic.getName());
        verifiableEpic.setDescription(epic.getDescription());
    }

    public Epic deleteEpicById(Integer taskId) {
        Epic removedEpic = epics.remove(taskId);
        if (removedEpic != null) {
            ArrayList<Integer> subTasksIdsToRemove = new ArrayList<>(removedEpic.getSubTasksIds());

            for (Integer subTaskId : subTasksIdsToRemove) {
                subTasks.remove(subTaskId);
            }
        }
        return removedEpic;
    }

    public void deleteAllEpics() {
        subTasks.clear();
        epics.clear();
    }

    public ArrayList<Epic> getEpicList() {
        return new ArrayList<>(epics.values());
    }

    public Epic getEpicById(Integer epicId) {
        return epics.get(epicId);
    }

    private void updateEpicStatus (Integer epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }

        if (epic.getSubTasksIds().isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }

        boolean AllSubTasksNew = true;
        boolean AllSubTasksDone = true;

        for (Integer subTaskId : epic.getSubTasksIds()) {
            SubTask subTask = subTasks.get(subTaskId);
            if (subTask == null) {
                continue;
            }

            if (subTask.getStatus() != TaskStatus.NEW) AllSubTasksNew = false;
            if (subTask.getStatus() != TaskStatus.DONE) AllSubTasksDone = false;
        }

            if (AllSubTasksNew) {
                epic.setStatus(TaskStatus.NEW);
            } else if (AllSubTasksDone) {
                epic.setStatus(TaskStatus.DONE);
            } else {
                epic.setStatus(TaskStatus.IN_PROGRESS);
            }
        }
    }