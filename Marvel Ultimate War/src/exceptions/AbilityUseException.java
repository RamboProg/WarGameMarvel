package exceptions;

public class AbilityUseException extends GameActionException {
	String message = "";
	public AbilityUseException(){
		super();
	}
	
	public AbilityUseException(String s){
	this.message = s;
	}

}
