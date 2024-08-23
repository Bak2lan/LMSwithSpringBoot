package peakSoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peakSoft.entity.Group;

import java.util.List;
@Repository
public interface GroupRepo extends JpaRepository<Group,Long> {

}
