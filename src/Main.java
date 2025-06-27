public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        // проверка taskManager по условиям из практикума

        //p.s. признаюсь, для меня это было сложное задание и без студентов из когорты, которые также выполняли его, я бы не успел
        //но это было крайне интересно! Надеюсь, всё работает хорошо и сделано правильно. Я поскакал впитывать 5 спринт
        //p.s.s. это фз показало, что у меня есть некоторые пробелы в знаниях, поэтому после 5 спринта буду навёртстывать!
        //а учиться нравится, жду фидбек :)


        int taskId1 = taskManager.addTask(new Task("Разобраться с делами по дому","До приезда родителей", 0, TaskStatus.NEW));
        int taskId2 = taskManager.addTask(new Task("Подготовиться к празднику", "Купить продукты и убраться", 0, TaskStatus.NEW));

        Epic epic1 = new Epic("Новая работа", "Устроиться на новую работу", 0, TaskStatus.NEW);
        int epicId1 = taskManager.createEpic(epic1);
        Epic epic2 = new Epic("Успеть закончить 5 спринт", "Дедлайн близко, а ты ещё на 4, МДА!", 0, TaskStatus.NEW);
        int epicId2 = taskManager.createEpic(epic2);

        int subtaskId1 = taskManager.createSubTask(new SubTask("Уволиться со старой работы", "Обязательно!", 0, TaskStatus.NEW, epicId1 ));
        int subtaskId2 = taskManager.createSubTask(new SubTask("Связаться с начальником", "Сообщить дату увольнения", 0, TaskStatus.IN_PROGRESS, epicId1));
        int subtaskId3 = taskManager.createSubTask(new SubTask("Закончить ФЗ 4 спринта", "Написать код и отправиться ревьюеру", 0, TaskStatus.DONE, epicId2));
        int subtaskId4 = taskManager.createSubTask(new SubTask("Кабаном до проверки на 5 спринт", "ПРЯМ КАБАНОМ, БРО! ВЗЯЛ И ПОМЧАЛСЯ, ХРЮ-ХРЮ!", 0, TaskStatus.IN_PROGRESS, epicId2));

        Task task1 = taskManager.getTask(taskId1);
        task1.setStatus(TaskStatus.DONE);
        taskManager.updateTask(task1);

        SubTask subtask1 = taskManager.getSubTask((subtaskId1));
        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubTask(subtask1);

        System.out.println("Список задач: " + taskManager.getAllTasks());
        System.out.println("Список эпиков: " + taskManager.getAllEpics());
        System.out.println("Список подзадач: " + taskManager.getAllSubTasks());

        taskManager.removeTask(taskId1);
        taskManager.removeEpic(epicId2);

        System.out.println("После удаления:");
        System.out.println("Список задач: " + taskManager.getAllTasks());
        System.out.println("Список эпиков: " + taskManager.getAllEpics());
        System.out.println("Список подзадач: " + taskManager.getAllSubTasks());
    }
}
