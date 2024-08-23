package peakSoft.service;

import peakSoft.entity.Course;
import peakSoft.entity.Instructor;

import java.util.List;
import java.util.Set;

public interface InstructorService {
    void save(Instructor instructor);
    void assignToCompany(Long companyId,Instructor instructor);
    List<Instructor> getAllInstructors();
    Instructor getInstructorById(Long id);
    void deleteInstructor(Long id);
    void update(Long id,Instructor newInstructor);
    List<Course>getAllInstructorsCourses(Long id);
    List<Instructor>getInstructorsByCompanyId(Long companyId);

}
