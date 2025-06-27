import java.util.HashMap;

public class Epic extends Task {
    private HashMap<Integer, Integer> subId;


    public Epic(String name, String description, int id, TaskStatus status) {
        super(name, description, id, status);
        this.subId = new HashMap<>();
    }

    public HashMap<Integer, Integer> getSubTaskIds() {
        return subId;
    }

    public void addSubTaskId(int subTaskId) {
        subId.put(subTaskId, subTaskId);
    }

    @Override
    public String toString() {
        return "Epic{" + "subTaskIds=" + subId + ", name='" + getName() + '\'' + ", description='" + getDescription() + '\'' + ", id=" + getId() + ", status=" + getStatus() + '}';
    }
}
