package peakSoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peakSoft.entity.Company;
import peakSoft.entity.Course;
import peakSoft.entity.Lesson;
import peakSoft.entity.Task;
import peakSoft.service.CompanyService;
import peakSoft.service.CourseService;
import peakSoft.service.LessonService;
import peakSoft.service.TaskService;

@Controller
@RequestMapping("/companies/{companyId}/courses/{courseId}/lesson/{lessonId}/get")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final LessonService lessonService;
    private final CourseService courseService;
    private final CompanyService companyService;

    @GetMapping("/create")
    public String createTaskForm(@PathVariable("companyId") Long companyId, @PathVariable("courseId") Long courseId,
                                 @PathVariable("lessonId") Long lessonId, Model model) {
        Company company = companyService.getById(companyId);
        Course course = courseService.getCourseById(courseId);
        Lesson lesson = lessonService.getLessonById(lessonId);
        model.addAttribute("company", company);
        model.addAttribute("course", course);
        model.addAttribute("lesson", lesson);
        model.addAttribute("newTask", new Task());
        return "task/taskForm";

    }

    @PostMapping("/save")
    public String saveTask(@PathVariable("companyId") Long companyId, @PathVariable("courseId") Long courseId,
                           @PathVariable("lessonId") Long lessonId, Model model, @ModelAttribute("newTask")Task task) {

        Company company = companyService.getById(companyId);
        Course course = courseService.getCourseById(courseId);
        Lesson lesson = lessonService.getLessonById(lessonId);
        model.addAttribute("company", company);
        model.addAttribute("course", course);
        model.addAttribute("lesson", lesson);
        taskService.saveTaskLesson(lesson.getId(),task);
        return "redirect:/companies/"+companyId+"/courses/"+courseId+"/get/lessons/"+lessonId+"/get";
    }
    @PostMapping("{id}/delete")
    public String deleteTask(@PathVariable("companyId") Long companyId, @PathVariable("courseId") Long courseId,
                             @PathVariable("lessonId") Long lessonId,@PathVariable("id")Long id, Model model){
        Company company = companyService.getById(companyId);
        Course course = courseService.getCourseById(courseId);
        Lesson lesson = lessonService.getLessonById(lessonId);
        Task task=taskService.getTaskByID(id);
        if (task.getLesson()!=null){
            task.setLesson(null);
        }
        taskService.deleteTask(id);
        return "redirect:/companies/"+companyId+"/courses/"+courseId+"/get/lessons/"+lessonId+"/get";

    }

    @GetMapping("{id}/formUpdate")
    public String formUpdate(@PathVariable("companyId") Long companyId, @PathVariable("courseId") Long courseId,
                             @PathVariable("lessonId") Long lessonId,@PathVariable("id")Long id, Model model){
        Company company = companyService.getById(companyId);
        Course course = courseService.getCourseById(courseId);
        Lesson lesson = lessonService.getLessonById(lessonId);
        model.addAttribute("company", company);
        model.addAttribute("course", course);
        model.addAttribute("lesson", lesson);
        model.addAttribute("updatedTask",taskService.getTaskByID(id));
        return "task/updateFormTask";
    }

    @PostMapping("{id}/update")
    public String updateTask(@PathVariable("companyId") Long companyId, @PathVariable("courseId") Long courseId,
                             @PathVariable("lessonId") Long lessonId,@PathVariable("id")Long id, Model model, @ModelAttribute("updatedTask")Task task){
        Company company = companyService.getById(companyId);
        Course course = courseService.getCourseById(courseId);
        Lesson lesson = lessonService.getLessonById(lessonId);
        model.addAttribute("company", company);
        model.addAttribute("course", course);
        model.addAttribute("lesson", lesson);
        taskService.updateTask(id,task);
        return "redirect:/companies/"+companyId+"/courses/"+courseId+"/get/lessons/"+lessonId+"/get";

    }
}
