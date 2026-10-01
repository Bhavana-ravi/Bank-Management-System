package jsp.springboot.bank.project.controller;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jsp.springboot.bank.project.dto.AccountType;
import jsp.springboot.bank.project.dto.ResponseStructure;
import jsp.springboot.bank.project.dto.TransactionDetails;
import jsp.springboot.bank.project.dto.TransferDetails;
import jsp.springboot.bank.project.entity.Account;
import jsp.springboot.bank.project.service.AccountService;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    //1. Create Account
    @PostMapping
    public ResponseEntity<ResponseStructure<Account>> createAccount(@RequestBody Account account) {
        return new ResponseEntity<>(accountService.createAccount(account), HttpStatus.CREATED);
    }

    //2. Get All accounts
    @GetMapping("/all-accounts")
    public ResponseEntity<ResponseStructure<List<Account>>> getAllAccounts() {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    //3. Get account by account id
    @GetMapping("/accid/{accId}")
    public ResponseEntity<ResponseStructure<Account>> getAccountById(@PathVariable Integer accId) {
        return new ResponseEntity<>(accountService.getAccountById(accId), HttpStatus.OK);
    }

    //4. Delete account
    @DeleteMapping("/delete/{accId}")
    public ResponseEntity<ResponseStructure<String>> deleteAccount(@PathVariable Integer accId) {
        return new ResponseEntity<>(accountService.deleteAccount(accId), HttpStatus.OK);
    }
    
    //5. Deposit amount
    @PutMapping("/deposit")
    public ResponseEntity<ResponseStructure<Account>> deposit(@RequestBody TransactionDetails tran) {
        return new ResponseEntity<>(accountService.depositAmount(tran), HttpStatus.OK);
    }

    //6. Withdraw amount
    @PutMapping("/withdraw")
    public ResponseEntity<ResponseStructure<Account>> withdraw(@RequestBody TransactionDetails tran) {
        return new ResponseEntity<>(accountService.withdrawAmount(tran), HttpStatus.OK);
    }

    //7. Transfer amount
    @PutMapping("/transfer")
    public ResponseEntity<ResponseStructure<Account>> transfer(@RequestBody TransferDetails transfer) {
        return new ResponseEntity<>(accountService.transferAmount(transfer), HttpStatus.OK);
    }

    //8. Get account by bank id 
    @GetMapping("/bank/{bankId}")
    public ResponseEntity<ResponseStructure<List<Account>>> getAccountsByBankId(@PathVariable Integer bankId) {
        return new ResponseEntity<>(accountService.getAccountsByBankId(bankId), HttpStatus.OK);
    }

    //9. Get account by account type 
    @GetMapping("/type/{accType}")
    public ResponseEntity<ResponseStructure<List<Account>>> getAccountsByType(@PathVariable AccountType accType) {
        return new ResponseEntity<>(accountService.getAccountsByType(accType), HttpStatus.OK);
    }

    //10. Get Accounts by balance Greater Than Value
    @GetMapping("/balance-greater-than/{value}")
    public ResponseEntity<ResponseStructure<List<Account>>> getAccountsGreaterThanValue(@PathVariable Double value) {
        return new ResponseEntity<>(accountService.getAccountsGreaterThanValue(value), HttpStatus.OK);
    }

    //11. Get accounts by pagination
    @GetMapping("/page/{pageNumber}/{pageSize}")
    public ResponseEntity<ResponseStructure<Page<Account>>> getAccountsByPage(@PathVariable Integer pageNumber, @PathVariable Integer pageSize) {
        return new ResponseEntity<>(accountService.getAccountsByPage(pageNumber, pageSize), HttpStatus.OK);
    }

    
}