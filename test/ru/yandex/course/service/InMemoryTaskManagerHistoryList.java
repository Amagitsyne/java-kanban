package ru.yandex.course.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.course.model.Epic;
import ru.yandex.course.model.SubTask;
import ru.yandex.course.model.Task;
import ru.yandex.course.model.TaskStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static ru.yandex.course.model.TaskStatus.NEW;

class InMemoryTaskManagerHistoryTest {
    private TaskManager manager;
    private static final String TASK_TITLE = "Задача";
    private static final String TASK_DESCRIPTION = "Описание задачи";
    @BeforeEach
    void setUp() {
        manager = new InMemoryTaskManager();
    }
    private Task createAndReturnTask(String title, String description, TaskStatus status) {
        Task task = new Task(title, description, status);
        manager.createTask(task);
        return task;
    }
    private void performMultipleTaskGets(Task task, int times) {
        for (int i = 0; i < times; i++) {
            manager.getTask(task.getId());
        }
    }
    @Test
    @DisplayName("getTask() должен добавлять задачу в историю")
    void getTask_shouldAddToHistory() {
        // Given
        Task task = createAndReturnTask(TASK_TITLE, TASK_DESCRIPTION, NEW);
        // When
        manager.getTask(task.getId());
        // Then
        List<Task> history = manager.getHistory();
        assertEquals(1, history.size(), "История должна содержать 1 задачу");
        assertEquals(task, history.get(0), "Добавленная задача должна быть в истории");
    }
    @Test
    @DisplayName("История не должна содержать более 10 элементов")
    void historyShouldNotExceedSizeLimit() {
        // Given - Создаем 15 задач и вызываем getTask для каждой
        // When
        for (int i = 0; i < 15; i++) {
            Task task = createAndReturnTask(TASK_TITLE + i, TASK_DESCRIPTION, NEW);
            manager.getTask(task.getId());
        }
        // Then
        List<Task> history = manager.getHistory();
        assertEquals(10, history.size(), "История не должна превышать 10 элементов");
    }
    @Test
    @DisplayName("Повторные вызовы getTask() не должны создавать дубли в истории")
    void repeatedGetTask_shouldNotCreateDuplicatesInHistory() {
        // Given
        Task task = createAndReturnTask(TASK_TITLE, TASK_DESCRIPTION, NEW);
        // When
        performMultipleTaskGets(task, 3);
        // Then
        List<Task> history = manager.getHistory();
        assertEquals(1, history.size(), "В истории должна быть только одна запись");
        assertEquals(task, history.get(0), "Задача не должна дублироваться в истории");
    }
    @Test
    @DisplayName("getEpic() должен добавлять эпик в историю")
    void getEpic_shouldAddToHistory() {
        // Given
        Epic epic = new Epic("Эпик", "Описание эпика");
        manager.createEpic(epic);
        // When
        manager.getEpic(epic.getId());
        // Then
        List<Task> history = manager.getHistory();
        assertEquals(1, history.size(), "История должна содержать 1 элемент");
        assertEquals(epic, history.get(0), "Эпик должен быть добавлен в историю");
    }
    @Test
    @DisplayName("getSubTask() должен добавлять подзадачу в историю")
    void getSubTask_shouldAddToHistory() {
        // Given
        Epic epic = new Epic("Эпик", "Описание эпика");
        manager.createEpic(epic);
        SubTask subTask = new SubTask("Подзадача", "Описание", NEW, epic.getId());
        manager.createSubTask(subTask);
        // When
        manager.getSubTask(subTask.getId());
        // Then
        List<Task> history = manager.getHistory();
        assertEquals(1, history.size(), "История должна содержать 1 элемент");
        assertEquals(subTask, history.get(0), "Подзадача должна быть добавлена в историю");
    }
    @Test
    @DisplayName("История корректно работает с разными типами задач")
    void historyWorksCorrectlyWithAllTaskTypes() {
        // Given
        Epic epic = new Epic("Эпик", "Описание");
        manager.createEpic(epic);
        SubTask subTask = new SubTask("Подзадача", "Описание", NEW, epic.getId());
        manager.createSubTask(subTask);
        Task task = createAndReturnTask("Задача", "Описание", NEW);
        // When
        manager.getEpic(epic.getId());
        manager.getSubTask(subTask.getId());
        manager.getTask(task.getId());
        // Then
        List<Task> history = manager.getHistory();
        assertEquals(3, history.size(), "История должна содержать 3 разных типа задач");
        assertSame(epic, history.get(0), "Первым в истории должен быть эпик");
        assertSame(subTask, history.get(1), "Вторым в истории должна быть подзадача");
        assertSame(task, history.get(2), "Третьей в истории должна быть обычная задача");
    }
}
