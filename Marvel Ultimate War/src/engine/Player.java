package engine;
import java.util.ArrayList;
import model.world.Champion;

public class Player {
	
	private String name;
	private Champion leader;
	private ArrayList<Champion> team;
	
	public Player(String name){
		team = new ArrayList<Champion>();
		this.name = name;
		
	}

	public Champion getLeader() {
		return leader;
	}

	public void setLeader(Champion leader) {
		this.leader = leader;
	}

	public String getName() {
		return name;
	}

	public void setName(String n){
		this.name = n;
	}

	public ArrayList<Champion> getTeam() {
		return team;
	}

	
}
