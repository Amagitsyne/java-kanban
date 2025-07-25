import ru.yandex.course.model.Epic;
import ru.yandex.course.model.SubTask;
import ru.yandex.course.model.Task;
import ru.yandex.course.model.TaskStatus;
import ru.yandex.course.service.InMemoryTaskManager;
import ru.yandex.course.service.TaskManager;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new InMemoryTaskManager();
        //Добавил Managers, HistoryManager, InMemoryHistoryManager в service (вопрос: может, лучше отправить их в
        //ru.yandex.course.managers? Не силён в этой теме, вдруг так будет лучше? Выглядит как отдельная тему, не уверен)
        //Добавил также категорию тестов InMemoryTaskManagerHistoryTest, но видел замечание, что не все методы в
        //InMemoryTaskManagerTest были покрыты тестами. Значит, желательно протестировать все методы из InMemoryTaskManager?
        //Или только какие-то определённые?
        //P.S. спасибо за помощь, Максим! Очень благодарен, а то уже дедлайн, думал что вообще свихнусь.
        //Даже часть ТЗ забыл...

        Task task1 = new Task("Сохранить ментальное здоровье", "Курсы", TaskStatus.NEW);
        Task task2 = new Task("Закрыть 5 спринт до 14 числа (включительно, я надеюсь)", "Курсы", TaskStatus.NEW);
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        Epic epic1 = new Epic("Курсы", "Сделать всё");
        taskManager.createEpic(epic1);

        SubTask sub1 = new SubTask("Не спать", "Сон", TaskStatus.NEW, epic1.getId());
        SubTask sub2 = new SubTask("Помогите", "Помощь", TaskStatus.NEW, epic1.getId());
        taskManager.createSubTask(sub1);
        taskManager.createSubTask(sub2);

        System.out.println("Все задачи: " + taskManager.getAllTasks());
        System.out.println("Все эпики: " + taskManager.getAllEpics());
        System.out.println("Все подзадачи эпика: " + taskManager.getEpicSubTasks(epic1.getId()));

        sub1.setStatus(TaskStatus.DONE);
        taskManager.updateSubTask(sub1);

        Epic updatedEpic = taskManager.getEpic(epic1.getId());
        System.out.println("Обновлённый статус эпика: " + updatedEpic.getStatus());
    }
}