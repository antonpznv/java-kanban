package tasks;

public class SubTask extends Task {
    private Integer epicId;

    public SubTask(String name, String description, TaskStatus status, Integer epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public SubTask(Integer taskId, String name, String description, TaskStatus status, Integer epicId) {
        super(taskId, name, description, status);
        this.epicId = epicId;
    }

    public Integer getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "taskId=" + getTaskId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", epicId=" + epicId +
                '}';
    }
}