package ru.yandex.course.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subId = new ArrayList<>();

    public Epic(String title, String description) {
        super(title, description, TaskStatus.NEW);
    }

    public List<Integer> getSubTaskIds() {
        return subId;
    }

    public void addSubTask(int id) {
        subId.add(id);
    }

    public void removeSubTask(int id) {
        subId.remove((Integer) id);
    }

    public void removeAllSubTasks() {
        subId.clear();
    }


}
