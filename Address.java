package jsp.springboot.bank.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer addressId;
	private String street;
	private String city;
	private String state;
	private String pincode;
	
	@JsonIgnore
	@OneToOne
	private Bank bank;
	
	
	
}
