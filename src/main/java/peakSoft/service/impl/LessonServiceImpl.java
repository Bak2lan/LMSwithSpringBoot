package peakSoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peakSoft.entity.Course;
import peakSoft.entity.Lesson;
import peakSoft.repository.CourseRepo;
import peakSoft.repository.LessonRepo;
import peakSoft.service.LessonService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepo lessonRepo;
    private final CourseRepo courseRepo;

    @Override
    public void saveToCourse(Long courseId, Lesson lesson) {
        Course course = courseRepo.getCoursesById(courseId);
        course.getLessons().add(lesson);
        lesson.setCourse(course);
        lessonRepo.save(lesson);
    }

    @Override
    public List<Lesson> getAllLessons(){
        return lessonRepo.findAll();
    }

    @Override
    public Lesson getLessonById(Long lessonId){
        return lessonRepo.getLessonById(lessonId);
    }

    @Override
    public void updateLesson(Long id, Lesson newLesson){
        Lesson lesson = getLessonById(id);
        lesson.setLessonName(newLesson.getLessonName());
    }

    @Override
    public void deleteLesson(Long id){
            lessonRepo.deleteById(id);
    }
}
