package jsp.springboot.bank.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jsp.springboot.bank.project.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
	 // 3. Get address by bank id
    Optional<Address> findByBank_BankId(Integer bankId);
 
    // 4. Get address by city
    List<Address> findByCity(String city);
 
    // 5. Get address by city and street
    List<Address> findByCityAndStreet(String city, String street);
 
    // 6. Get address by pincode
    List<Address> findByPincode(String pincode);
}