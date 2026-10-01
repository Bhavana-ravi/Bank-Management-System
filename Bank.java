package jsp.springboot.bank.project.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
public class Bank {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique = true)
	private Integer bankId;
	private String bankName;
	
	@Column(unique = true)
	private String ifscCode;
	private String branchName;
	
	@Column(unique = true)
	private String bankContactNumber;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address addressId;

	
	@OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Account> acc;
	
	
}
 
