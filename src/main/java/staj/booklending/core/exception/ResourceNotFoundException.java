package staj.booklending.core.exception;

public class ResourceNotFoundException extends RuntimeException {

	 public ResourceNotFoundException( String fieldName, Object fieldValue) {
	        super(String.format("%s not found with %s : '%s'",  fieldName, fieldValue));
	    }
}
