

import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, SubTask> subTasks;
    private HashMap<Integer, Epic> epics;

    private int idCounter = 1;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.epics = new HashMap<>();
    }

    private int increaseId() {
        return idCounter++;
    }

    public int addTask(Task task) {
        int id = increaseId();
        task.setId(id);
        tasks.put(task.getId(), task);
        return id;
    }

    public boolean updateTask(Task task) {
        if (!tasks.containsKey(task.getId())) {
            return false;
        }
        return tasks.put(task.getId(), task) != null;
    }

    public void removeTask(int id) {
        tasks.remove(id);
    }
    public HashMap<Integer, Task> getAllTasks() {
        return tasks;
    }


    public HashMap<Integer, SubTask> getAllSubTasks() {
        return subTasks;
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }


    public SubTask getSubTask(int id) {
        return subTasks.get(id);
    }
    public int createSubTask(SubTask subTask) {
        int id = increaseId();
        subTask = new SubTask(subTask.getName(), subTask.getDescription(), id, subTask.getStatus(), subTask.getEpicId());
        subTasks.put(id, subTask);
        Epic epic = epics.get(subTask.getEpicId());
        if(epic != null) {
            epic.addSubTaskId(id);
            updateEpicStatus(epic);
        }
        return id;
    }
    public void updateSubTask(SubTask subTask) {
        if (subTasks.containsKey(subTask.getId())) {
            subTasks.put(subTask.getId(), subTask);
            Epic epic = epics.get(subTask.getEpicId());
            if(epic != null) {
                updateEpicStatus(epic);
            }
        }
    }
    public void removeSubTask(int id) {
        SubTask subTask = subTasks.get(id);
        if (subTasks != null) {
            Epic epic =epics.get(subTask.getEpicId());
            if (epic != null) {
                epic.getSubTaskIds().remove(id);
                updateEpicStatus(epic);
            }
            subTasks.remove(id);
        }
    }
    public HashMap<Integer, Epic> getAllEpics() {
        return epics;
    }
    public void removeAllEpics() {
        epics.clear();
        subTasks.clear();
    }
    public Epic getEpic(int id) {
        return epics.get(id);
    }
    public int createEpic(Epic epic) {
        int id = increaseId();
        epic = new Epic(epic.getName(), epic.getDescription(), id, epic.getStatus());
        epics.put(id, epic);
        return id;
    }
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
            updateEpicStatus(epic);
        }
    }
    public void removeEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            HashMap<Integer, Integer> subTaskIds = epic.getSubTaskIds();
            for (Integer subTaskId : subTaskIds.keySet()) {
                subTasks.remove(subTaskId);
            }
            epics.remove(id);
        }
    }
    public HashMap<Integer, SubTask> getEpicSubTasks(int epicId) {
        Epic epic = epics.get(epicId);
        HashMap<Integer, SubTask> epicSubTasks = new HashMap<>();
        if (epic != null) {
            HashMap<Integer, Integer> subTaskIds = epic.getSubTaskIds();
            for (Integer subTaskId : subTaskIds.values()) {
                SubTask subTask = subTasks.get(subTaskId);
                if (subTask != null) {
                    epicSubTasks.put(subTaskId, subTask);
                }
            }
        }
        return epicSubTasks;
    }

    private void updateEpicStatus(Epic epic) {
        HashMap<Integer, Integer> subTaskIds = epic.getSubTaskIds();
        if (subTaskIds.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (Integer subTaskId : subTaskIds.values()) {
            SubTask subTask = subTasks.get(subTaskId);
            if (subTask == null) {
                continue;
            }
            if (subTask.getStatus() != TaskStatus.NEW) {
                allNew = false;
            }
            if (subTask.getStatus() != TaskStatus.DONE) {
                allDone = false;
            }
        }

        if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else if (allNew) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }
}
