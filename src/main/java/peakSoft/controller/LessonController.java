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
@RequestMapping("/companies/{companyId}/courses/{courseId}/get/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;
    private final CourseService courseService;
    private final CompanyService companyService;
    private final TaskService taskService;

    @GetMapping("/createLesson")
    public String createFormLesson(@PathVariable("companyId")Long companyId,@PathVariable("courseId")Long courseId,Model model){
        Company company=companyService.getById(companyId);
        Course course= courseService.getCourseById(courseId);
        model.addAttribute("companyForLesson",company);
        model.addAttribute("courseForLesson",course);
        model.addAttribute("newLesson",new Lesson());
        return "lesson/newLesson";
    }

    @PostMapping("/saveLesson")
    public String save(@ModelAttribute("newLesson")Lesson lesson, @PathVariable("companyId")Long companyId, @PathVariable("courseId")Long courseId,Model model){
        Company company=companyService.getById(companyId);
        Course course= courseService.getCourseById(courseId);
        model.addAttribute("companyForLesson",company);
        model.addAttribute("courseForLesson",course);
        lessonService.saveToCourse(courseId,lesson);
        return "redirect:/companies/"+companyId+"/courses/"+courseId+"/get";
    }
    @GetMapping("{id}/update")
    public String updateFormLesson(@PathVariable("id")Long id,@PathVariable("companyId")Long companyId,@PathVariable("courseId")Long courseId ,Model model){
        Company company=companyService.getById(companyId);
        Course course= courseService.getCourseById(courseId);
        model.addAttribute("companyForLesson",company);
        model.addAttribute("courseForLesson",course);
        model.addAttribute("updatedLesson",lessonService.getLessonById(id));
        return "lesson/updateForm";

    }
    @PostMapping("/updatedLesson/{id}")
    public String updateLesson(@ModelAttribute("updatedLesson")Lesson lesson,@PathVariable("id")Long id,@PathVariable("companyId")Long companyId,@PathVariable("courseId")Long courseId ,Model model){
        Company company=companyService.getById(companyId);
        Course course= courseService.getCourseById(courseId);
        model.addAttribute("companyForLesson",company);
        model.addAttribute("courseForLesson",course);
        lessonService.updateLesson(id,lesson);
        return "redirect:/companies/"+companyId+"/courses/"+courseId+"/get";
    }
    @PostMapping("{id}/delete")
    public String deleteLesson(@PathVariable("id")Long id,@PathVariable("companyId")Long companyId,@PathVariable("courseId")Long courseId ,Model model) {
        Company company = companyService.getById(companyId);
        Course course = courseService.getCourseById(courseId);
        Lesson lesson = lessonService.getLessonById(id);
        model.addAttribute("companyForLesson", company);
        model.addAttribute("courseForLesson", course);
        course.getLessons().remove(lesson);
        courseService.saveCourse(course);
        for (Task task:lesson.getTasks()){
            taskService.deleteTask(task.getId());
        }
        System.out.println("Deleted");
       lessonService.deleteLesson(id);

        return "redirect:/companies/" + companyId + "/courses/" + courseId + "/get";
    }
    @GetMapping("{id}/get")
    public String getLesson(@PathVariable("companyId") Long companyId, @PathVariable("courseId") Long courseId,
                           @PathVariable("id")Long id,Model model){
        Company company = companyService.getById(companyId);
        Course course = courseService.getCourseById(courseId);
        Task task = taskService.getTaskByID(id);
        Lesson lesson = lessonService.getLessonById(id);
        model.addAttribute("companyForLesson", company);
        model.addAttribute("courseForLesson", course);
        model.addAttribute("allTasks",lesson.getTasks());
        model.addAttribute("task",task);
        System.out.println(lesson.getTasks());

        return "lesson/getLesson";
    }

}
