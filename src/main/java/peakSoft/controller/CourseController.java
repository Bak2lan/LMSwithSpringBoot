package peakSoft.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peakSoft.entity.Company;
import peakSoft.entity.Course;
import peakSoft.entity.Instructor;
import peakSoft.entity.Lesson;
import peakSoft.service.CompanyService;
import peakSoft.service.CourseService;
import peakSoft.service.InstructorService;

import java.util.Set;


@Transactional
@Controller
@RequestMapping("/companies/{companyId}/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final CompanyService companyService;
    private final InstructorService instructorService;
    @GetMapping("/create")
    public String create(@PathVariable("companyId") Long companyId, Model model) {
        Company company = companyService.getById(companyId);
        model.addAttribute("company", company);
        model.addAttribute("newCourse", new Course());
        return "course/newCourse";
    }



    @PostMapping("/save")
    public String saveCourse(@PathVariable("companyId") Long companyId, @ModelAttribute("newCourse") Course course) {
        Company company = companyService.getById(companyId);
        course.setCompany(company);
        courseService.saveCourse(course);
        return "redirect:/companies/" + companyId + "/get";
    }
    @GetMapping("/{id}/form")
    public String formToUpdate(@PathVariable("id")Long id,@PathVariable("companyId") Long companyId,Model model){
        model.addAttribute("companyId",companyId);
        model.addAttribute("updatedCourse",courseService.getCourseById(id));
        return "course/courseUpdateForm";
    }

    @PostMapping("{id}/update")
    public String updateCourse(@ModelAttribute("updatedCourse")Course course, @PathVariable("id")Long id, @PathVariable("companyId") Long companyId){
        courseService.updateCourse(id,course);
        return "redirect:/companies/"+ companyId+"/get";
    }
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id")Long id,@PathVariable("companyId")Long companyId ){
        Course course=courseService.getCourseById(id);
        Instructor instructor = course.getInstructor();
        if (instructor!=null){
            course.setInstructor(null);
            courseService.saveCourse(course);
        }
        courseService.delete(id);
        course.setCompany(null);
        return "redirect:/companies/"+companyId+"/get";
    }

    @GetMapping("{courseId}/get")
    public String getCourse(@PathVariable("courseId")Long courseId,
                            @PathVariable("companyId")Long companyId,
                            Model model){
        Company company=companyService.getById(companyId);
        Course course=courseService.getCourseById(courseId);
        Set<Lesson> lessons = course.getLessons();
        model.addAttribute("course",course);
        model.addAttribute("company",company);
        model.addAttribute("allInstructors",company.getInstructors());
        model.addAttribute("instructors",course.getInstructor());
        model.addAttribute("allGroupsCourses",course.getGroups());
        model.addAttribute("allLessons", lessons);
        return "course/getCourse";
    }


    @PostMapping("{id}/assign")
    public String createFormToAssign(@PathVariable("companyId")Long companyId,@PathVariable("id")Long id
            ,@RequestParam("instructorId")Long instructorId,Model model){
        Company company=companyService.getById(companyId);
        Course course=courseService.getCourseById(id);
        Instructor instructor=instructorService.getInstructorById(instructorId);
        model.addAttribute("company",company);
        model.addAttribute("course",course);
        model.addAttribute("instructor",instructor);
        courseService.assignInstructorToCourse(instructorId,id);
        return "redirect:/companies/"+companyId+"/courses/"+id+"/get";
    }

}
