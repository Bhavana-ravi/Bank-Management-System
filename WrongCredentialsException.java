package jsp.springboot.bank.project.exception;

@SuppressWarnings("serial")
public class WrongCredentialsException extends RuntimeException{

	public WrongCredentialsException(String message) {
		super(message);
	}

}
