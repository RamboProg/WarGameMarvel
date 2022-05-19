package exceptions;

public class LeaderNotCurrentException extends GameActionException {
	String message = "";
	public LeaderNotCurrentException() {
		super();
	}

	public LeaderNotCurrentException(String s) {
	this.message = s;
	}
}