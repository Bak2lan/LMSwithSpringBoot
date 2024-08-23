package peakSoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peakSoft.entity.Company;
import peakSoft.entity.Course;
import peakSoft.entity.Group;
import peakSoft.entity.Student;
import peakSoft.enums.StudyFormat;
import peakSoft.service.CompanyService;
import peakSoft.service.CourseService;
import peakSoft.service.GroupService;
import peakSoft.service.StudentService;

@Controller
@RequestMapping("/companies/{companyId}/courses/{courseId}/groups/{groupId}/students")
@RequiredArgsConstructor()
public class StudentController {
    private final CompanyService companyService;
    private final CourseService courseService;
    private final GroupService groupService;
    private final StudentService studentService;

    @GetMapping("/create")
    public String createStudentForm(@PathVariable("companyId") Long companyId, @PathVariable("courseId") Long courseId,
                                    @PathVariable("groupId") Long groupId, Model model) {
        Company company = companyService.getById(companyId);
        Course course = courseService.getCourseById(courseId);
        Group group = groupService.getById(groupId);
        model.addAttribute("company", company);
        model.addAttribute("course", course);
        model.addAttribute("group", group);
        model.addAttribute("studyFormat", StudyFormat.values());
        model.addAttribute("newStudent", new Student());

        return "student/studentForm";
    }

    @PostMapping("/save")
    public String saveStudent(@PathVariable("companyId") Long companyId, @PathVariable("courseId") Long courseId,
                              @PathVariable("groupId") Long groupId, @ModelAttribute("newStudent") Student student, Model model) {
        Company company = companyService.getById(companyId);
        Course course = courseService.getCourseById(courseId);
        Group group = groupService.getById(groupId);
        model.addAttribute("company", company);
        model.addAttribute("course", course);
        model.addAttribute("group", group);
        student.setGroup(group);
        group.getStudents().add(student);
        studentService.saveStudentToGroup(groupId, student);
        System.out.println(group.getStudents());
        return "redirect:/companies/" + companyId + "/courses/" + courseId + "/groups/" + groupId + "/getGroup";
    }

    @GetMapping({"/updateForm/{id}"})
    public String updateStudentForm(@PathVariable("companyId") Long companyId, @PathVariable("courseId") Long courseId,
                                @PathVariable("groupId") Long groupId, @PathVariable("id") Long id, Model model) {
        Company company = companyService.getById(companyId);
        Course course = courseService.getCourseById(courseId);
        Group group = groupService.getById(groupId);
        model.addAttribute("company", company);
        model.addAttribute("course", course);
        model.addAttribute("group", group);
        model.addAttribute("studyFormat",StudyFormat.values());
        model.addAttribute("updatedStudent", studentService.getByIdStudent(id));
        return "student/updateForm";

    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable("companyId") Long companyId, @PathVariable("courseId") Long courseId,
                                @PathVariable("groupId") Long groupId,@PathVariable("id")Long id, @ModelAttribute("updatedStudent")Student student, Model model){
        Company company = companyService.getById(companyId);
        Course course = courseService.getCourseById(courseId);
        Group group = groupService.getById(groupId);
        model.addAttribute("company", company);
        model.addAttribute("course", course);
        model.addAttribute("group", group);
        studentService.updateStudent(id,student);
        return "redirect:/companies/" + companyId + "/courses/" + courseId + "/groups/" + groupId + "/getGroup";

    }
    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("companyId") Long companyId, @PathVariable("courseId") Long courseId,
                                @PathVariable("groupId") Long groupId, @PathVariable("id") Long id, Model model){
        Company company = companyService.getById(companyId);
        Course course = courseService.getCourseById(courseId);
        Group group = groupService.getById(groupId);
        Student student=studentService.getByIdStudent(id);
        model.addAttribute("company", company);
        model.addAttribute("course", course);
        model.addAttribute("group", group);
        group.getStudents().remove(student);
        studentService.deleteStudent(id);
        return "redirect:/companies/" + companyId + "/courses/" + courseId + "/groups/" + groupId + "/getGroup";

    }
}
