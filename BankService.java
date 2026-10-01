package jsp.springboot.bank.project.service;

import java.util.List;   
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jsp.springboot.bank.project.dto.ResponseStructure;
import jsp.springboot.bank.project.entity.Bank;
import jsp.springboot.bank.project.exception.IdNotFoundException;
import jsp.springboot.bank.project.exception.InvalidInputException;
import jsp.springboot.bank.project.exception.NoRecordAvailableException;
import jsp.springboot.bank.project.repository.BankRepository;



@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    // 1. Create Bank
    public ResponseStructure<Bank> createBank(Bank bank) {
    	if (bank.getBankName() == null) {
    	    throw new InvalidInputException("Bank name is required");
    	} else if (bank.getIfscCode() == null) {
    	    throw new InvalidInputException("IFSC code is required");
    	} else if (bank.getBankContactNumber() == null || !bank.getBankContactNumber().matches("^[0-9]{10}$")) {
    	    throw new InvalidInputException("Bank contact number must be exactly 10 digits");
    	} else if (bankRepository.findByBankContactNumber(bank.getBankContactNumber()).isPresent()) {
    	    throw new InvalidInputException("Bank contact number already exists");
    	} else if (bankRepository.findByIfscCode(bank.getIfscCode()).isPresent()) {
    	    throw new InvalidInputException("IFSC code already exists");
    	}
 
        Bank saved = bankRepository.save(bank);
        ResponseStructure<Bank> res = new ResponseStructure<>();
        res.setData(saved);
        res.setMessage("Bank created successfully");
        res.setStatusCode(HttpStatus.CREATED.value());
        return res;
    }
 
    // 2. Get all banks
    public ResponseStructure<List<Bank>> getAllBanks() {
        List<Bank> banks = bankRepository.findAll();
        ResponseStructure<List<Bank>> res = new ResponseStructure<>();
        res.setData(banks);
        res.setMessage("Banks fetched successfully");
        res.setStatusCode(HttpStatus.OK.value());
        return res;
    }
 
    // 3. Get bank by Id
    public ResponseStructure<Bank> getBankByBankId(Integer bankId) {
        Optional<Bank> optionalBank = bankRepository.findById(bankId);
        if (optionalBank.isEmpty()) {
            throw new IdNotFoundException("Bank not found with id: " + bankId);
        }
        ResponseStructure<Bank> res = new ResponseStructure<>();
        res.setData(optionalBank.get());
        res.setMessage("Bank fetched successfully");
        res.setStatusCode(HttpStatus.OK.value());
        return res;
    }
 
    // 4. Delete bank
    public ResponseStructure<String> deleteBank(Integer bankId) {
        if (!bankRepository.existsById(bankId)) {
            throw new IdNotFoundException("Bank not found with id: " + bankId);
        }
        bankRepository.deleteById(bankId);
 
        ResponseStructure<String> res = new ResponseStructure<>();
        res.setData("Deleted bank with id: " + bankId);
        res.setMessage("Bank deleted successfully");
        res.setStatusCode(HttpStatus.OK.value());
        return res;
    }
 
    // 5. Get banks by pagination and sorting
    public ResponseStructure<Page<Bank>> getBanksByPaginationAndSorting(Integer pageNum, int pageSize, String fieldName) {
 
        Page<Bank> bankPage = bankRepository.findAll(PageRequest.of(pageNum, pageSize,Sort.by(fieldName).descending()));
        if(bankPage.isEmpty()) {
        	throw new NoRecordAvailableException("Data not available");
        }
        else {
        	 ResponseStructure<Page<Bank>> res = new ResponseStructure<>();
             res.setData(bankPage);
             res.setMessage("Banks fetched successfully");
             res.setStatusCode(HttpStatus.OK.value());
             return res;
        }
       
    }
 
    // 6. Get bank by IFSC code
    public ResponseStructure<Bank> getBankByIfscCode(String ifscCode) {
        Optional<Bank> optionalBank = bankRepository.findByIfscCode(ifscCode);
        if (optionalBank.isEmpty()) {
            throw new IdNotFoundException("Bank not found with IFSC code: " + ifscCode);
        }
 
        ResponseStructure<Bank> res = new ResponseStructure<>();
        res.setData(optionalBank.get());
        res.setMessage("Bank fetched successfully");
        res.setStatusCode(HttpStatus.OK.value());
        return res;
    }
 
    // 7. Get bank by address id
       public ResponseStructure<Bank> getBankByAddressId(Integer addressId) {
        Optional<Bank> optionalBank = bankRepository.findByAddressId_AddressId(addressId);
        if (optionalBank.isEmpty()) {
            throw new IdNotFoundException("Bank not found with address id: " + addressId);
        }
        ResponseStructure<Bank> res = new ResponseStructure<>();
        res.setData(optionalBank.get());
        res.setMessage("Bank fetched successfully");
        res.setStatusCode(HttpStatus.OK.value());
        return res;
    }
 
    // 8. Get banks by city 
    public ResponseStructure<List<Bank>> getBankByCity(String city) {
        List<Bank> banks = bankRepository.findByAddressId_City(city);
        ResponseStructure<List<Bank>> res = new ResponseStructure<>();
        res.setData(banks);
        res.setMessage("Banks fetched successfully");
        res.setStatusCode(HttpStatus.OK.value());
        return res;
    }
 
    // 9. Get bank by contact number
    public ResponseStructure<Bank> getBankByContactNo(String bankContactNo) {
        Optional<Bank> optionalBank = bankRepository.findByBankContactNumber(bankContactNo);
        if (optionalBank.isEmpty()) {
            throw new IdNotFoundException("Bank not found with contact no: " + bankContactNo);
        }
        ResponseStructure<Bank> res = new ResponseStructure<>();
        res.setData(optionalBank.get());
        res.setMessage("Bank fetched successfully");
        res.setStatusCode(HttpStatus.OK.value());
        return res;
    }
}
 