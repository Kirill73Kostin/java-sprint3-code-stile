package taskmanager.manager;

import taskmanager.model.Epic;
import taskmanager.model.Subtask;
import taskmanager.model.Task;
import taskmanager.model.Enums.Status;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    private static HashMap<Integer, Task> taskMap = new HashMap<>();
    private static HashMap<Integer, Epic> epicMap = new HashMap<>();
    private static HashMap<Integer, Subtask> subtaskMap = new HashMap<>();

    private int nextId = 1;


    public int createTask(Task task) {
        generateId(task);
        taskMap.put(task.getId(), task);
        return task.getId();
    }

    public int createEpic(Epic epic) {
        generateId(epic);

        if (epic.getSubtaskIdsSize() == 0) {
            epic.setStatus(Status.NEW);
        }
        epicMap.put(epic.getId(), epic);
        return epic.getId();
    }

    public int createSubtask(Subtask subtask) {
        generateId(subtask);
        Epic epic = epicMap.get(subtask.getEpicId());
        epic.addSubtaskId(subtask.getId());
        subtaskMap.put(subtask.getId(), subtask);
        updateEpicStatus(epic);
        return subtask.getId();
    }

    public void updateTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        epicMap.put(epic.getId(), epic);
    }

    private void updateEpicStatus(Epic epic) {
        int n = 0;
        int done = 0;
        if ((epic.getSubtaskIds() == null) || (subtaskMap.size() == 0)) {
            epic.setStatus(Status.NEW);
        } else {

            for (Integer subtaskId : epic.getSubtaskIds()) {
                Subtask curSub = subtaskMap.get(subtaskId);
                if (curSub.getStatus() == Status.IN_PROGRESS) {
                    epic.setStatus(Status.IN_PROGRESS);
                }
                if (curSub.getStatus() == Status.NEW) {
                    n += 1;
                }
                if (curSub.getStatus() == Status.DONE) {
                    done += 1;
                }
            }
        }
        if (epic.getSubtaskIdsSize() == n) {
            epic.setStatus(Status.NEW);
        } else {
            if (epic.getSubtaskIdsSize() == done) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
    }

    public void updateSubtask(Subtask subtask) {
        subtaskMap.put(subtask.getId(), subtask);
        updateEpicStatus(epicMap.get(subtask.getEpicId()));
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(taskMap.values());
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtaskMap.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epicMap.values());
    }

    public void delAllTasks() {
        taskMap.clear();
    }

    public void delAllSubtasks() {
        subtaskMap.clear();
        for (Epic epic : epicMap.values()) {
            epic.setStatus(Status.NEW);
        }
    }

    public void delAllEpics() {
        epicMap.clear();
        subtaskMap.clear();
    }

    public Task getByIdTask(Integer id) {
        return (taskMap.get(id));
    }

    public Subtask getByIdSubtask(Integer id) {
        return (subtaskMap.get(id));
    }

    public Epic getByIdEpic(Integer id) {
        return (epicMap.get(id));
    }

    public void delByIdTask(Integer id) {
        taskMap.remove(id);
    }

    public void delByIdSubtask(Integer id) {
        Epic e = epicMap.get(subtaskMap.get(id).getEpicId());
        ArrayList<Integer> stIds = e.getSubtaskIds();
        stIds.remove(id);
        updateEpicStatus(e);
        subtaskMap.remove(id);
    }

    public void delByIdEpic(Integer id) {
        ArrayList<Integer> stIds = epicMap.get(id).getSubtaskIds();
        for (Integer s : stIds) {
            subtaskMap.remove(s);
        }
        epicMap.remove(id);
    }

    public ArrayList<Subtask> listSubtaskOfEpic(Integer id) {
        ArrayList<Subtask> subTList = new ArrayList<>();
        if (epicMap.get(id) != null) {
            Epic e = epicMap.get(id);
            ArrayList<Integer> subIds = e.getSubtaskIds();
            for (Integer sId : subIds) {
                subTList.add(subtaskMap.get(sId));
            }
        }

        return (subTList);
    }

    private int generateId(Task task) {
        task.setId(nextId);
        nextId++;
        return nextId;
    }

}
