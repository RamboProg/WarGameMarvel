package exceptions;

public class UnallowedMovementException extends GameActionException {
	public UnallowedMovementException(){
		super();
	}
	
	public UnallowedMovementException(String s){
		s = " This movement is not allowed ";
	}
}
