package exceptions;

public class ChampionDisarmedException extends Exception {
	
	public ChampionDisarmedException(){
		super();
	}
	
	public ChampionDisarmedException(String s){
		s = " custom_message_ ";
	}
}
