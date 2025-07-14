package ru.yandex.course.service;
import ru.yandex.course.model.*;

import java.util.List;


public interface HistoryManager {
    void add(Task task);
    List<Task> getHistory();
}
