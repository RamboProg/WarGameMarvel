package engine;

import engine.Player;
import exceptions.*;
import model.world.AntiHero;
import model.world.Condition;
import model.world.Cover;
import model.world.Damageable;
import model.world.Direction;
import model.world.Hero;
import model.world.Villain;

import java.awt.Point;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import model.world.Champion;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.effects.*;

public class Game {

	private Player firstPlayer;
	private Player secondPlayer;
	private boolean firstLeaderAbilityUsed;
	private boolean secondLeaderAbilityUsed;
	private Object[][] board;
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private PriorityQueue turnOrder;
	private final static int BOARDHEIGHT = 5;
	private final static int BOARDWIDTH = 5;
	

	public Game(Player firstPlayer, Player secondPlayer) {
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
		board = new Object[BOARDHEIGHT][BOARDWIDTH];
		availableChampions = new ArrayList<Champion>();
		availableAbilities = new ArrayList<Ability>();
		placeChampions();
		placeCovers();
		this.turnOrder = new PriorityQueue(firstPlayer.getTeam().size()
				+ secondPlayer.getTeam().size());
		setQueue(this.firstPlayer, this.secondPlayer);

	}

	public Player getFirstPlayer() {
		return firstPlayer;
	}

	public Player getSecondPlayer() {
		return secondPlayer;
	}

	public boolean isFirstLeaderAbilityUsed() {
		return firstLeaderAbilityUsed;
	}

	public boolean isSecondLeaderAbilityUsed() {
		return secondLeaderAbilityUsed;
	}

	public Object[][] getBoard() {
		return board;
	}

	public static ArrayList<Champion> getAvailableChampions() {
		return availableChampions;
	}

	public static ArrayList<Ability> getAvailableAbilities() {
		return availableAbilities;
	}

	public PriorityQueue getTurnOrder() {
		return turnOrder;
	}

	public static int getBoardheight() {
		return BOARDHEIGHT;
	}

	public static int getBoardwidth() {
		return BOARDWIDTH;
	}

	private void placeChampions() {
		for (int i = 0; i < firstPlayer.getTeam().size(); i++) {
			board[0][i + 1] = firstPlayer.getTeam().get(i);
			firstPlayer.getTeam().get(i).setLocation(new Point(0, i + 1));

			board[4][i + 1] = secondPlayer.getTeam().get(i);
			secondPlayer.getTeam().get(i).setLocation(new Point(4, i + 1));
		}

	}

	private void placeCovers() {
		Random rnd = new Random();
		Random rnd1 = new Random();
		int k = 0;
		while (k < 5) {
			int coloumn = rnd.nextInt((3)) + 1;
			int row = rnd1.nextInt((5));
			if (board[coloumn][row] == null) {
				board[coloumn][row] = new Cover(coloumn, row);
				k++;
			}
		}
	}

