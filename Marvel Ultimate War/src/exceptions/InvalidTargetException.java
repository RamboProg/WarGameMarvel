package exceptions;

public class InvalidTargetException extends Exception {
	public InvalidTargetException(){
		super();
	}
	
	public InvalidTargetException(String s){
		super(s);
	}
}
