package exceptions;

public class UnallowedMovementException extends GameActionException {
	String message = "";
	public UnallowedMovementException(){
		super();
	}
	
	public UnallowedMovementException(String s){
		this.message = s;
	}
}
