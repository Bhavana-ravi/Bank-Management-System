package jsp.springboot.bank.project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jsp.springboot.bank.project.dto.ResponseStructure;
import jsp.springboot.bank.project.entity.Address;
import jsp.springboot.bank.project.service.AddressService;

@RestController
@RequestMapping("/api/address")
public class AddressController {
 
    private final AddressService addressService;
 
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }
 
    // 1. Get address by id
    @GetMapping("/{addressId}")
    public ResponseEntity<ResponseStructure<Address>> getAddressById(@PathVariable Integer addressId) {
        return new ResponseEntity<>(addressService.getAddressById(addressId), HttpStatus.OK);
    }
 
    // 2. Update address
    @PatchMapping("/update/{addressId}")
    public ResponseEntity<ResponseStructure<Address>> updateAddress(@PathVariable Integer addressId, @RequestBody Map<String, Object> updates) {
        return new ResponseEntity<>(addressService.updateAddress(addressId, updates), HttpStatus.OK);
    }
 
    // 3. Get address by bank id
    @GetMapping("/bank/{bankId}")
    public ResponseEntity<ResponseStructure<Address>> getAddressByBankId(@PathVariable Integer bankId) {
        return new ResponseEntity<>(addressService.getAddressByBankId(bankId), HttpStatus.OK);
    }
 
    // 4. Get address by city
    @GetMapping("/city/{city}")
    public ResponseEntity<ResponseStructure<List<Address>>> getAddressByCity(@PathVariable String city) {
        return new ResponseEntity<>(addressService.getAddressByCity(city), HttpStatus.OK);
    }
 
    // 5. Get address by city and street
    @GetMapping("/city-street/{city}/{street}")
    public ResponseEntity<ResponseStructure<List<Address>>> getAddressByCityAndStreet(@PathVariable String city,@PathVariable String street) {
        return new ResponseEntity<>(addressService.getAddressByCityAndStreet(city, street), HttpStatus.OK);
    }
 
    // 6. Get address by pincode
    @GetMapping("/pincode/{pincode}")
    public ResponseEntity<ResponseStructure<List<Address>>> getAddressByPincode(@PathVariable String pincode) {
        return new ResponseEntity<>(addressService.getAddressByPincode(pincode), HttpStatus.OK);
    }
}
 