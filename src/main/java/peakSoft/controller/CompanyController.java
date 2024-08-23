package peakSoft.controller;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peakSoft.entity.Company;
import peakSoft.service.CompanyService;
import peakSoft.service.CourseService;
import peakSoft.service.InstructorService;

@Transactional
@Controller
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final CourseService courseService;
    private final InstructorService instructorService;

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("allCompanies", companyService.getAllCompany());
        return "mainPage";
    }

    @GetMapping("{id}/get")
    public String getCompany(@PathVariable("id") Long id, Model model) {
        model.addAttribute("getCompany", companyService.getById(id));
        model.addAttribute("courses",companyService.getById(id).getCourses());
        model.addAttribute("allInst",companyService.getById(id).getInstructors());
        return "company/getCompany";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("newCompany", new Company());
        return "company/newCompany";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("newCompany") Company company) {
        companyService.saveCompany(company);
        return "redirect:/companies";
    }
    @GetMapping("{id}/getUpdate")
    public String getOldCompany(@PathVariable("id")Long id,Model model){
        model.addAttribute("getOldCompany",companyService.getById(id));
        return "company/getOldCompany";
    }
    @PostMapping("{id}/update")
    public String update(@ModelAttribute("getOldCompany")Company company,@PathVariable("id")Long id){
       companyService.updateCompany(id,company);
       return "redirect:/companies";

    }
    @PostMapping("{id}/delete")
    public String delete(@PathVariable("id")Long id){
        companyService.deleteCompany(id);
        return "redirect:/companies";
    }



}
