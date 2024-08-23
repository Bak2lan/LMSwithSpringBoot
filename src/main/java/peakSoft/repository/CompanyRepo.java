package peakSoft.repository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peakSoft.entity.Company;

import java.util.List;

@Repository
public interface CompanyRepo extends JpaRepository<Company,Long> {

    @Query("select c from Company c where c.id=:id")
    Company getCompanyById(Long id);

    @Modifying
    @Transactional
    @Query("delete from Company c where c.id=:id")
    void deleteCompany(@Param("id") Long id);

}
