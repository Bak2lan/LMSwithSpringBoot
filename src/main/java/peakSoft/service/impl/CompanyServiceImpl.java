package peakSoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peakSoft.entity.Company;
import peakSoft.repository.CompanyRepo;
import peakSoft.service.CompanyService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepo companyRepo;


    @Override
    public void saveCompany(Company company) {
        companyRepo.save(company);
    }

    @Override
    public List<Company> getAllCompany() {
        return companyRepo.findAll();
    }

    @Override
    public Company getById(Long id) {
     return   companyRepo.getCompanyById(id);
    }

    @Override
    public void updateCompany(Long id, Company newCompany) {
        Company company = getById(id);
        company.setName(newCompany.getName());
        company.setAddress(newCompany.getAddress());
        company.setImage(newCompany.getImage());
        company.setPhoneNumber(newCompany.getPhoneNumber());
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepo.deleteCompany(id);
    }
}
