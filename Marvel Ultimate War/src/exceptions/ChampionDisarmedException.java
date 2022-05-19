package exceptions;

public class ChampionDisarmedException extends Exception {
	String message = "";
	public ChampionDisarmedException(){
		super();
	}
	
	public ChampionDisarmedException(String s){
		this.message = s;
	}
}
