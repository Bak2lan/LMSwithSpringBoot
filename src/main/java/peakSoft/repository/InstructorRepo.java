package peakSoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peakSoft.entity.Course;
import peakSoft.entity.Instructor;

import java.util.List;
import java.util.Set;
@Repository
public interface InstructorRepo extends JpaRepository<Instructor,Long> {
       @Query("select c from Course c join c.instructor ci where ci.id=:instructorId")
    List<Course>getAllInstructorCourses(Long instructorId);
       List<Instructor>getInstructorByCompaniesId(Long companies_id);
       @Query("select i from Instructor i where i.id=:instructorId")
       Instructor getInstructorById(Long instructorId);

}
