package peakSoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peakSoft.entity.Task;

import java.util.List;
@Repository
public interface TaskRepo extends JpaRepository<Task,Long> {

    @Query("select t from Task t where t.id=:id")
    Task getTaskById(Long id);
}
