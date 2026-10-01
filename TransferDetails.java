package jsp.springboot.bank.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferDetails {
    private Integer senderAccId;
    private String senderAccountNumber;
    private Integer receiverAccId;
    private String receiverAccountNumber;
    private Double amount;
}
 