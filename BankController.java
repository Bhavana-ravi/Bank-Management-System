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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jsp.springboot.bank.project.dto.ResponseStructure;
import jsp.springboot.bank.project.entity.Bank;
import jsp.springboot.bank.project.service.BankService;


@RestController
@RequestMapping("/api/bank")
public class BankController {

    @Autowired
    private BankService bankService;

    // 1. Create Bank
    @PostMapping
    public ResponseEntity<ResponseStructure<Bank>> createBank(@RequestBody Bank bank) {
        return new ResponseEntity<>(bankService.createBank(bank), HttpStatus.CREATED);
    }
 
    // 2. Get all banks
    @GetMapping
    public ResponseEntity<ResponseStructure<List<Bank>>> getAllBanks() {
        return new ResponseEntity<>(bankService.getAllBanks(), HttpStatus.OK);
    }
 
    // 3. Get bank by Id
    @GetMapping("/{bankId}")
    public ResponseEntity<ResponseStructure<Bank>> getBankByBankId(@PathVariable Integer bankId) {
        return new ResponseEntity<>(bankService.getBankByBankId(bankId), HttpStatus.OK);
    }
 
    
    // 4. Delete bank
    @DeleteMapping("/delete/{bankId}")
    public ResponseEntity<ResponseStructure<String>> deleteBank(@PathVariable Integer bankId) {
        return new ResponseEntity<>(bankService.deleteBank(bankId), HttpStatus.OK);
    }
 
    // 5. Get banks by pagination and sorting
    @GetMapping("/pagination/{pageNum}/{pageSize}/{fieldName}")
    public ResponseEntity<ResponseStructure<Page<Bank>>> getBanksByPaginationAndSorting(@PathVariable Integer pageNum, @PathVariable Integer pageSize,@PathVariable String fieldName) {
        return new ResponseEntity<>(bankService.getBanksByPaginationAndSorting(pageNum,pageSize,fieldName), HttpStatus.OK);
    }
    
    // 6. Get bank by IFSC code
    @GetMapping("/ifsc/{ifscCode}")
    public ResponseEntity<ResponseStructure<Bank>> getBankByIfscCode(@PathVariable String ifscCode) {
        return new ResponseEntity<>(bankService.getBankByIfscCode(ifscCode), HttpStatus.OK);
    }
 
    // 7. Get bank by address id
    @GetMapping("/address/{addressId}")
    public ResponseEntity<ResponseStructure<Bank>> getBankByAddressId(@PathVariable Integer addressId) {
        return new ResponseEntity<>(bankService.getBankByAddressId(addressId), HttpStatus.OK);
    }
 
    // 8. Get banks by city
    @GetMapping("/city/{city}")
    public ResponseEntity<ResponseStructure<List<Bank>>> getBankByCity(@PathVariable String city) {
        return new ResponseEntity<>(bankService.getBankByCity(city), HttpStatus.OK);
    }
 
    // 9. Get bank by contact no.
    @GetMapping("/contact/{contactNo}")
    public ResponseEntity<ResponseStructure<Bank>> getBankByContactNo(@PathVariable String contactNo) {
        return new ResponseEntity<>(bankService.getBankByContactNo(contactNo), HttpStatus.OK);
    }
}
 