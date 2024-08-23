package peakSoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peakSoft.entity.Course;

import java.util.List;
import java.util.Set;
@Repository
public interface CourseRepo extends JpaRepository<Course,Long> {
    @Query("select c from Course c join c.company cc where cc.id=:company_id")
    List<Course>getCoursesByCompanyId(Long company_id);
    @Query("select c from Course c join c.instructor ci where ci.id=:instructorId")
    Course getCoursesByInstructorId(Long instructorId);
    @Query("select c from Course c where c.id=:courseId")
    Course getCoursesById(Long courseId);
}
