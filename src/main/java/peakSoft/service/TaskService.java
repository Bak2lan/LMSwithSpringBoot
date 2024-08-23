package peakSoft.service;

import peakSoft.entity.Task;

import java.util.List;

public interface TaskService {
    void saveTaskLesson(Long lessonId, Task task);
    List<Task>getAllTask();
    void updateTask(Long id, Task newTask);
    void deleteTask(Long id);
    Task getTaskByID(Long id);

}
