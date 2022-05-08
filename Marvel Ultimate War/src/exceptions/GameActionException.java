package exceptions;

abstract class GameActionException extends Exception {
	
	public GameActionException(){
		super();
	}
	
	public GameActionException(String s){
		s = " custom_message_ ";
	}
}
