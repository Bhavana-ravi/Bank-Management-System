package jsp.springboot.bank.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jsp.springboot.bank.project.entity.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {
    Optional<Bank> findByIfscCode(String ifscCode);
    Optional<Bank> findByBankContactNumber(String bankContactNumber);
    Optional<Bank> findByAddressId_AddressId(Integer addressId);
    List<Bank> findByAddressId_City(String city);
    boolean existsByIfscCode(String ifscCode);
    boolean existsByBankContactNumber(String bankContactNumber);
}