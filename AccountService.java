package jsp.springboot.bank.project.service;

import java.util.List; 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jsp.springboot.bank.project.dto.AccountType;
import jsp.springboot.bank.project.dto.ResponseStructure;
import jsp.springboot.bank.project.dto.TransactionDetails;
import jsp.springboot.bank.project.dto.TransferDetails;
import jsp.springboot.bank.project.entity.Account;
import jsp.springboot.bank.project.entity.Bank;
import jsp.springboot.bank.project.exception.IdNotFoundException;
import jsp.springboot.bank.project.exception.InsufficientBalanceException;
import jsp.springboot.bank.project.exception.InvalidInputException;
import jsp.springboot.bank.project.exception.NoRecordAvailableException;
import jsp.springboot.bank.project.exception.WrongCredentialsException;
import jsp.springboot.bank.project.repository.AccountRepository;
import jsp.springboot.bank.project.repository.BankRepository;

@Service
public class AccountService {

    final Double savingsMinimumBalance = 1000.0;
    final Double currentMinimumBalance = 3000.0;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BankRepository bankRepository;

    //1. Create Account
    public ResponseStructure<Account> createAccount(Account account) {
        if (account.getBank() == null || account.getBank().getBankId() == null) {
            throw new InvalidInputException("Bank is Required to create the account");
        }

        Optional<Bank> opt = bankRepository.findById(account.getBank().getBankId());

        if (account.getAccType() == AccountType.SAVINGS && account.getAccBalance() < savingsMinimumBalance) {
            throw new InsufficientBalanceException("To create the account the balance should be greater than " + savingsMinimumBalance);
        } 
        else if (account.getAccType() == AccountType.CURRENT && account.getAccBalance() < currentMinimumBalance) {
            throw new InsufficientBalanceException("To create the account the balance should be greater than " + currentMinimumBalance);
        } 
        else if (accountRepository.existsByAccNumber(account.getAccNumber())) {
            throw new InvalidInputException(account.getAccNumber() + " is already Exist");
        }
        else if (opt.isEmpty()) {
            throw new InvalidInputException("Bank is Required to create the account kindly check the bank details");
        }
        account.setBank(opt.get());
        Account a = accountRepository.save(account);
        ResponseStructure<Account> res = new ResponseStructure<>();
        res.setData(a);
        res.setMessage("Account Created Successfully");
        res.setStatusCode(HttpStatus.CREATED.value());
        return res;   
    }
    
    
    //2. Get All accounts
    public ResponseStructure<List<Account>> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        ResponseStructure<List<Account>> res = new ResponseStructure<>();

        if (accounts.isEmpty()) {
            throw new NoRecordAvailableException("No accounts available");
        }

