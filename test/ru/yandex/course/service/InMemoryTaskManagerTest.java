package ru.yandex.course.service;
import ru.yandex.course.model.*;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class InMemoryTaskManagerTest {

    private static final String TASK_NAME = "Задача";
    private static final String TASK_DESCRIPTION = "Описание";
    private static final String EPIC_NAME = "Эпик";
    private static final String EPIC_DESCRIPTION = "Описание эпика";
    private static final String SUBTASK_NAME = "Подзадача";
    private static final String SUBTASK_DESCRIPTION = "Описание подзадачи";

    @Test
    @DisplayName("addTask() должен сохранять задачу в менеджер")
    void addTask_givenValidTask_shouldStoreTask() {
        TaskManager manager = new InMemoryTaskManager();
        Task task = new Task(TASK_NAME, TASK_DESCRIPTION, TaskStatus.NEW);

        manager.createTask(task);
        List<Task> allTasks = manager.getAllTasks();

        assertEquals(1, allTasks.size());
        assertEquals(TASK_NAME, allTasks.get(0).getName());
    }

    @Test
    @DisplayName("addEpic() должен сохранять эпик")
    void addEpic_givenValidEpic_shouldStoreEpic() {
        TaskManager manager = new InMemoryTaskManager();
        Epic epic = new Epic(EPIC_NAME, EPIC_DESCRIPTION);

        manager.createEpic(epic);
        Epic storedEpic = manager.getEpic(epic.getId());

        assertNotNull(storedEpic);
        assertEquals(EPIC_NAME, storedEpic.getName());
    }

    @Test
    @DisplayName("addSubtask() должен добавлять подзадачу к эпику")
    void addSubtask_givenValidSubtask_shouldLinkToEpic() {
        TaskManager manager = new InMemoryTaskManager();
        Epic epic = new Epic(EPIC_NAME, EPIC_DESCRIPTION);
        manager.createEpic(epic);

        SubTask subTask = new SubTask(SUBTASK_NAME, SUBTASK_DESCRIPTION, TaskStatus.NEW, epic.getId());
        manager.createSubTask(subTask);

        List<SubTask> subTasks = manager.getEpicSubTasks(epic.getId());

        assertEquals(1, subTasks.size());
        assertEquals(SUBTASK_NAME, subTasks.get(0).getName());
    }

    @Test
    @DisplayName("updateSubtask() должен менять статус подзадачи и обновлять статус эпика")
    void updateSubtask_whenStatusChanged_shouldUpdateEpicStatus() {
        TaskManager manager = new InMemoryTaskManager();
        Epic epic = new Epic(EPIC_NAME, EPIC_DESCRIPTION);
        manager.createEpic(epic);

        SubTask subTask = new SubTask(SUBTASK_NAME, SUBTASK_DESCRIPTION, TaskStatus.NEW, epic.getId());
        manager.createSubTask(subTask);

        subTask.setStatus(TaskStatus.DONE);
        manager.updateSubTask(subTask);

        Epic updatedEpic = manager.getEpic(epic.getId());
        assertEquals(TaskStatus.DONE, updatedEpic.getStatus());
    }

    @Test
    @DisplayName("removeTask() должен удалять задачу")
    void removeTask_givenExistingTask_shouldRemoveIt() {
        TaskManager manager = new InMemoryTaskManager();
        Task task = new Task(TASK_NAME, TASK_DESCRIPTION, TaskStatus.NEW);
        manager.createTask(task);

        manager.removeTask(task.getId());

        assertTrue(manager.getAllTasks().isEmpty());
    }

    @Test
    @DisplayName("clearTasks() должен очищать все задачи")
    void clearTasks_whenCalled_shouldEmptyTaskList() {
        TaskManager manager = new InMemoryTaskManager();
        manager.createTask(new Task(TASK_NAME, TASK_DESCRIPTION, TaskStatus.NEW));
        manager.createTask(new Task(TASK_NAME, TASK_DESCRIPTION, TaskStatus.NEW));

        manager.removeAllTasks();

        assertEquals(0, manager.getAllTasks().size());
    }
}