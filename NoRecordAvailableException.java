package jsp.springboot.bank.project.exception;

@SuppressWarnings("serial")
public class NoRecordAvailableException extends RuntimeException{

	public NoRecordAvailableException(String message) {
		super(message);
	}
}
