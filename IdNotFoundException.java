package jsp.springboot.bank.project.exception;

@SuppressWarnings("serial")
public class IdNotFoundException extends RuntimeException{

	public IdNotFoundException(String message) {
		super(message);
	}
	
	

}
