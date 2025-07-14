package ru.yandex.course.service;

import ru.yandex.course.service.*;
import ru.yandex.course.model.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history = new LinkedList<>();
    @Override
    public void add(Task task) {
        if (task == null) return;
        history.remove(task); // удаляем дубликаты
        history.add(task);
        if (history.size() > 10) {
            history.remove(0); // ограничение на 10 элементов
        }
    }
    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}
