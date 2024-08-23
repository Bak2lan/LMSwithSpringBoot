package peakSoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peakSoft.entity.Lesson;
import peakSoft.entity.Task;
import peakSoft.repository.LessonRepo;
import peakSoft.repository.TaskRepo;
import peakSoft.service.TaskService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepo taskRepo;
    private final LessonRepo lessonRepo;

    @Override
    public void saveTaskLesson(Long lessonId, Task task) {
        Lesson lesson = lessonRepo.getLessonById(lessonId);
        lesson.getTasks().add(task);
        task.setLesson(lesson);
        taskRepo.save(task);
    }

    @Override
    public List<Task> getAllTask() {
        return taskRepo.findAll();
    }

    @Override
    public void updateTask(Long id, Task newTask) {
        Task task = taskRepo.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("Not found task with id %s", id)));
        task.setTaskName(newTask.getTaskName());
        task.setDeadLine(newTask.getDeadLine());
    }

    @Override
    public void deleteTask(Long id) {
        taskRepo.deleteById(id);
    }

    @Override
    public Task getTaskByID(Long id) {
        return taskRepo.getTaskById(id);
    }
}
