package jsp.springboot.bank.project.exception;

@SuppressWarnings("serial")
public class DuplicateEntryException extends RuntimeException {

	public DuplicateEntryException(String message) {
		super(message);
	}

}
