package manager;

import tasks.Epic;
import tasks.Task;
import tasks.SubTask;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history = new ArrayList<>();
    private static final int MAX_HISTORY_SIZE = 10;

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public void addToHistory(Task task) {
        if (task == null) {
            return;
        }

        if (history.size() >= MAX_HISTORY_SIZE) {
            history.removeFirst();
        }
        Task taskCopy = copyTask(task);
        history.add(taskCopy);
    }

    private Task copyTask(Task original) {
        if (original instanceof Epic) {
            Epic epic = (Epic) original;
            Epic copy = new Epic(epic.getName(), epic.getDescription());
            copy.setTaskId(epic.getTaskId());
            copy.setStatus(epic.getStatus());

            for (Integer subTaskId : epic.getSubTasksIds()) {
                copy.addSubTaskId(subTaskId);
            }
            return copy;
        } else if (original instanceof SubTask) {
            SubTask subTask = (SubTask) original;
            return new SubTask(subTask.getTaskId(), subTask.getName(), subTask.getDescription(), subTask.getStatus(),
                    subTask.getEpicId());
        } else {
            return new Task(original.getTaskId(), original.getName(), original.getDescription(), original.getStatus());
        }
    }

}