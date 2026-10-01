package jsp.springboot.bank.project.exception;

@SuppressWarnings("serial")
public class InvalidInputException extends RuntimeException{

	public InvalidInputException(String message) {
		super(message);
	}
}
