package exceptions;

public class InvalidTargetException extends Exception {
	String message = "";
	public InvalidTargetException(){
		super();
	}
	
	public InvalidTargetException(String s){
	this.message = s;
	}
}
