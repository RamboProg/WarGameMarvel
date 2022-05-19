package exceptions;

public class NotEnoughResourcesException extends GameActionException {
	String message = "";
	public NotEnoughResourcesException() {
		super();
	}

	public NotEnoughResourcesException(String s) {
	this.message = s;
	}

}
