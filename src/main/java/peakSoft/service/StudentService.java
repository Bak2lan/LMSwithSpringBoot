package peakSoft.service;

import peakSoft.entity.Student;

import java.util.List;

public interface StudentService {
    void save(Student student);
    void saveStudentToGroup(Long groupId, Student student);
    List<Student> gatAllStudent();
    Student getByIdStudent(Long id);
    void deleteStudent(Long id);
    void updateStudent(Long id,Student newStudent);
}
