package peakSoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peakSoft.entity.Company;
import peakSoft.entity.Course;
import peakSoft.entity.Instructor;
import peakSoft.enums.Specialization;
import peakSoft.service.CompanyService;
import peakSoft.service.CourseService;
import peakSoft.service.InstructorService;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/companies")
@RequiredArgsConstructor
public class InstructorController {
    private final CompanyService companyService;
    private final InstructorService instructorService;
    private final CourseService courseService;


    @GetMapping("/instructor/create/{companyId}")
    public String createForm(Model model,@PathVariable("companyId")Long companyId){
        Company company = companyService.getById(companyId);
        model.addAttribute("company",company);
        model.addAttribute("specializations", Specialization.values());
        model.addAttribute("allComp",companyService.getAllCompany());
        model.addAttribute("newInstructor",new Instructor());
        return "instructor/instructorForm";


    }
    @PostMapping("/save/{companyId}")
    public String saveInstructor(@PathVariable("companyId")Long companyId, @ModelAttribute("newInstructor")Instructor instructor,Model model){
        Company company=companyService.getById(companyId);
        model.addAttribute("companyInst",company);
        instructorService.assignToCompany(companyId,instructor);

        return "redirect:/companies/"+companyId+"/get";
    }

    @GetMapping("{companyId}/form/{id}")
    public String formToUpdateInstructor(Model model,@PathVariable("companyId")Long companyId,@PathVariable("id")Long id){
        model.addAttribute("updatedInstructor",instructorService.getInstructorById(id));
        Company company=companyService.getById(companyId);
        model.addAttribute("company",company);
        model.addAttribute("specializations",Specialization.values());
        return "instructor/updateInstructorForm";

    }

    @PostMapping("{companyId}/update/{id}")
    public String updateInstructor(Model model,@PathVariable("companyId")Long companyId , @PathVariable("id")Long id,@ModelAttribute("updatedInstructor")Instructor instructor){
        Company company=companyService.getById(companyId);
        model.addAttribute("company",company);
        instructorService.update(id,instructor);
        return "redirect:/companies/"+companyId+"/get";
    }

    @PostMapping("{companyId}/delete/{id}")
    public String delete(@PathVariable("id") Long id, @PathVariable("companyId") Long companyId, Model model) {
        Company company = companyService.getById(companyId);
        Instructor instructor = instructorService.getInstructorById(id);

        for (Company c : instructor.getCompanies()) {
            c.getInstructors().remove(instructor);
            companyService.saveCompany(c);
        }
        Set<Course> courses = instructor.getCourses();
        for (Course course:courses){
            course.setInstructor(null);
            courseService.saveCourse(course);
        }
        instructorService.deleteInstructor(id);

        return "redirect:/companies/" + companyId + "/get";
    }

    @GetMapping("/{companyId}/get/{id}")

    public String getAllCourses(@PathVariable("companyId")Long companyId,@PathVariable("id")Long id,Model model){
        Company company=companyService.getById(companyId);
        Instructor instructor=instructorService.getInstructorById(id);
        model.addAttribute("allCourses", instructor.getCourses());
        model.addAttribute("company",company);
        model.addAttribute("instructor",instructor);

        return "instructor/getInstructor";

    }



}
