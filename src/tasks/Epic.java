package tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subTasksIds;

    public Epic(String name, String description) {
        super(name, description, TaskStatus.NEW);
        this.subTasksIds = new ArrayList<>();
    }

    public void addSubTaskId(Integer subTaskId) {
        if (subTaskId != null) {
            this.subTasksIds.add(subTaskId);
        }
    }

    public ArrayList<Integer> getSubTasksIds() {
        return subTasksIds;
    }

    public void clearSubTasksIds() {
        subTasksIds.clear();
    }

    public void removeSubtaskId(Integer subtaskId) {
        subTasksIds.remove(subtaskId);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "taskId=" + getTaskId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subtaskIds=" + subTasksIds +
                '}';
    }
}