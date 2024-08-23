package peakSoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peakSoft.entity.Lesson;

import java.util.List;
@Repository
public interface LessonRepo  extends JpaRepository<Lesson,Long> {
    @Query("select l from Lesson l where l.id=:lessonId")
    Lesson getLessonById(Long lessonId);

}
