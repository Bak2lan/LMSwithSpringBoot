package peakSoft.controller;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peakSoft.entity.Company;
import peakSoft.entity.Course;
import peakSoft.entity.Group;
import peakSoft.entity.Student;
import peakSoft.service.CompanyService;
import peakSoft.service.CourseService;
import peakSoft.service.GroupService;
import peakSoft.service.StudentService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Controller
@RequestMapping("/companies/{companyId}/courses/{courseId}/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final CourseService courseService;
    private final CompanyService companyService;
    private final StudentService studentService;
    @GetMapping("/formGroup")
    public String formGroup(Model model, @PathVariable("companyId")Long companyId,@PathVariable("courseId")Long courseId){
        model.addAttribute("newGroup",new Group());
        Course course = courseService.getCourseById(courseId);
        Company company = companyService.getById(companyId);
        Set<Course> courses= new HashSet<>();
        courses.add(course);
        company.setCourses(courses);
        model.addAttribute("company",company);
        model.addAttribute("course", course);
        return "group/newGroup";
    }
    @PostMapping("/saveGroup")
    public String saveGroup(@ModelAttribute("newGroup")Group group, @PathVariable("companyId")Long companyId, @PathVariable("courseId")Long courseId,Model model) {

        Course course = courseService.getCourseById(courseId);
        Company company = companyService.getById(companyId);

        model.addAttribute("company",company);
        model.addAttribute("course",course);

        groupService.saveGroupWithRelations(group,companyId,courseId);
        return "redirect:/companies/"+companyId+"/courses/"+courseId+"/get";
    }

    @GetMapping("/{id}/updateForm")
    public String updatedForm(@PathVariable("id")Long id,@PathVariable("companyId")Long companyId,@PathVariable("courseId")Long courseId,Model model){
        Course course = courseService.getCourseById(courseId);
        Company company=companyService.getById(companyId);
        model.addAttribute("company",company);
        model.addAttribute("course",course);
        model.addAttribute("updatedGroup",groupService.getById(id));
        return "group/updateGroup";
    }
    @PostMapping("/{id}/updateGroup")
    public String updateGroup(@PathVariable("id")Long id,@ModelAttribute("updatedGroup")Group group,@PathVariable("companyId")Long companyId,@PathVariable("courseId")Long courseId,Model model){
        Course course = courseService.getCourseById(courseId);
        Company company=companyService.getById(companyId);
        model.addAttribute("company",company);
        model.addAttribute("course",course);
        groupService.updateGroup(id,group);
        return "redirect:/companies/"+companyId+"/courses/"+courseId+"/get";

    }

    @PostMapping("/deleteGroup/{id}")
    public String deleteGroup(@PathVariable("id")Long id,@PathVariable("companyId")Long companyId,@PathVariable("courseId")Long courseId,Model model){
        Course course = courseService.getCourseById(courseId);
        Company company=companyService.getById(companyId);
        Group group=groupService.getById(id);
        model.addAttribute("company",company);
        model.addAttribute("course",course);
        course.getGroups().remove(group);
        groupService.deleteGroup(id);
       return "redirect:/companies/"+companyId+"/courses/"+courseId+"/get";
    }

    @GetMapping("{id}/getGroup")
    public String getGroup(@PathVariable("id")Long id,@PathVariable("companyId")Long companyId,@PathVariable("courseId")Long courseId,Model model){
        Course course = courseService.getCourseById(courseId);
        Company company=companyService.getById(companyId);
        Student student=studentService.getByIdStudent(id);
        model.addAttribute("company",company);
        model.addAttribute("student",student);
        model.addAttribute("course",course);
        Group group = groupService.getById(id);
        model.addAttribute("allStudents",group.getStudents());
        return "group/getGroup";
    }
    @GetMapping("/formToAssign")
    public String formForAssign(@PathVariable("companyId")Long companyId,@PathVariable("courseId")Long courseId,Model model){
        Company company = companyService.getById(companyId);
        Course course = courseService.getCourseById(courseId);
        model.addAttribute("newGroupToAssign",new Group() );
        model.addAttribute("company",company);
        model.addAttribute("course",course);
        model.addAttribute("allCourses",company.getCourses());
        return "formToAssign";
    }

    //TODO create formToAssign

    @PostMapping("/assign")
    public String assignGroup(@ModelAttribute("newGroupToAssign")Group group,@PathVariable("companyId")Long companyId,@PathVariable("courseId")Long courseId ,Model model){
        Company company = companyService.getById(companyId);
        Course course = courseService.getCourseById(courseId);
        model.addAttribute("company",company);
        model.addAttribute("course",course);
        groupService.saveGroupToCourse(courseId,group);
        return "redirect:/companies/"+companyId+"/courses/"+courseId+"/get";
    }
}
