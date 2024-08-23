package peakSoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peakSoft.entity.Group;
import peakSoft.entity.Student;
import peakSoft.repository.GroupRepo;
import peakSoft.repository.StudentRepo;
import peakSoft.service.StudentService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepo studentRepo;
    private final GroupRepo groupRepo;

    @Override
    public void save(Student student) {
        studentRepo.save(student);
    }

    @Override
    public void saveStudentToGroup(Long groupId, Student student) {
        Group group = groupRepo.findById(groupId).orElseThrow(() -> new NoSuchElementException(String.format("not found group with id %s", groupId)));
        group.getStudents().add(student);
        student.setGroup(group);
        studentRepo.save(student);
    }

    @Override
    public List<Student> gatAllStudent() {
        return studentRepo.findAll();
    }

    @Override
    public Student getByIdStudent(Long id) {
        return studentRepo.getStudentById(id);

    }

    @Override
    public void deleteStudent(Long id) {
        studentRepo.deleteById(id);

    }

    @Override
    public void updateStudent(Long id, Student newStudent) {
        Student byIdStudent = getByIdStudent(id);
        byIdStudent.setFirstName(newStudent.getFirstName());
        byIdStudent.setLastName(newStudent.getLastName());
        byIdStudent.setPhoneNumber(newStudent.getPhoneNumber());
        byIdStudent.setEmail(newStudent.getEmail());
        byIdStudent.setStudyFormat(newStudent.getStudyFormat());
    }
}
