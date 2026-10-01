package jsp.springboot.bank.project.entity;



import jakarta.persistence.Column;  
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jsp.springboot.bank.project.dto.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
  
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true)
	private Integer accId;
	private String accNumber;
	private String accHolderName;
	
	@Enumerated(EnumType.STRING)
	private AccountType accType;
	private Double accBalance;
	
	
	@ManyToOne
	@JoinColumn(name = "bankId")
	private Bank bank;
	
	@OneToOne
	private Address address;
}

