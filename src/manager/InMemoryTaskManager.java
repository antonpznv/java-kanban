package manager;

import tasks.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, SubTask> subTasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Integer idGenerator = 1;
    private HistoryManager historyManager = Managers.getDefaultHistory();

    private Integer getNextId() {
        return idGenerator++;
    }

    // Логика работы с тасками
    @Override
    public Task createTask(Task task) {
        task.setTaskId(getNextId());
        tasks.put(task.getTaskId(), task);
        return task;
    }

    @Override
    public Task updateTask(Task task) {
        tasks.put(task.getTaskId(), task);
        return task;
    }

    @Override
    public Task deleteTask(Integer taskId) {
        return tasks.remove(taskId);
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public ArrayList<Task> getTaskList() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public Task getTaskById(Integer taskId) {
        Task task = tasks.get(taskId);
        addToHistory(task);
        return task;
    }

    // Логика работы с сабтасками
    @Override
    public SubTask createSubTask(SubTask subtask) {
        Epic epic = epics.get(subtask.getEpicId());
        if (epic == null) {
            return null;
        }

        if (subtask.getTaskId() != null && subtask.getTaskId().equals(subtask.getEpicId())) {
            return null;
        }

        subtask.setTaskId(getNextId());
        subTasks.put(subtask.getTaskId(), subtask);
        epic.addSubTaskId(subtask.getTaskId());
        updateEpicStatus(epic.getTaskId());
        return subtask;
    }

    @Override
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

    @Override
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

    @Override
    public void deleteAllSubTasks() {
        subTasks.clear();

        for (Epic epic : epics.values()) {
            epic.clearSubTasksIds();
            updateEpicStatus(epic.getTaskId());
        }
    }

    @Override
    public ArrayList<SubTask> getSubtaskList() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public SubTask getSubTaskById(Integer subTaskId) {
        SubTask subTask = subTasks.get(subTaskId);
        addToHistory(subTask);
        return subTask;
    }

    @Override
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
    @Override
    public Epic createEpic(Epic epic) {
        epic.setTaskId(getNextId());
        epics.put(epic.getTaskId(), epic);
        return epic;
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic verifiableEpic = epics.get(epic.getTaskId());
        if (verifiableEpic == null) {
            return;
        }

        verifiableEpic.setName(epic.getName());
        verifiableEpic.setDescription(epic.getDescription());
    }

    @Override
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

    @Override
    public void deleteAllEpics() {
        subTasks.clear();
        epics.clear();
    }

    @Override
    public ArrayList<Epic> getEpicList() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public Epic getEpicById(Integer epicId) {
        Epic epic = epics.get(epicId);
        addToHistory(epic);
        return epic;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void addToHistory(Task task) {
        historyManager.addToHistory(task);
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