	public static void loadAbilities(String filePath) throws IOException {
		String currentLine = "";
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			String[] result = currentLine.split(",");

			String name = result[1];
			int manaCost = Integer.parseInt(result[2]);
			int castRange = Integer.parseInt(result[3]);
			int baseCooldown = Integer.parseInt(result[4]);
			AreaOfEffect areaOfEffect = AreaOfEffect.valueOf(result[5]);
			int requiredActionsPerTurn = Integer.parseInt(result[6]);

			if (result[0].equals("HEL")) {
				int healAmount = Integer.parseInt(result[7]);
				HealingAbility a = new HealingAbility(name, manaCost,
						baseCooldown, castRange, areaOfEffect,
						requiredActionsPerTurn, healAmount);
				availableAbilities.add(a);
			} else {
				if (result[0].equals("DMG")) {
					int damageAmount = Integer.parseInt(result[7]);
					DamagingAbility a = new DamagingAbility(name, manaCost,
							baseCooldown, castRange, areaOfEffect,
							requiredActionsPerTurn, damageAmount);
					availableAbilities.add(a);
				}

				else {
					// String effectName = result[7];
					int effectDuration = Integer.parseInt(result[8]);

					switch (result[7]) {
					case "Disarm":
						Disarm d = new Disarm(effectDuration);
						CrowdControlAbility cc2 = new CrowdControlAbility(name,
								manaCost, baseCooldown, castRange,
								areaOfEffect, requiredActionsPerTurn, d);
						availableAbilities.add(cc2);
						break;
					case "Silence":
						Silence s = new Silence(effectDuration);
						CrowdControlAbility cc3 = new CrowdControlAbility(name,
								manaCost, baseCooldown, castRange,
								areaOfEffect, requiredActionsPerTurn, s);
						availableAbilities.add(cc3);
						break;
					case "Root":
						Root r = new Root(effectDuration);
						CrowdControlAbility cc4 = new CrowdControlAbility(name,
								manaCost, baseCooldown, castRange,
								areaOfEffect, requiredActionsPerTurn, r);
						availableAbilities.add(cc4);
						break;
					case "Shock":
						Shock sh = new Shock(effectDuration);
						CrowdControlAbility cc5 = new CrowdControlAbility(name,
								manaCost, baseCooldown, castRange,
								areaOfEffect, requiredActionsPerTurn, sh);
						availableAbilities.add(cc5);
						break;
					case "Stun":
						Stun st = new Stun(effectDuration);
						CrowdControlAbility cc1 = new CrowdControlAbility(name,
								manaCost, baseCooldown, castRange,
								areaOfEffect, requiredActionsPerTurn, st);
						availableAbilities.add(cc1);
						break;

					case "PowerUp":
						PowerUp p = new PowerUp(effectDuration);
						CrowdControlAbility cc7 = new CrowdControlAbility(name,
								manaCost, baseCooldown, castRange,
								areaOfEffect, requiredActionsPerTurn, p);
						availableAbilities.add(cc7);
						break;
					case "Shield":
						Shield shi = new Shield(effectDuration);
						CrowdControlAbility cc8 = new CrowdControlAbility(name,
								manaCost, baseCooldown, castRange,
								areaOfEffect, requiredActionsPerTurn, shi);
						availableAbilities.add(cc8);
						break;
					case "SpeedUp":
						SpeedUp su = new SpeedUp(effectDuration);
						CrowdControlAbility cc9 = new CrowdControlAbility(name,
								manaCost, baseCooldown, castRange,
								areaOfEffect, requiredActionsPerTurn, su);
						availableAbilities.add(cc9);
						break;
					case "Embrace":
						Embrace e = new Embrace(effectDuration);
						CrowdControlAbility cc10 = new CrowdControlAbility(
								name, manaCost, baseCooldown, castRange,
								areaOfEffect, requiredActionsPerTurn, e);
						availableAbilities.add(cc10);
						break;
					case "Dodge":
						Dodge ddg = new Dodge(effectDuration);
						CrowdControlAbility cc6 = new CrowdControlAbility(name,
								manaCost, baseCooldown, castRange,
								areaOfEffect, requiredActionsPerTurn, ddg);
						availableAbilities.add(cc6);
						break;
					}
				}
			}
		}
	}

	public static void loadChampions(String filePath) throws IOException {
		String currentLine = "";
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			String[] result = currentLine.split(",");

			String name = result[1];
			int maxHP = Integer.parseInt(result[2]);
			int mana = Integer.parseInt(result[3]);
			int actions = Integer.parseInt(result[4]);
			int speed = Integer.parseInt(result[5]);
			int attackRange = Integer.parseInt(result[6]);
			int attackDamage = Integer.parseInt(result[7]);
			String ability1Name = result[8];
			String ability2Name = result[9];
			String ability3Name = result[10];

			if (result[0].equals("A")) {
				AntiHero a = new AntiHero(name, maxHP, mana, actions, speed,
						attackRange, attackDamage);
				a.getAbilities().add(locateAbility(ability1Name));
				a.getAbilities().add(locateAbility(ability2Name));
				a.getAbilities().add(locateAbility(ability3Name));
				a.setSpeed(speed);
				availableChampions.add(a);
			} else {
				if (result[0].equals("H")) {
					Hero h = new Hero(name, maxHP, mana, actions, speed,
							attackRange, attackDamage);
					h.getAbilities().add(locateAbility(ability1Name));
					h.getAbilities().add(locateAbility(ability2Name));
					h.getAbilities().add(locateAbility(ability3Name));
					h.setSpeed(speed);
					availableChampions.add(h);
				} else {
					Villain v = new Villain(name, maxHP, mana, actions, speed,
							attackRange, attackDamage);
					v.getAbilities().add(locateAbility(ability1Name));
					v.getAbilities().add(locateAbility(ability2Name));
					v.getAbilities().add(locateAbility(ability3Name));
					v.setSpeed(speed);
					availableChampions.add(v);
				}
			}
		}
	}

	private static Ability locateAbility(String abilityName) {
		for (Ability ability : availableAbilities) {
			if (ability.getName().equals(abilityName))
				return ability;
		}
		return null;
	}

	public void setQueue(Player firstPLayer, Player secondPlayer) {
		for (int i = 0; i < firstPlayer.getTeam().size(); i++) {
			for (int j = 0; j < secondPlayer.getTeam().size(); j++) {
				turnOrder.insert((firstPlayer.getTeam().get(i)
						.compareTo(secondPlayer.getTeam().get(j))) * -1);
			}
		}
	}

	private void prepareChampionTurns() {
		for (Champion c : firstPlayer.getTeam()) {
			if (c.getCurrentHP() != 0)
				setQueue(this.firstPlayer, this.secondPlayer);
		}
		for (Champion c : secondPlayer.getTeam()) {
			if (c.getCurrentHP() != 0)
				setQueue(this.firstPlayer, this.secondPlayer);
		}
	}

	public Champion getCurrentChampion() {
		return (Champion) turnOrder.remove();
	}

	public Player checkGameOver() {
		int count1 = 0;
		int count2 = 0;
		for (Champion c : firstPlayer.getTeam()) {
			if (c.getCurrentHP() == 0)
				count1++;
		}
		for (Champion c : secondPlayer.getTeam()) {
			if (c.getCurrentHP() == 0)
				count2++;
		}

		if (count1 == 3 && count2 < 3)
			return secondPlayer;
		else if (count2 == 3 && count1 < 3)
			return firstPlayer;
		else
			return null;

	}

	public void move(Direction d) throws UnallowedMovementException,
			NotEnoughResourcesException {

		if (getCurrentChampion().getCondition() != Condition.ROOTED
				&& getCurrentChampion().getCurrentActionPoints() > 0) {
			int x = getCurrentChampion().getLocation().x;
			int y = getCurrentChampion().getLocation().y;
			if ((x < 5 && x > -1) && (y < 5 && y > -1)) {
				switch (d) {
				case UP:
					if (getBoard()[y + 1][x] == null)
						getCurrentChampion().setLocation(new Point(y + 1, x));
					else
						throw new UnallowedMovementException(
								"You cannot move here!");
					break;
				case DOWN:
					if (getBoard()[y - 1][x] == null)
						getCurrentChampion().setLocation(new Point(y - 1, x));
					else
						throw new UnallowedMovementException(
								"You cannot move here!");
					break;
				case RIGHT:
					if (getBoard()[y][x + 1] == null)
						getCurrentChampion().setLocation(new Point(y, x + 1));
					else
						throw new UnallowedMovementException(
								"You cannot move here!");
					break;
				case LEFT:
					if (getBoard()[y][x - 1] == null)
						getCurrentChampion().setLocation(new Point(y, x - 1));
					else
						throw new UnallowedMovementException(
								"You cannot move here!");
					break;
				default:
					throw new UnallowedMovementException("Invalid input");
				}
			} else
				throw new UnallowedMovementException();
		} else
			throw new NotEnoughResourcesException(
					"you do not have the enough resources!");

	}

//	private Player checkFriendly(ArrayList<Damageable> target){
//		//for(int i = 0; i < )
//	}
	
	public void attack(Direction d) throws ChampionDisarmedException,
			InvalidTargetException, NotEnoughResourcesException {

		if (getCurrentChampion().getCurrentActionPoints() > 0
				&& getCurrentChampion().getCondition() == Condition.ACTIVE
				&& getCurrentChampion().getAttackRange() < 5) {
			ArrayList<Damageable> targets = new ArrayList<Damageable>();
		
			
		}

	}

}
