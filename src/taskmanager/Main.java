package taskmanager;

import taskmanager.manager.Manager;
import taskmanager.model.Enums.Status;
import taskmanager.model.Epic;
import taskmanager.model.Subtask;
import taskmanager.model.Task;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        Task task2 = new Task("t2", "tt2");
        task2.setStatus(Status.DONE);
        Task task1 = new Task("t1", "tt1");
        task1.setStatus(Status.DONE);
        Epic epic2 = new Epic("e2", "ee2");
        Epic epic1 = new Epic("e1", "ee1");
        Subtask subtask1 = new Subtask("s1", "ss1", Status.NEW, 1);
        Subtask subtask2 = new Subtask("s2", "ss2", Status.IN_PROGRESS, 1);
        Subtask subtask3 = new Subtask("s3", "ss3", Status.DONE, 2);

        manager.createEpic(epic2);
        manager.createEpic(epic1);
        manager.createTask(task2);
        manager.createTask(task1);
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);
        manager.createSubtask(subtask3);
        //manager.delByIdTask(3);
        //manager.delByIdEpic(1);

        System.out.println(manager.getEpics());
        System.out.println(manager.listSubtaskOfEpic(1));
        System.out.println(manager.listSubtaskOfEpic(2));
        System.out.println(manager.getTasks());
        System.out.println(manager.getSubtasks());
        System.out.println(manager.getByIdTask(3));

    }
}
