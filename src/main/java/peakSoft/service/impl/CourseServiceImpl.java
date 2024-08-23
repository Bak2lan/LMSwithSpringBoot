package peakSoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peakSoft.entity.Course;
import peakSoft.entity.Instructor;
import peakSoft.repository.CourseRepo;
import peakSoft.repository.InstructorRepo;
import peakSoft.service.CourseService;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepo courseRepo;
    private final InstructorRepo instructorRepo;

    @Override
    public void saveCourse(Course course) {
        courseRepo.save(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepo.getCoursesById(id);
        }

    @Override
    public void updateCourse(Long id, Course newCourse) {
        Course course = getCourseById(id);
        course.setCourseName(newCourse.getCourseName());
        course.setDescription(newCourse.getDescription());
        course.setDateOfStart(newCourse.getDateOfStart());
    }

    @Override
    public void delete(Long id) {
        courseRepo.deleteById(id);
    }

    @Override
    public void assignInstructorToCourse(Long instructorId, Long courseId) {
        Course course = getCourseById(courseId);
        Instructor instructor = instructorRepo.findById(instructorId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Not found instructor with %s id", instructorId)));
        Set<Course> courses = instructor.getCourses();
        if (courses==null){
            courses=new HashSet<>();

        }
        courses.add(course);
        instructor.setCourses(courses);

        instructorRepo.save(instructor);
        courseRepo.save(course);
    }

    @Override
    public List<Course> getCoursesByCompanyId(Long companyId) {
       return courseRepo.getCoursesByCompanyId(companyId);
    }

    @Override
    public void deleteAll(Set<Course> courses) {
            courseRepo.deleteAll();
    }

    @Override
    public Course getCourseByInstructorId(Long instID) {
        return courseRepo.getCoursesByInstructorId(instID);
    }


}
