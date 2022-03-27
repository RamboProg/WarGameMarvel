package engine;

import engine.Player;
import model.world.AntiHero;
import model.world.Cover;
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
import model.effects.Effect;
import model.effects.EffectType;

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
	}

	public Player getFirstP() {
		return this.firstPlayer;
	}

	public Player getScndP() {
		return this.secondPlayer;
	}

	public boolean getFrstLdrAbility() {
		return this.firstLeaderAbilityUsed;
	}

	public boolean getScndLdrAbility() {
		return this.secondLeaderAbilityUsed;
	}

	public Object[][] getBoard() {
		return this.board;
	}

	public static ArrayList<Champion> getChampAvailable() {
		return availableChampions;
	}

	public static ArrayList<Ability> getAbilityAvailable() {
		return availableAbilities;
	}

	public PriorityQueue getTurnOrder() {
		return this.turnOrder;
	}

	public static int getBoardHeight() {
		return BOARDHEIGHT;
	}

	public static int getBoardWidth() {
		return BOARDWIDTH;
	}

	private void placeChampions() {
		firstPlayer.getTeam().get(0).setLocation(new Point(0, 1));
		board[0][1] = firstPlayer.getTeam().get(0);
		board[0][2] = firstPlayer.getTeam().get(1);
		board[0][3] = firstPlayer.getTeam().get(2);

		secondPlayer.getTeam().get(0).setLocation(new Point(0, 1));
		board[0][1] = secondPlayer.getTeam().get(0);
		board[0][2] = secondPlayer.getTeam().get(1);
		board[0][3] = secondPlayer.getTeam().get(2);
	}

	private void placeCovers() {
		Random rnd = new Random();
		Random rnd1 = new Random();
		int k = 0;
		while (k < 5) {
			int i = rnd.nextInt((3 - 1)) + 1;
			int j = rnd1.nextInt((5));
			if (board[i][j] == null) {
				Cover c = new Cover(i, j);
				c.setLocation(j, i);
				k++;
			}
		}
	}

	public static void loadAbility(String filePath) throws IOException {
		String currentLine = "";
		FileReader fileReader = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			String[] result = currentLine.split(",");

			String name = result[1];
			int manaCost = Integer.parseInt(result[2]);
			int castRange = Integer.parseInt(result[4]);
			int baseCooldown = Integer.parseInt(result[3]);
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
					String effectName = result[7];
					int effectDuration = Integer.parseInt(result[8]);

					switch (result[7]) {
					case "Disarm":
					case "Silence":
					case "Root":
					case "Shock":
					case "Stun":
						Effect e1 = new Effect(effectName, effectDuration,
								EffectType.DEBUFF);
						CrowdControlAbility cc1 = new CrowdControlAbility(name,
								manaCost, baseCooldown, castRange,
								areaOfEffect, requiredActionsPerTurn, e1);
						availableAbilities.add(cc1);
						break;

					case "PowerUp":
					case "Shield":
					case "SpeedUp":
					case "Embrace":
					case "Dodge":
						Effect e2 = new Effect(effectName, effectDuration,
								EffectType.BUFF);
						CrowdControlAbility cc2 = new CrowdControlAbility(name,
								manaCost, baseCooldown, castRange,
								areaOfEffect, requiredActionsPerTurn, e2);
						availableAbilities.add(cc2);
						break;
					}
				}
			}
		}
	}

	public static void loadChampion(String filePath) throws IOException {
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
				if (availableAbilities.contains(name)) {
					a.getAbilities().add(locateAbiliry(ability1Name));
					a.getAbilities().add(locateAbiliry(ability2Name));
					a.getAbilities().add(locateAbiliry(ability3Name));
					a.setSpeed(speed);
					availableChampions.add(a);
				}
			} else {
				if (result[0].equals("H")) {
					Hero h = new Hero(name, maxHP, mana, actions, speed,
							attackRange, attackDamage);
					h.getAbilities().add(locateAbiliry(ability1Name));
					h.getAbilities().add(locateAbiliry(ability2Name));
					h.getAbilities().add(locateAbiliry(ability3Name));
					h.setSpeed(speed);
					availableChampions.add(h);
				} else {
					Villain v = new Villain(name, maxHP, mana, actions, speed,
							attackRange, attackDamage);
					v.getAbilities().add(locateAbiliry(ability1Name));
					v.getAbilities().add(locateAbiliry(ability2Name));
					v.getAbilities().add(locateAbiliry(ability3Name));
					v.setSpeed(speed);
					availableChampions.add(v);
				}
			}
		}
	}

	private static Ability locateAbiliry(String abilityName) {
		for (Ability ability : availableAbilities) {
			if (ability.getName().equals(abilityName))
				return ability;
		}
		return null;
	}

}
