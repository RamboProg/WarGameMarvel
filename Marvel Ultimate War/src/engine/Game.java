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
import java.rmi.AlreadyBoundException;
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
		prepareChampionTurns();

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

	private void prepareChampionTurns() {
		for (Champion c : firstPlayer.getTeam()) {
			if(c.getCondition() != Condition.KNOCKEDOUT)
			turnOrder.insert(c);
		}
		for (Champion c : secondPlayer.getTeam()) {
			if(c.getCondition() != Condition.KNOCKEDOUT)
			turnOrder.insert(c);
		}
	}

	public Champion getCurrentChampion() {
		return (Champion) turnOrder.peekMin();
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
					if (getBoard()[x + 1][y] == null) {
						getCurrentChampion().setLocation(new Point(x + 1, y));
						getCurrentChampion()
								.setCurrentActionPoints(
										getCurrentChampion()
												.getCurrentActionPoints() - 1);
					} else
						throw new UnallowedMovementException(
								"You cannot move here!");
					break;
				case DOWN:
					if (getBoard()[x - 1][y] == null) {
						getCurrentChampion().setLocation(new Point(x - 1, y));
						getCurrentChampion()
								.setCurrentActionPoints(
										getCurrentChampion()
												.getCurrentActionPoints() - 1);
					} else
						throw new UnallowedMovementException(
								"You cannot move here!");
					break;
				case RIGHT:
					if (getBoard()[x][y + 1] == null) {
						getCurrentChampion().setLocation(new Point(x, y + 1));
						getCurrentChampion()
								.setCurrentActionPoints(
										getCurrentChampion()
												.getCurrentActionPoints() - 1);
					} else
						throw new UnallowedMovementException(
								"You cannot move here!");
					break;
				case LEFT:
					if (getBoard()[x][y - 1] == null) {
						getCurrentChampion().setLocation(new Point(x, y - 1));
						getCurrentChampion()
								.setCurrentActionPoints(
										getCurrentChampion()
												.getCurrentActionPoints() - 1);
					} else
						throw new UnallowedMovementException(
								"You cannot move here!");
					break;
				default:
					throw new UnallowedMovementException("Invalid input");
				}
			} else
				throw new UnallowedMovementException();
		} else if (getCurrentChampion().getCondition() == Condition.ROOTED)
			throw new UnallowedMovementException("Your champion is ROOTED!");
		else
			throw new NotEnoughResourcesException(
					"You do not have enough resources!");

	}

	private static int manhattanDist(int M, int N, int X1, int Y1, int X2,
			int Y2) {
		int dist = Math.abs(X2 - X1) + Math.abs(Y2 - Y1);
		return dist;
	}

	public void attack(Direction d) throws ChampionDisarmedException,
			InvalidTargetException, NotEnoughResourcesException {
		boolean containsDisarm = false;
		for (int k = 0; k < getCurrentChampion().getAppliedEffects().size(); k++) {
			if (getCurrentChampion().getAppliedEffects().get(k) instanceof Disarm) {
				containsDisarm = true;
			}
			if (containsDisarm == true) {
				throw new ChampionDisarmedException("Your Champion is DISARMED");
			} else {
				if (getCurrentChampion().getCurrentActionPoints() > 0
						&& getCurrentChampion().getCondition() == Condition.ACTIVE
						&& getCurrentChampion().getAttackRange() < 5) {
					ArrayList<Damageable> targets = new ArrayList<Damageable>();

					/*
					 * The below nested loop adds to the ArrayList the valid
					 * targets that are within range of the attacker
					 */
					for (int i = 0; i <= getCurrentChampion().getAttackRange(); i++) {
						for (int j = 0; j <= getCurrentChampion()
								.getAttackRange(); j++) {
							if (board[j][i] != null
									&& board[j][i] != getCurrentChampion()) {
								targets.add((Damageable) board[j][i]);
							}
						}
					}

					switch (d) {
					case UP:
						int min = getCurrentChampion().getAttackRange();
						int minDistIndex = 0;
						for (int i = 0; i < targets.size(); i++) {
							if ((targets.get(i).getLocation().y - getCurrentChampion()
									.getLocation().y) < min && min > -1) {
								min = targets.get(i).getLocation().y
										- getCurrentChampion().getLocation().y;
								minDistIndex = i;
							}
						}

						if (getCurrentChampion() instanceof Hero) {
							Champion c = (Champion) targets.get(minDistIndex);
							if (c instanceof Villain
									|| c instanceof AntiHero) {
								for(int j = 0; j<c.getAppliedEffects().size();j++){
									if(c.getAppliedEffects().get(j) instanceof Dodge){
										for(int n = 0; k<c.getAppliedEffects().size();k++){
											
										}
									}
								}
								targets.get(minDistIndex)
										.setCurrentHP(
												(int) (targets
														.get(minDistIndex)
														.getCurrentHP() - getCurrentChampion()
														.getAttackDamage() * 1.5));
								if (targets.get(minDistIndex).getCurrentHP() == 0)
								{
									c = (Champion) targets.get(minDistIndex);
									c.setCondition(Condition.KNOCKEDOUT);
									targets.remove(targets.get(minDistIndex));
									
								}							} else {
								if (getCurrentChampion() instanceof Villain) {
									if (targets.get(minDistIndex) instanceof Hero
											|| targets.get(minDistIndex) instanceof AntiHero) {
										targets.get(minDistIndex)
												.setCurrentHP(
														(int) (targets.get(
																minDistIndex)
																.getCurrentHP() - getCurrentChampion()
																.getAttackDamage() * 1.5));
										if (targets.get(minDistIndex)
												.getCurrentHP() == 0)
										{
											c = (Champion) targets.get(minDistIndex);
											c.setCondition(Condition.KNOCKEDOUT);
											targets.remove(targets.get(minDistIndex));
											
										}
									} else if (getCurrentChampion() instanceof AntiHero) {
										if (targets.get(minDistIndex) instanceof Villain
												|| targets.get(minDistIndex) instanceof Hero) {
											targets.get(minDistIndex)
													.setCurrentHP(
															(int) (targets
																	.get(minDistIndex)
																	.getCurrentHP() - getCurrentChampion()
																	.getAttackDamage() * 1.5));
											if (targets.get(minDistIndex)
													.getCurrentHP() == 0)
											{
												c = (Champion) targets.get(minDistIndex);
												c.setCondition(Condition.KNOCKEDOUT);
												targets.remove(targets.get(minDistIndex));
												
											}
										} else if (targets.get(minDistIndex) instanceof Champion
												|| targets.get(minDistIndex) instanceof Cover) {
											targets.get(minDistIndex)
													.setCurrentHP(
															(int) (targets
																	.get(minDistIndex)
																	.getCurrentHP() - getCurrentChampion()
																	.getAttackDamage()));
											if (targets.get(minDistIndex)
													.getCurrentHP() == 0)
											{
												c = (Champion) targets.get(minDistIndex);
												c.setCondition(Condition.KNOCKEDOUT);
												targets.remove(targets.get(minDistIndex));
												
											}
										}
									}
								}
							}
						}
						break;
					case RIGHT:
						int min1 = getCurrentChampion().getAttackRange();
						int minDistIndex1 = 0;
						for (int i = 0; i < targets.size(); i++) {
							if ((targets.get(i).getLocation().x - getCurrentChampion()
									.getLocation().x) < min1 && min1 > -1) {
								min = targets.get(i).getLocation().x
										- getCurrentChampion().getLocation().x;
								minDistIndex1 = i;
							}
						}

						if (getCurrentChampion() instanceof Hero) {
							if (targets.get(minDistIndex1) instanceof Villain
									|| targets.get(minDistIndex1) instanceof AntiHero) {
								targets.get(minDistIndex1)
										.setCurrentHP(
												(int) (targets.get(
														minDistIndex1)
														.getCurrentHP() - getCurrentChampion()
														.getAttackDamage() * 1.5));
								if (targets.get(minDistIndex1).getCurrentHP() == 0)
								{
									Champion c = (Champion) targets.get(minDistIndex1);
									c.setCondition(Condition.KNOCKEDOUT);
									targets.remove(targets.get(minDistIndex1));
									
								}							} else {
								if (getCurrentChampion() instanceof Villain) {
									if (targets.get(minDistIndex1) instanceof Hero
											|| targets.get(minDistIndex1) instanceof AntiHero) {
										targets.get(minDistIndex1)
												.setCurrentHP(
														(int) (targets.get(
																minDistIndex1)
																.getCurrentHP() - getCurrentChampion()
																.getAttackDamage() * 1.5));
										if (targets.get(minDistIndex1)
												.getCurrentHP() == 0)
										{
											Champion c = (Champion) targets.get(minDistIndex1);
											c.setCondition(Condition.KNOCKEDOUT);
											targets.remove(targets.get(minDistIndex1));
											
										}
									} else if (getCurrentChampion() instanceof AntiHero) {
										if (targets.get(minDistIndex1) instanceof Villain
												|| targets.get(minDistIndex1) instanceof Hero) {
											targets.get(minDistIndex1)
													.setCurrentHP(
															(int) (targets
																	.get(minDistIndex1)
																	.getCurrentHP() - getCurrentChampion()
																	.getAttackDamage() * 1.5));
											if (targets.get(minDistIndex1)
													.getCurrentHP() == 0)
											{
												Champion c = (Champion) targets.get(minDistIndex1);
												c.setCondition(Condition.KNOCKEDOUT);
												targets.remove(targets.get(minDistIndex1));
												
											}
										} else if (targets.get(minDistIndex1) instanceof Champion
												|| targets.get(minDistIndex1) instanceof Cover) {
											targets.get(minDistIndex1)
													.setCurrentHP(
															(int) (targets
																	.get(minDistIndex1)
																	.getCurrentHP() - getCurrentChampion()
																	.getAttackDamage()));
											if (targets.get(minDistIndex1)
													.getCurrentHP() == 0)
											{
												Champion c = (Champion) targets.get(minDistIndex1);
												c.setCondition(Condition.KNOCKEDOUT);
												targets.remove(targets.get(minDistIndex1));
												
											}
										}
									}
								}
							}
						}
						break;
					case LEFT:
						int min2 = getCurrentChampion().getAttackRange();
						int minDistIndex2 = 0;
						for (int i = 0; i < targets.size(); i++) {
							if ((targets.get(i).getLocation().x - getCurrentChampion()
									.getLocation().x) < min2 && min2 < 0) {
								min = targets.get(i).getLocation().x
										- getCurrentChampion().getLocation().x;
								minDistIndex2 = i;
							}
						}

						if (getCurrentChampion() instanceof Hero) {
							if (targets.get(minDistIndex2) instanceof Villain
									|| targets.get(minDistIndex2) instanceof AntiHero) {
								targets.get(minDistIndex2)
										.setCurrentHP(
												(int) (targets.get(
														minDistIndex2)
														.getCurrentHP() - getCurrentChampion()
														.getAttackDamage() * 1.5));
								if (targets.get(minDistIndex2).getCurrentHP() == 0){
									Champion c = (Champion) targets.get(minDistIndex2);
									c.setCondition(Condition.KNOCKEDOUT);
									targets.remove(targets.get(minDistIndex2));
									
								}
									
							} else {
								if (getCurrentChampion() instanceof Villain) {
									if (targets.get(minDistIndex2) instanceof Hero
											|| targets.get(minDistIndex2) instanceof AntiHero) {
										targets.get(minDistIndex2)
												.setCurrentHP(
														(int) (targets.get(
																minDistIndex2)
																.getCurrentHP() - getCurrentChampion()
																.getAttackDamage() * 1.5));
										if (targets.get(minDistIndex2)
												.getCurrentHP() == 0)
										{
											Champion c = (Champion) targets.get(minDistIndex2);
											c.setCondition(Condition.KNOCKEDOUT);
											targets.remove(targets.get(minDistIndex2));
											
										}
									} else if (getCurrentChampion() instanceof AntiHero) {
										if (targets.get(minDistIndex2) instanceof Villain
												|| targets.get(minDistIndex2) instanceof Hero) {
											targets.get(minDistIndex2)
													.setCurrentHP(
															(int) (targets
																	.get(minDistIndex2)
																	.getCurrentHP() - getCurrentChampion()
																	.getAttackDamage() * 1.5));
											if (targets.get(minDistIndex2)
													.getCurrentHP() == 0)
											{
												Champion c = (Champion) targets.get(minDistIndex2);
												c.setCondition(Condition.KNOCKEDOUT);
												targets.remove(targets.get(minDistIndex2));
												
											};
										} else if (targets.get(minDistIndex2) instanceof Champion
												|| targets.get(minDistIndex2) instanceof Cover) {
											targets.get(minDistIndex2)
													.setCurrentHP(
															(int) (targets
																	.get(minDistIndex2)
																	.getCurrentHP() - getCurrentChampion()
																	.getAttackDamage()));
											if (targets.get(minDistIndex2)
													.getCurrentHP() == 0)
											{
												Champion c = (Champion) targets.get(minDistIndex2);
												c.setCondition(Condition.KNOCKEDOUT);
												targets.remove(targets.get(minDistIndex2));
												
											}
										}
									}
								}
							}
						}
						break;
					case DOWN:
						int min3 = getCurrentChampion().getAttackRange();
						int minDistIndex3 = 0;
						for (int i = 0; i < targets.size(); i++) {
							if ((targets.get(i).getLocation().y - getCurrentChampion()
									.getLocation().y) < min3 && min3 < 0) {
								min = targets.get(i).getLocation().y
										- getCurrentChampion().getLocation().y;
								minDistIndex3 = i;
							}
						}

						if (getCurrentChampion() instanceof Hero) {
							if (targets.get(minDistIndex3) instanceof Villain
									|| targets.get(minDistIndex3) instanceof AntiHero) {
								targets.get(minDistIndex3)
										.setCurrentHP(
												(int) (targets.get(
														minDistIndex3)
														.getCurrentHP() - getCurrentChampion()
														.getAttackDamage() * 1.5));
								if (targets.get(minDistIndex3).getCurrentHP() == 0)
									
								{
									Champion c = (Champion) targets.get(minDistIndex3);
									c.setCondition(Condition.KNOCKEDOUT);
									targets.remove(targets.get(minDistIndex3));
									
								}
							} else {
								if (getCurrentChampion() instanceof Villain) {
									if (targets.get(minDistIndex3) instanceof Hero
											|| targets.get(minDistIndex3) instanceof AntiHero) {
										targets.get(minDistIndex3)
												.setCurrentHP(
														(int) (targets.get(
																minDistIndex3)
																.getCurrentHP() - getCurrentChampion()
																.getAttackDamage() * 1.5));
										if (targets.get(minDistIndex3)
												.getCurrentHP() == 0)
										{
											Champion c = (Champion) targets.get(minDistIndex3);
											c.setCondition(Condition.KNOCKEDOUT);
											targets.remove(targets.get(minDistIndex3));
											
										}
									} else if (getCurrentChampion() instanceof AntiHero) {
										if (targets.get(minDistIndex3) instanceof Villain
												|| targets.get(minDistIndex3) instanceof Hero) {
											targets.get(minDistIndex3)
													.setCurrentHP(
															(int) (targets
																	.get(minDistIndex3)
																	.getCurrentHP() - getCurrentChampion()
																	.getAttackDamage() * 1.5));
											if (targets.get(minDistIndex3)
													.getCurrentHP() == 0)
											{
												Champion c = (Champion) targets.get(minDistIndex3);
												c.setCondition(Condition.KNOCKEDOUT);
												targets.remove(targets.get(minDistIndex3));
												
											}
											
											
										} else if (targets.get(minDistIndex3) instanceof Champion
												|| targets.get(minDistIndex3) instanceof Cover) {
											targets.get(minDistIndex3)
													.setCurrentHP(
															(int) (targets
																	.get(minDistIndex3)
																	.getCurrentHP() - getCurrentChampion()
																	.getAttackDamage()));
											if (targets.get(minDistIndex3)
													.getCurrentHP() == 0)
											{
												Champion c = (Champion) targets.get(minDistIndex3);
												c.setCondition(Condition.KNOCKEDOUT);
												targets.remove(targets.get(minDistIndex3));
												
											}
										}
									}
								}
							}
						}
						break;
					default:
						throw new InvalidTargetException();
					}

					// DO THE REST OF THE DIRECTIONS WHEN U GET HOME
				} else if (getCurrentChampion().getCurrentActionPoints() < 2)
					throw new NotEnoughResourcesException();
			}
		}
	}

	public void castAbility(Ability a) throws NotEnoughResourcesException, CloneNotSupportedException {
		ArrayList<Damageable> targets = new ArrayList<Damageable>();
		for (int i = 0; i < a.getCastRange(); i++) {
			for (int j = 0; j < a.getCastRange(); j++) {
				if (board[j][i] != null && board[j][i] != getCurrentChampion()
						&& !(board[j][i] instanceof Cover)) {
					targets.add((Damageable) board[j][i]);
				}
			}
		}

		if ((a.getCastArea() == AreaOfEffect.SELFTARGET)
				|| (a.getCastArea() == AreaOfEffect.TEAMTARGET)
				|| (a.getCastArea() == AreaOfEffect.SURROUND))
			a.execute(targets);
		if ((a.getCastArea() == AreaOfEffect.SELFTARGET)) {
			for (int k = 0; k < targets.size(); k++) {
				if (targets.get(k) != getCurrentChampion()) {
					targets.remove(k);
				}
			}
			a.execute(targets);
		} else if ((a.getCastArea() == AreaOfEffect.TEAMTARGET)) {
			for (int k = 0; k < targets.size(); k++) {
				if (firstPlayer.getTeam().contains(getCurrentChampion())) {

				}
			}
		}

		else
			throw new NotEnoughResourcesException("Not enough mana!");
	}

	public void castAbility(Ability a, Direction d)
			throws NotEnoughResourcesException, AbilityUseException, CloneNotSupportedException {
		ArrayList<Damageable> targets = new ArrayList<Damageable>();
		for (int i = 0; i < a.getCastRange(); i++) {
			for (int j = 0; j < a.getCastRange(); j++) {
				if (board[j][i] != null && board[j][i] != getCurrentChampion()) {
					targets.add((Damageable) board[j][i]);
				}
			}
		}
		if (a.getCastArea() == AreaOfEffect.DIRECTIONAL) {
			switch (d) {
			case UP:
				int dist = 0;
				if (getCurrentChampion().getMana() >= a.getManaCost()) {
					for (int l = 0; l < targets.size(); l++) {
						dist = manhattanDist(a.getCastRange(),
								a.getCastRange(),
								targets.get(l).getLocation().x, targets.get(l)
										.getLocation().y, getCurrentChampion()
										.getLocation().x, getCurrentChampion()
										.getLocation().y);
						if (dist > a.getCastRange()) {
							targets.remove(l);
						}
					}
					a.execute(targets);
				} else
					throw new NotEnoughResourcesException();
				break;
			case DOWN:
				int dist1 = 0;
				if (getCurrentChampion().getMana() >= a.getManaCost()) {
					for (int l = 0; l < targets.size(); l++) {
						dist1 = manhattanDist(a.getCastRange(),
								a.getCastRange(),
								targets.get(l).getLocation().x, targets.get(l)
										.getLocation().y, getCurrentChampion()
										.getLocation().x, getCurrentChampion()
										.getLocation().y);
						if (dist1 > a.getCastRange()) {
							targets.remove(l);
						}
					}
					a.execute(targets);
				} else
					throw new NotEnoughResourcesException();
				break;
			case RIGHT:
				int dist2 = 0;
				if (getCurrentChampion().getMana() >= a.getManaCost()) {
					for (int l = 0; l < targets.size(); l++) {
						dist2 = manhattanDist(a.getCastRange(),
								a.getCastRange(),
								targets.get(l).getLocation().x, targets.get(l)
										.getLocation().y, getCurrentChampion()
										.getLocation().x, getCurrentChampion()
										.getLocation().y);
						if (dist2 > a.getCastRange()) {
							targets.remove(l);
						}
					}
					a.execute(targets);
				} else
					throw new NotEnoughResourcesException();
				break;
			case LEFT:
				int dist3 = 0;
				if (getCurrentChampion().getMana() >= a.getManaCost()) {
					for (int l = 0; l < targets.size(); l++) {
						dist3 = manhattanDist(a.getCastRange(),
								a.getCastRange(),
								targets.get(l).getLocation().x, targets.get(l)
										.getLocation().y, getCurrentChampion()
										.getLocation().x, getCurrentChampion()
										.getLocation().y);
						if (dist3 > a.getCastRange()) {
							targets.remove(l);
						}
					}
					a.execute(targets);
				} else
					throw new NotEnoughResourcesException();
				break;
			default:
				throw new AbilityUseException();
			}
		}
	}

	public void castAbility(Ability a, int x, int y)
			throws AbilityUseException, NotEnoughResourcesException, CloneNotSupportedException {
		ArrayList<Damageable> targets = new ArrayList<Damageable>();
		for (int i = 0; i < a.getCastRange(); i++) {
			for (int j = 0; j < a.getCastRange(); j++) {
				if (board[j][i] != null && board[j][i] != getCurrentChampion()) {
					targets.add((Damageable) board[j][i]);
				}
			}
		}
		if (a.getCastArea() == AreaOfEffect.SINGLETARGET) {
			int dist = 0;
			dist = manhattanDist(a.getCastRange(), a.getCastRange(), x, y,
					getCurrentChampion().getLocation().x, getCurrentChampion()
							.getLocation().y);
			if (dist < a.getCastRange()) {
				if (getCurrentChampion().getMana() >= a.getManaCost())
					a.execute(targets);
				else
					throw new NotEnoughResourcesException();
			} else
				throw new AbilityUseException();
		}

	}

	public void useLeaderAbility() throws LeaderNotCurrentException,
			LeaderAbilityAlreadyUsedException {
		if (firstPlayer.getLeader().equals(getCurrentChampion())) {
			int count = 0;
			ArrayList<Damageable> targets = new ArrayList<Damageable>();
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (board[j][i] != null
							&& board[j][i] != getCurrentChampion()
							&& board[j][i] instanceof Champion) {
						targets.add((Damageable) board[j][i]);
					}
				}
			}
			ArrayList<Champion> targetChamp = new ArrayList<Champion>();
			for (int k = 0; k < targets.size(); k++) {
				targetChamp.add((Champion) targets.get(k));
			}
			if (getCurrentChampion() instanceof Hero) {
				for (int l = 0; l < targetChamp.size(); l++) {
					if (!(targetChamp.get(l) instanceof Villain)
							&& !(targetChamp.get(l) instanceof AntiHero))
						targetChamp.remove(l);
				}
				getCurrentChampion().useLeaderAbility(targetChamp);
				count++;
			} else if (getCurrentChampion() instanceof Villain) {
				for (int l = 0; l < targetChamp.size(); l++) {
					if (!(targetChamp.get(l) instanceof Hero)
							&& !(targetChamp.get(l) instanceof AntiHero))
						targetChamp.remove(l);
				}
				getCurrentChampion().useLeaderAbility(targetChamp);
				count++;
			} else if (getCurrentChampion() instanceof AntiHero) {
				for (int l = 0; l < targetChamp.size(); l++) {
					if (!(targetChamp.get(l) instanceof Villain)
							&& !(targetChamp.get(l) instanceof Hero))
						targetChamp.remove(l);
				}
				getCurrentChampion().useLeaderAbility(targetChamp);
				count++;
			} else if (count > 0)
				throw new LeaderAbilityAlreadyUsedException();
		} else if (secondPlayer.getLeader().equals(getCurrentChampion())) {

		} else
			throw new LeaderNotCurrentException();
	}

	// Method is called when the current champion decides to end his turn
	public void endTurn() {
		// removes the current Champion form the turnOrder Queue
		if (turnOrder.peekMin().equals(getCurrentChampion()))
			turnOrder.remove();

		if (turnOrder.isEmpty())
			prepareChampionTurns();
		else {
			// Removes an inactive champion from the turn order queue
			ArrayList<Champion> nxtChamp = new ArrayList<>();
			nxtChamp.add((Champion) turnOrder.remove());
			for (int i = 0; i < nxtChamp.size(); i++) {
				if (nxtChamp.get(i).getCondition() != Condition.INACTIVE && nxtChamp.get(i).getCondition() != Condition.KNOCKEDOUT)
					nxtChamp.get(i).setCurrentActionPoints(
							nxtChamp.get(i).getMaxActionPointsPerTurn());
				turnOrder.insert(nxtChamp.remove(i));
			}
		}
		for (int j = 0; j < 5; j++) {
			for (int k = 0; k < 5; k++) {
				if (board[j][k] instanceof Champion) {
					Champion c = (Champion) board[j][k];
					if (c.getAppliedEffects().size() > 0) {
						for (int l = 0; l < c.getAppliedEffects().size(); l++) {
							if (c.getAppliedEffects().get(l).getDuration() == 1) {
								c.getAppliedEffects()
										.get(l)
										.setDuration(
												c.getAppliedEffects().get(l)
														.getDuration() - 1);
								c.getAppliedEffects().get(l).remove(c);
							} else
								c.getAppliedEffects()
										.get(l)
										.setDuration(
												c.getAppliedEffects().get(l)
														.getDuration() - 1);

						}
					}
				}
			}
		}
	}
}
