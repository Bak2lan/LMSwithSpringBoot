package peakSoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peakSoft.entity.Company;
import peakSoft.entity.Course;
import peakSoft.entity.Group;
import peakSoft.repository.CompanyRepo;
import peakSoft.repository.CourseRepo;
import peakSoft.repository.GroupRepo;
import peakSoft.service.GroupService;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepo groupRepo;
    private final CourseRepo courseRepo;
    private final CompanyRepo companyRepo;

    @Override
    public void saveGroup(Group group) {
            groupRepo.save(group);
    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepo.findAll();
    }

    @Override
    public Group getById(Long id) {
        return groupRepo.findById(id).orElseThrow(
                ()->new NoSuchElementException(String.format("Not found group with %s id",id)));
    }

    @Override
    public void updateGroup(Long id, Group newGroup) {
        Group group = getById(id);
        group.setGroupName(newGroup.getGroupName());
        group.setDescription(newGroup.getDescription());
        group.setImageLink(newGroup.getImageLink());
    }

    @Override
    public void deleteGroup(Long id) {
        groupRepo.deleteById(id);
    }

    @Override
    public void saveGroupToCourse(Long courseId, Group group) {
        Set<Group>groups= new HashSet<>();
        groups.add(group);
        Course course = courseRepo.findById(courseId).orElseThrow(
                () -> new NoSuchElementException(String.format("not found course with %s id", courseId))
        );
       course.setGroups(groups);
       saveGroup(group);

    }

    @Override
    public void saveGroupWithRelations(Group group, Long companyId, Long courseId) {
        Company company = companyRepo.getCompanyById(companyId);
        Course course = courseRepo.getCoursesById(courseId);
        if (course.getGroups()==null){
            course.setGroups(new HashSet<>());
        }
        if (group.getCourses()==null){
            group.setCourses(new ArrayList<>());
        }
        if (company.getCourses()==null){
            company.setCourses(new HashSet<>());
        }
        course.getGroups().add(group);
        group.getCourses().add(course);
        company.getCourses().add(course);

        groupRepo.save(group);
    }
}
