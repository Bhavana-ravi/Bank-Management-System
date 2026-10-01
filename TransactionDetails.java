package jsp.springboot.bank.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDetails {
    private Integer accId;
    private String accountNumber;
    private Double amount;
    private Integer toAccId;
    private String toAccountNumber;
}