        res.setData(accounts);
        res.setMessage("All accounts retrieved");
        res.setStatusCode(HttpStatus.OK.value());
        return res;
    }

  //3. Get account by account id
    public ResponseStructure<Account> getAccountById(Integer id) {
        Optional<Account> opt = accountRepository.findById(id);
        ResponseStructure<Account> res = new ResponseStructure<>();

        if (opt.isEmpty()) {
            throw new IdNotFoundException("Account not found with id: " + id);
        }

        res.setData(opt.get());
        res.setMessage("Account retrieved successfully");
        res.setStatusCode(HttpStatus.OK.value());
        return res;
    }

  //4. Delete account
    public ResponseStructure<String> deleteAccount(Integer id) {
        Optional<Account> opt = accountRepository.findById(id);
        ResponseStructure<String> res = new ResponseStructure<>();

        if (opt.isEmpty()) {
            throw new IdNotFoundException("Account not found with id: " + id);
        }

        accountRepository.deleteById(id);

        res.setData("Account with id " + id + " deleted successfully");
        res.setMessage("Success");
        res.setStatusCode(HttpStatus.OK.value());
        return res;
    }
    
    
  //5. Deposit amount
    @Transactional
    public ResponseStructure<Account> depositAmount(TransactionDetails tran) {
        if (tran.getAccId() == null) {
            throw new InvalidInputException("Account id is required");
        } else if (tran.getAccountNumber() == null) {
            throw new InvalidInputException("Account number is required");
        } else if (tran.getAmount() == null || tran.getAmount() <= 0) {
            throw new InvalidInputException("Amount must be greater than zero");
        }

        Optional<Account> opt = accountRepository.findById(tran.getAccId());
        ResponseStructure<Account> res = new ResponseStructure<>();

        if (opt.isEmpty()) {
            throw new IdNotFoundException("Account not found with id: " + tran.getAccId());
        }

        Account account = opt.get();
        if (!account.getAccNumber().equals(tran.getAccountNumber())) {
            throw new WrongCredentialsException("Account number is incorrect");
        }

        account.setAccBalance(account.getAccBalance() + tran.getAmount());
        Account updated = accountRepository.save(account);

        res.setData(updated);
        res.setMessage("Amount deposited successfully");
        res.setStatusCode(HttpStatus.OK.value());
        return res;
    }

    
  //6. Withdraw amount
    @Transactional
    public ResponseStructure<Account> withdrawAmount(TransactionDetails tran) {
        if (tran.getAccId() == null) {
            throw new InvalidInputException("Account id is required");
        } else if (tran.getAccountNumber() == null) {
            throw new InvalidInputException("Account number is required");
        } else if (tran.getAmount() == null || tran.getAmount() <= 0) {
            throw new InvalidInputException("Amount must be greater than zero");
        }

        Optional<Account> opt = accountRepository.findById(tran.getAccId());
        ResponseStructure<Account> res = new ResponseStructure<>();

        if (opt.isEmpty()) {
            throw new IdNotFoundException("Account not found with id: " + tran.getAccId());
        }

        Account account = opt.get();
        if (!account.getAccNumber().equals(tran.getAccountNumber())) {
            throw new WrongCredentialsException("Account number is incorrect");
        }

        Double minBalance = 0.0;
        if (account.getAccType() == AccountType.SAVINGS) {
            minBalance = savingsMinimumBalance;
        } else if (account.getAccType() == AccountType.CURRENT) {
            minBalance = currentMinimumBalance;
        }

        if (account.getAccBalance() - tran.getAmount() < minBalance) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        account.setAccBalance(account.getAccBalance() - tran.getAmount());
        Account updated = accountRepository.save(account);

        res.setData(updated);
        res.setMessage("Amount withdrawn successfully");
        res.setStatusCode(HttpStatus.OK.value());
        return res;
    }

    
  //7. Transfer amount
    @Transactional
    public ResponseStructure<Account> transferAmount(TransferDetails transfer) {
        if (transfer.getSenderAccId() == null || transfer.getReceiverAccId() == null) {
            throw new InvalidInputException("Both account ids are required");
        }
        if (transfer.getSenderAccountNumber() == null || transfer.getSenderAccountNumber().isBlank()
                || transfer.getReceiverAccountNumber() == null || transfer.getReceiverAccountNumber().isBlank()) {
            throw new InvalidInputException("Both account numbers are required");
        }
        if (transfer.getAmount() == null || transfer.getAmount() <= 0) {
            throw new InvalidInputException("Amount must be greater than zero");
        }
        if (transfer.getSenderAccId().equals(transfer.getReceiverAccId())) {
            throw new InvalidInputException("Sender and receiver account cannot be the same");
        }

        Optional<Account> fromOpt = accountRepository.findById(transfer.getSenderAccId());
        Optional<Account> toOpt = accountRepository.findById(transfer.getReceiverAccId());

        if (fromOpt.isEmpty()) {
            throw new IdNotFoundException("Sender account not found with id: " + transfer.getSenderAccId());
        }
        if (toOpt.isEmpty()) {
            throw new IdNotFoundException("Receiver account not found with id: " + transfer.getReceiverAccId());
        }

        Account fromAccount = fromOpt.get();
        Account toAccount = toOpt.get();

        if (!fromAccount.getAccNumber().equals(transfer.getSenderAccountNumber())) {
            throw new WrongCredentialsException("Sender account number is incorrect");
        }
        if (!toAccount.getAccNumber().equals(transfer.getReceiverAccountNumber())) {
            throw new WrongCredentialsException("Receiver account number is incorrect");
        }

        Double minBalance = 0.0;
        if (fromAccount.getAccType() == AccountType.SAVINGS) {
            minBalance = savingsMinimumBalance;
        } else if (fromAccount.getAccType() == AccountType.CURRENT) {
            minBalance = currentMinimumBalance;
        }

        if (fromAccount.getAccBalance() - transfer.getAmount() < minBalance) {
            throw new InsufficientBalanceException("Insufficient balance for transfer");
        }

        fromAccount.setAccBalance(fromAccount.getAccBalance() - transfer.getAmount());
        toAccount.setAccBalance(toAccount.getAccBalance() + transfer.getAmount());
        
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        ResponseStructure<Account> res = new ResponseStructure<>();
        res.setData(fromAccount);
        res.setMessage("Amount transferred successfully");
        res.setStatusCode(HttpStatus.OK.value());
        return res;
    }
    
    
    //8. Get account by bank id 
    public ResponseStructure<List<Account>> getAccountsByBankId(Integer bankId) {
        List<Account> accounts = accountRepository.findByBank_BankId(bankId);
        ResponseStructure<List<Account>> res = new ResponseStructure<>();

        if (accounts.isEmpty()) {
            throw new NoRecordAvailableException("No accounts found for bank id: " + bankId);
        }

        res.setData(accounts);
        res.setMessage("Accounts retrieved successfully");
        res.setStatusCode(HttpStatus.OK.value());
        return res;
    }

    
  //9. Get account by account type 
    public ResponseStructure<List<Account>> getAccountsByType(AccountType type) {
        if (type == null) {
            throw new InvalidInputException("Account type is required");
        }

        List<Account> accounts = accountRepository.findByAccType(type);
        ResponseStructure<List<Account>> res = new ResponseStructure<>();

        if (accounts.isEmpty()) {
            throw new NoRecordAvailableException("No accounts found for type: " + type);
        }

        res.setData(accounts);
        res.setMessage("Accounts retrieved successfully");
        res.setStatusCode(HttpStatus.OK.value());
        return res;
    }
    
  //10. Get Accounts by balance Greater Than Value
    public ResponseStructure<List<Account>> getAccountsGreaterThanValue(Double value) {
        if (value == null || value < 0) {
            throw new InvalidInputException("Value must be zero or greater");
        }

        List<Account> accounts = accountRepository.findByAccBalanceGreaterThan(value);
        ResponseStructure<List<Account>> res = new ResponseStructure<>();

        if (accounts.isEmpty()) {
            throw new NoRecordAvailableException("No accounts found with balance greater than " + value);
        }
        res.setData(accounts);
        res.setMessage("Accounts retrieved successfully");
        res.setStatusCode(HttpStatus.OK.value());
        return res;
    }

    
  //11. Get accounts by pagination
    public ResponseStructure<Page<Account>> getAccountsByPage(Integer pageNumber, Integer pageSize) {
        if (pageNumber == null || pageNumber < 0) {
            throw new InvalidInputException("Page number must be zero or greater");
        } else if (pageSize == null || pageSize <= 0) {
            throw new InvalidInputException("Page size must be greater than zero");
        }

        Page<Account> accountPage = accountRepository.findAll(PageRequest.of(pageNumber, pageSize));
        ResponseStructure<Page<Account>> res = new ResponseStructure<>();

        if (accountPage.isEmpty()) {
            throw new NoRecordAvailableException("No accounts available");
        }

        res.setData(accountPage);
        res.setMessage("Accounts retrieved successfully");
        res.setStatusCode(HttpStatus.OK.value());
        return res;
    }

    
}