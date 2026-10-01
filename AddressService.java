package jsp.springboot.bank.project.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jsp.springboot.bank.project.dto.ResponseStructure;
import jsp.springboot.bank.project.entity.Address;
import jsp.springboot.bank.project.entity.Bank;
import jsp.springboot.bank.project.exception.IdNotFoundException;
import jsp.springboot.bank.project.exception.NoRecordAvailableException;
import jsp.springboot.bank.project.repository.AddressRepository;
import jsp.springboot.bank.project.repository.BankRepository;


@Service
public class AddressService {

    @Autowired
    public AddressRepository addressRepository;
    
    @Autowired
    public BankRepository bankRepository;

 // 1. Get address by id
    public ResponseStructure<Address> getAddressById(Integer addressId) {
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isEmpty()) {
            throw new IdNotFoundException("Address not found with id: " + addressId);
        }
 
        ResponseStructure<Address> res = new ResponseStructure<>();
        res.setData(optionalAddress.get());
        res.setMessage("Address fetched successfully");
        res.setStatusCode(HttpStatus.OK.value());
        return res;
    }
 
    // 2. Update address
    public ResponseStructure<Address> updateAddress(Integer addressId, Map<String, Object> map) {
        if (addressId == null) {
            throw new IdNotFoundException("Id must be passed to update a record");
        }

        Optional<Address> opt = addressRepository.findById(addressId);
        if (opt.isEmpty()) {
            throw new NoRecordAvailableException("Address with ID " + addressId + " not found");
        }

        Address address = opt.get();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            switch (key) {
                case "street":
                    address.setStreet((String) value);
                    break;
                case "city":
                    address.setCity((String) value);
                    break;
                case "state":
                    address.setState((String) value);
                    break;
                case "pincode":
                    address.setPincode((String) value);
                    break;
                case "bankId":
                    if (value != null) {
                        Integer bankId = ((Number) value).intValue();
                        Optional<Bank> optBank = bankRepository.findById(bankId);
                        if (optBank.isEmpty()) {
                            throw new IdNotFoundException("Bank not found with id: " + bankId);
                        }
                        address.setBank(optBank.get());
                    }
                    break;
            }
        }

        Address updatedAddress = addressRepository.save(address);
        ResponseStructure<Address> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Address record updated successfully");
        res.setData(updatedAddress);
        return res;
    }

    // 3. Get address by bank id
    public ResponseStructure<Address> getAddressByBankId(Integer bankId) {
        Optional<Bank> optBank = bankRepository.findById(bankId);
        
        if (optBank.isEmpty()) {
            throw new IdNotFoundException("Bank not found with id: " + bankId);
        }
        Bank bank = optBank.get();
        Address address = bank.getAddressId();
        if (address == null) {
            throw new IdNotFoundException("Address not found for bank id: " + bankId);
        }
        ResponseStructure<Address> res = new ResponseStructure<>();
        res.setData(address);
        res.setMessage("Address fetched successfully");
        res.setStatusCode(HttpStatus.OK.value());
        return res;
    }
    
    
    // 4. Get address by city
    public ResponseStructure<List<Address>> getAddressByCity(String city) {
        List<Address> addresses = addressRepository.findByCity(city);
 
        ResponseStructure<List<Address>> res = new ResponseStructure<>();
        res.setData(addresses);
        res.setMessage("Addresses fetched successfully");
        res.setStatusCode(HttpStatus.OK.value());
        return res;
    }
 
    // 5. Get address by city and street 
    public ResponseStructure<List<Address>> getAddressByCityAndStreet(String city, String street) {
        List<Address> addresses = addressRepository.findByCityAndStreet(city, street);
        
        if (addresses.isEmpty()) {
            throw new NoRecordAvailableException("Addresses not found for city: " + city + " and street: " + street);
        }
        
        ResponseStructure<List<Address>> res = new ResponseStructure<>();
        res.setData(addresses);
        res.setMessage("Addresses fetched successfully");
        res.setStatusCode(HttpStatus.OK.value());
        return res;
    }
 
    // 6. Get address by pincode
    public ResponseStructure<List<Address>> getAddressByPincode(String pincode) {
        List<Address> addresses = addressRepository.findByPincode(pincode);
        if (addresses.isEmpty()) {
            throw new NoRecordAvailableException("Addresses not found for pincode: "+pincode);
        }
        ResponseStructure<List<Address>> res = new ResponseStructure<>();
        res.setData(addresses);
        res.setMessage("Addresses fetched successfully for pincode: " +pincode);
        res.setStatusCode(HttpStatus.OK.value());
        return res;
    }
}
 