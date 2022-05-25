package exceptions;

public class LeaderAbilityAlreadyUsedException extends GameActionException {
	String message = "";
	public LeaderAbilityAlreadyUsedException(){
		super();
	}
	
	public LeaderAbilityAlreadyUsedException(String s){
		super(s);
	}
	
}
