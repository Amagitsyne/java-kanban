package ru.yandex.course.service;

import ru.yandex.course.model.*;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    void createTask(Task task);

    void createEpic(Epic epic);

    void createSubTask(SubTask subTask);

    List<Task> getAllTasks();

    Task getTask(int id);

    Epic getEpic(int id);

    SubTask getSubTask(int id);

    List<Epic> getAllEpics();

    List<SubTask> getAllSubTasks();

    List<SubTask> getEpicSubTasks(int epicId);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubTask(SubTask subtask);

    void removeTask(int id);

    void removeEpic(int id);

    void removeSubTask(int id);

    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubTasks();


}