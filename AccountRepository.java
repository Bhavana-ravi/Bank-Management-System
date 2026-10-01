package jsp.springboot.bank.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jsp.springboot.bank.project.dto.AccountType;
import jsp.springboot.bank.project.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	
    Optional<Account> findByAccNumber(String accNumber);
    boolean existsByAccNumber(String accNumber);
    List<Account> findByBank_BankId(Integer bankId);
    List<Account> findByAccType(AccountType accType);
    List<Account> findByAccBalanceGreaterThan(Double accBalance);

}