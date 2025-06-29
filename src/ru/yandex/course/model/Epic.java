package ru.yandex.course.model;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subId;

    public Epic(String name, String description, int id, TaskStatus status) {
        super(name, description, id, status);
        this.subId = new ArrayList<>();
    }

    public ArrayList<Integer> getSubTaskIds() {
        return subId;
    }

    public void addSubTaskId(int subTaskId) {
        subId.add(subTaskId);
    }

    @Override
    public String toString() {
        return "ru.yandex.course.model.Epic{" +
                "subTaskIds=" + subId +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                '}';
    }
}
