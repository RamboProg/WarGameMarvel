package exceptions;

abstract class GameActionException extends Exception {
	String message = "";
	public GameActionException(){
		super();
	}
	
	public GameActionException(String s){
		super(s);
	}
}
