package jsp.springboot.bank.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jsp.springboot.bank.project.dto.ResponseStructure;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleINFE(IdNotFoundException exception){
		
		ResponseStructure<String> res=new ResponseStructure<>();
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setMessage(exception.getMessage());
		res.setData("Failure");
		return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(NoRecordAvailableException.class)
	public ResponseEntity<ResponseStructure<String>> handleNRAE(NoRecordAvailableException exception){
		ResponseStructure<String> res=new ResponseStructure<>();
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setMessage(exception.getMessage());
		res.setData("Failure");
		return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
	}
	
	 @ExceptionHandler(InsufficientBalanceException.class)
	    public ResponseEntity<ResponseStructure<String>> handleInsufficientBalance(InsufficientBalanceException exception) {
			ResponseStructure<String> res=new ResponseStructure<>();
		 	res.setStatusCode(HttpStatus.NOT_FOUND.value());
			res.setMessage(exception.getMessage());
			res.setData("Failure");
			return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
	    }
	 
	 @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<ResponseStructure<String>> handleResourceNotFound(ResourceNotFoundException exception) {
		 ResponseStructure<String> res=new ResponseStructure<>();
		 	res.setStatusCode(HttpStatus.NOT_FOUND.value());
			res.setMessage(exception.getMessage());
			res.setData("Failure");
			return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
	    }
	 
	 @ExceptionHandler(InvalidInputException.class)
	    public ResponseEntity<ResponseStructure<String>> handleInvalidInput(InvalidInputException exception) {
		 ResponseStructure<String> res=new ResponseStructure<>();
		 	res.setStatusCode(HttpStatus.NOT_FOUND.value());
			res.setMessage(exception.getMessage());
			res.setData("Failure");
			return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
	    }
	 @ExceptionHandler(WrongCredentialsException.class)
	    public ResponseEntity<ResponseStructure<String>> handleWrongCredentials(WrongCredentialsException exception) {
		 ResponseStructure<String> res=new ResponseStructure<>();
		 	res.setStatusCode(HttpStatus.NOT_FOUND.value());
			res.setMessage(exception.getMessage());
			res.setData("Failure");
			return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
	    }
	 @ExceptionHandler(DuplicateEntryException.class)
	    public ResponseEntity<ResponseStructure<String>> handleDuplicateEntry(DuplicateEntryException exception) {
		 ResponseStructure<String> res=new ResponseStructure<>();
		 	res.setStatusCode(HttpStatus.NOT_FOUND.value());
			res.setMessage(exception.getMessage());
			res.setData("Failure");
			return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);
	    }
}
