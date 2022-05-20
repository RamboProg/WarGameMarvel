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

import javax.activity.InvalidActivityException;

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
			if (c.getCondition() != Condition.KNOCKEDOUT)
				turnOrder.insert(c);
		}
		for (Champion c : secondPlayer.getTeam()) {
			if (c.getCondition() != Condition.KNOCKEDOUT)
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

				&& getCurrentChampion().getCurrentActionPoints() > 0) {
			int x = getCurrentChampion().getLocation().x;
			int y = getCurrentChampion().getLocation().y;
			if ((x < 4 && x > 0) && (y < 4 && y > 0)) {
				switch (d) {
				case UP:
					if (getBoard()[y + 1][x] == null) {
						getCurrentChampion().setLocation(new Point(x, y + 1));
						getCurrentChampion()
								.setCurrentActionPoints(
										getCurrentChampion()
												.getCurrentActionPoints() - 1);
					} else
						throw new UnallowedMovementException(
								"You cannot move here!");
					break;
				case DOWN:
					if (getBoard()[y - 1][x] == null) {
						getCurrentChampion().setLocation(new Point(x, y - 1));
						getCurrentChampion()
								.setCurrentActionPoints(
										getCurrentChampion()
												.getCurrentActionPoints() - 1);
					} else
						throw new UnallowedMovementException(
								"You cannot move here!");
					break;
				case RIGHT:
					if (getBoard()[y][x + 1] == null) {
						getCurrentChampion().setLocation(new Point(x + 1, y));
						getCurrentChampion()
								.setCurrentActionPoints(
										getCurrentChampion()
												.getCurrentActionPoints() - 1);
					} else
						throw new UnallowedMovementException(
								"You cannot move here!");
					break;
				case LEFT:
					if (getBoard()[y][x - 1] == null) {
						getCurrentChampion().setLocation(new Point(x - 1, y));
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

	private static int manhattanDist(int X1, int Y1, int X2, int Y2) {
		int dist = Math.abs(X2 - X1) + Math.abs(Y2 - Y1);
		return dist;
	}

	private boolean dodgePresent(Champion c) {
		for (Effect e : c.getAppliedEffects()) {
			if (e instanceof Dodge)
				return true;
		}
		return false;
	}

	private boolean shieldPresent(Champion c) {
		for (Effect e : c.getAppliedEffects()) {
			if (e instanceof Shield)
				return true;
		}
		return false;
	}

	private ArrayList<Damageable> loadValidChamps(int max, Direction d) {
		Champion c = getCurrentChampion();
		Point p = c.getLocation();
		ArrayList<Damageable> targets = new ArrayList<Damageable>();
		for (int i = 0; i < max; i++) {
			switch (d) {
			case UP:
				p.y++;
				if ((p.x < 6 || p.x >= 0 || p.y < 6 || p.y >= 0)
						&& board[p.y][p.x] instanceof Champion
						|| board[p.y][p.x] instanceof Cover)
					targets.add((Damageable) board[p.y][p.x]);
				break;
			case DOWN:
				p.y--;
				if ((p.x < 6 || p.x >= 0 || p.y < 6 || p.y >= 0)
						&& board[p.y][p.x] instanceof Champion
						|| board[p.y][p.x] instanceof Cover)
					targets.add((Damageable) board[p.y][p.x]);
				break;
			case RIGHT:
				p.x++;
				if ((p.x < 6 || p.x >= 0 || p.y < 6 || p.y >= 0)
						&& board[p.y][p.x] instanceof Champion
						|| board[p.y][p.x] instanceof Cover)
					targets.add((Damageable) board[p.y][p.x]);
				break;
			case LEFT:
				p.x--;
				if ((p.x < 6 || p.x >= 0 || p.y < 6 || p.y >= 0)
						&& board[p.y][p.x] instanceof Champion
						|| board[p.y][p.x] instanceof Cover)
					targets.add((Damageable) board[p.y][p.x]);
				break;
			}
		}
		return targets;
	}

	public void attack(Direction d) throws ChampionDisarmedException,
			InvalidTargetException, NotEnoughResourcesException {
		boolean containsDisarm = false;
		for (int k = 0; k < getCurrentChampion().getAppliedEffects().size(); k++) {
			if (getCurrentChampion().getAppliedEffects().get(k) instanceof Disarm) {
				containsDisarm = true;
			}
		}
		ArrayList<Damageable> targets = new ArrayList<Damageable>();
		if (containsDisarm == true) {
			throw new ChampionDisarmedException("Your Champion is DISARMED");
		} else {
			if (getCurrentChampion().getCurrentActionPoints() > 2
					&& getCurrentChampion().getCondition() == Condition.ACTIVE
					&& getCurrentChampion().getAttackRange() < 5) {

				/*
				 * The below nested loop adds to the ArrayList the valid targets
				 * that are within range of the attacker
				 */

				for (int i = 0; i <= getCurrentChampion().getAttackRange(); i++) {
					for (int j = 0; j <= getCurrentChampion().getAttackRange(); j++) {
						if (board[j][i] != null
								&& board[j][i] != getCurrentChampion()) {
							targets.add((Damageable) board[j][i]);
						}
					}
				}
			}
			switch (d) {
			case UP:

				int dist = getCurrentChampion().getAttackRange();

				for (int i = 0; i < targets.size(); i++) {
					if (manhattanDist(getCurrentChampion().getLocation().x,
							getCurrentChampion().getLocation().y, targets
									.get(i).getLocation().x, targets.get(i)
									.getLocation().y) < dist
							&& manhattanDist(
									getCurrentChampion().getLocation().x,
									getCurrentChampion().getLocation().y,
									targets.get(i).getLocation().x, targets
											.get(i).getLocation().y) != 0) {
						dist = i;
					}
				}

				if (getCurrentChampion() instanceof Hero) {
					if (targets.get(dist) instanceof Villain
							|| targets.get(dist) instanceof AntiHero) {
						Champion c = (Champion) targets.get(dist);
						if (dodgePresent(c)) {
							int x = (int) (Math.random() * 2);
							if (x == 1) {
								c.setCurrentHP((int) (c.getCurrentHP() - getCurrentChampion()
										.getAttackDamage() * 1.5));
								if (c.getCurrentHP() == 0) {
									c.setCondition(Condition.KNOCKEDOUT);
									targets.remove(c);

								}
							}
						}

						if (shieldPresent(c) == false) {
							c.setCurrentHP((int) (c.getCurrentHP() - getCurrentChampion()
									.getAttackDamage() * 1.5));
							if (c.getCurrentHP() == 0) {
								c.setCondition(Condition.KNOCKEDOUT);
								targets.remove(c);

							}
						}

					} else {
						if (getCurrentChampion() instanceof Villain) {
							if (targets.get(dist) instanceof Hero
									|| targets.get(dist) instanceof AntiHero) {
								int x = (int) (Math.random() * 2);
								if (x == 1) {
									targets.get(dist)
											.setCurrentHP(
													(int) (targets.get(dist)
															.getCurrentHP() - getCurrentChampion()
															.getAttackDamage() * 1.5));
									if (targets.get(dist).getCurrentHP() == 0) {
										Champion c = (Champion) targets
												.get(dist);
										c.setCondition(Condition.KNOCKEDOUT);
										targets.remove(targets.get(dist));

									}
								}
							} else if (getCurrentChampion() instanceof AntiHero) {
								if (targets.get(dist) instanceof Villain
										|| targets.get(dist) instanceof Hero) {
									int x = (int) (Math.random() * 2);
									if (x == 1) {
										targets.get(dist)
												.setCurrentHP(
														(int) (targets
																.get(dist)
																.getCurrentHP() - getCurrentChampion()
																.getAttackDamage() * 1.5));
										if (targets.get(dist).getCurrentHP() == 0) {
											Champion c = (Champion) targets
													.get(dist);
											c.setCondition(Condition.KNOCKEDOUT);
											targets.remove(targets.get(dist));

										}
									}
								} else if (targets.get(dist) instanceof Champion
										|| targets.get(dist) instanceof Cover) {
									if (targets.get(dist).getCurrentHP() == 0) {
										Champion c = (Champion) targets
												.get(dist);
										c.setCondition(Condition.KNOCKEDOUT);
										targets.remove(targets.get(dist));

									}
								}
							}
						}
					}
				}
				break;
			case RIGHT:

				int min1 = getCurrentChampion().getAttackRange();

				for (int i = 0; i < targets.size(); i++) {
					if ((targets.get(i).getLocation().y - getCurrentChampion()
							.getLocation().y) < min1 && min1 > 0) {
						min1 = targets.get(i).getLocation().y
								- getCurrentChampion().getLocation().y;
						min1 = i;
					}
				}

				if (getCurrentChampion() instanceof Hero) {
					if (targets.get(min1) instanceof Villain
							|| targets.get(min1) instanceof AntiHero) {

						int x = (int) (Math.random() * 2);
						if (x == 1) {
							Champion c = (Champion) targets.get(min1);

							c.setCurrentHP((int) (targets.get(min1)
									.getCurrentHP() - getCurrentChampion()
									.getAttackDamage() * 1.5));
							if (targets.get(min1).getCurrentHP() == 0) {
								c.setCondition(Condition.KNOCKEDOUT);
								targets.remove(targets.get(min1));

							}
						}
					} else {
						if (getCurrentChampion() instanceof Villain) {
							if (targets.get(min1) instanceof Hero
									|| targets.get(min1) instanceof AntiHero) {
								int x = (int) (Math.random() * 2);
								if (x == 1) {
									Champion c = (Champion) targets.get(min1);

									c.setCurrentHP((int) (targets.get(min1)
											.getCurrentHP() - getCurrentChampion()
											.getAttackDamage() * 1.5));
									if (targets.get(min1).getCurrentHP() == 0) {
										c.setCondition(Condition.KNOCKEDOUT);
										targets.remove(targets.get(min1));

									}
								}
							} else if (getCurrentChampion() instanceof AntiHero) {
								if (targets.get(min1) instanceof Villain
										|| targets.get(min1) instanceof Hero) {
									targets.get(min1)
											.setCurrentHP(
													(int) (targets.get(min1)
															.getCurrentHP() - getCurrentChampion()
															.getAttackDamage() * 1.5));
									if (targets.get(min1).getCurrentHP() == 0) {
										Champion c = (Champion) targets
												.get(min1);
										c.setCondition(Condition.KNOCKEDOUT);
										targets.remove(targets.get(min1));

									}
								} else if (targets.get(min1) instanceof Champion
										|| targets.get(min1) instanceof Cover) {
									Champion c = (Champion) targets.get(min1);

									c.setCurrentHP((int) (targets.get(min1)
											.getCurrentHP() - getCurrentChampion()
											.getAttackDamage()));
									if (targets.get(min1).getCurrentHP() == 0) {
										c.setCondition(Condition.KNOCKEDOUT);
										targets.remove(targets.get(min1));
									}
								}
							}
						}
					}
				}
				break;
			case LEFT:

				int min2 = getCurrentChampion().getAttackRange();

				for (int i = 0; i < targets.size(); i++) {
					if ((targets.get(i).getLocation().y - getCurrentChampion()
							.getLocation().y) < min2 && min2 > 0) {
						min2 = targets.get(i).getLocation().y
								- getCurrentChampion().getLocation().y;
						min2 = i;
					}
				}

				if (getCurrentChampion() instanceof Hero) {
					if (targets.get(min2) instanceof Villain
							|| targets.get(min2) instanceof AntiHero) {
						int x = (int) (Math.random() * 2);
						if (x == 1) {
							Champion c = (Champion) targets.get(min2);

							c.setCurrentHP((int) (targets.get(min2)
									.getCurrentHP() - getCurrentChampion()
									.getAttackDamage() * 1.5));
							if (targets.get(min2).getCurrentHP() == 0) {
								c.setCondition(Condition.KNOCKEDOUT);
								targets.remove(targets.get(min2));

							}
						}

					} else {
						if (getCurrentChampion() instanceof Villain) {
							if (targets.get(min2) instanceof Hero
									|| targets.get(min2) instanceof AntiHero) {
								int x = (int) (Math.random() * 2);
								if (x == 1) {
									Champion c = (Champion) targets.get(min2);

									c.setCurrentHP((int) (targets.get(min2)
											.getCurrentHP() - getCurrentChampion()
											.getAttackDamage() * 1.5));
									if (targets.get(min2).getCurrentHP() == 0) {
										c.setCondition(Condition.KNOCKEDOUT);
										targets.remove(targets.get(min2));

									}
								}
							} else if (getCurrentChampion() instanceof AntiHero) {
								if (targets.get(min2) instanceof Villain
										|| targets.get(min2) instanceof Hero) {
									int x = (int) (Math.random() * 2);
									if (x == 1) {
										Champion c = (Champion) targets
												.get(min2);

										c.setCurrentHP((int) (targets.get(min2)
												.getCurrentHP() - getCurrentChampion()
												.getAttackDamage() * 1.5));
										if (targets.get(min2).getCurrentHP() == 0) {
											c.setCondition(Condition.KNOCKEDOUT);
											targets.remove(targets.get(min2));

										}
									}

								} else if (targets.get(min2) instanceof Champion
										|| targets.get(min2) instanceof Cover) {
									int x = (int) (Math.random() * 2);
									if (x == 1) {
										Champion c = (Champion) targets
												.get(min2);

										c.setCurrentHP((int) (targets.get(min2)
												.getCurrentHP() - getCurrentChampion()
												.getAttackDamage() * 1.5));
										if (targets.get(min2).getCurrentHP() == 0) {
											c.setCondition(Condition.KNOCKEDOUT);
											targets.remove(targets.get(min2));

										}
									}
								}
							}
						}
					}
				}
				break;
			case DOWN:

				int min3 = getCurrentChampion().getAttackRange();

				for (int i = 0; i < targets.size(); i++) {
					if ((targets.get(i).getLocation().x - getCurrentChampion()
							.getLocation().x) < min3 && min3 > 0) {
						min3 = targets.get(i).getLocation().x
								- getCurrentChampion().getLocation().x;
						min3 = i;
					}
				}

				if (getCurrentChampion() instanceof Hero) {
					if (targets.get(min3) instanceof Villain
							|| targets.get(min3) instanceof AntiHero) {
						int x = (int) (Math.random() * 2);
						if (x == 1) {
							Champion c = (Champion) targets.get(min3);

							c.setCurrentHP((int) (targets.get(min3)
									.getCurrentHP() - getCurrentChampion()
									.getAttackDamage() * 1.5));
							if (targets.get(min3).getCurrentHP() == 0) {
								c.setCondition(Condition.KNOCKEDOUT);
								targets.remove(targets.get(min3));

							}
						}
					} else {
						if (getCurrentChampion() instanceof Villain) {
							if (targets.get(min3) instanceof Hero
									|| targets.get(min3) instanceof AntiHero) {
								int x = (int) (Math.random() * 2);
								if (x == 1) {
									Champion c = (Champion) targets.get(min3);

									c.setCurrentHP((int) (targets.get(min3)
											.getCurrentHP() - getCurrentChampion()
											.getAttackDamage() * 1.5));
									if (targets.get(min3).getCurrentHP() == 0) {
										c.setCondition(Condition.KNOCKEDOUT);
										targets.remove(targets.get(min3));

									}
								}
							} else if (getCurrentChampion() instanceof AntiHero) {
								if (targets.get(min3) instanceof Villain
										|| targets.get(min3) instanceof Hero) {
									int x = (int) (Math.random() * 2);
									if (x == 1) {
										Champion c = (Champion) targets
												.get(min3);

										c.setCurrentHP((int) (targets.get(min3)
												.getCurrentHP() - getCurrentChampion()
												.getAttackDamage() * 1.5));
										if (targets.get(min3).getCurrentHP() == 0) {
											c.setCondition(Condition.KNOCKEDOUT);
											targets.remove(targets.get(min3));

										}
									}
								} else if (targets.get(min3) instanceof Champion
										|| targets.get(min3) instanceof Cover) {
									int x = (int) (Math.random() * 2);
									if (x == 1) {
										Champion c = (Champion) targets
												.get(min3);

										c.setCurrentHP((int) (targets.get(min3)
												.getCurrentHP() - getCurrentChampion()
												.getAttackDamage() * 1.5));
										if (targets.get(min3).getCurrentHP() == 0) {
											c.setCondition(Condition.KNOCKEDOUT);
											targets.remove(targets.get(min3));

										}
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
		}
		if (getCurrentChampion().getCurrentActionPoints() < 2)
			throw new NotEnoughResourcesException();
	}

	// Checks if ability is a type of CC
	private boolean isCC(Ability a) {
		if (a instanceof CrowdControlAbility)
			return true;
		else
			return false;
	}

	// Checks if ability is a type of Healing ability
	private boolean isHealing(Ability a) {
		if (a instanceof HealingAbility)
			return true;
		else
			return false;
	}

	// checks if ability is a type of Damaging ability
	private boolean isDmg(Ability a) {
		if (a instanceof DamagingAbility)
			return true;
		else
			return false;
	}

	// Which team has the current champion
	private Player whoContainCurrent() {
		if (firstPlayer.getTeam().contains(getCurrentChampion()))
			return firstPlayer;
		else
			return secondPlayer;
	}

	private Player oppositeTeam() {
		if (firstPlayer.getTeam().contains(getCurrentChampion()))
			return secondPlayer;
		else
			return firstPlayer;
	}

	public void castAbility(Ability a) throws NotEnoughResourcesException,
			CloneNotSupportedException {
		if (getCurrentChampion().getCurrentActionPoints() >= a
				.getRequiredActionPoints()) {
			if (getCurrentChampion().getMana() >= a.getManaCost()) {
				ArrayList<Damageable> targets = new ArrayList<Damageable>();

				if (isHealing(a)) {
					if (a.getCastArea() == AreaOfEffect.SELFTARGET) {
						targets.add(getCurrentChampion());
						a.execute(targets);
					}
					if (a.getCastArea() == AreaOfEffect.TEAMTARGET) {
						targets.addAll(whoContainCurrent().getTeam());
						for (Damageable d : targets) {
							if ((manhattanDist(d.getLocation().x,
									d.getLocation().y, getCurrentChampion()
											.getLocation().x,
									getCurrentChampion().getLocation().y) > a
									.getCastRange())) {
								targets.remove(d);
							}
						}
						a.execute(targets);
					}
					if (a.getCastArea() == AreaOfEffect.SURROUND) {
						Point currentLoc = getCurrentChampion().getLocation();
						targets.addAll(whoContainCurrent().getTeam());
						for (Damageable d : targets) {
							if ((manhattanDist(d.getLocation().x,
									d.getLocation().y, getCurrentChampion()
											.getLocation().x,
									getCurrentChampion().getLocation().y) > 1)) {
								targets.remove(d);
							}
						}

						// Checks if the top right corner slot is within
						// the team
						Point cornerXY = getCurrentChampion().getLocation();
						int cornerXY_X = getCurrentChampion().getLocation().x + 1;
						int cornerXY_Y = getCurrentChampion().getLocation().y + 1;

						if (board[cornerXY_Y][cornerXY_X] instanceof Champion) {
							if (whoContainCurrent().getTeam().contains(
									board[cornerXY_Y][cornerXY_X])) {
								targets.add((Damageable) board[cornerXY_Y][cornerXY_X]);
							}
						}

						// Checks if the bottom right corner slot is
						// within the team
						Point cornerXy = getCurrentChampion().getLocation();
						int cornerXy_X = getCurrentChampion().getLocation().x + 1;
						int cornerXy_Y = getCurrentChampion().getLocation().y - 1;

						if (board[cornerXY_Y][cornerXY_X] instanceof Champion) {
							if (whoContainCurrent().getTeam().contains(
									board[cornerXy_Y][cornerXy_X])) {
								targets.add((Damageable) board[cornerXy_Y][cornerXy_X]);
							}
						}

						// Checks if the bottom left corner slot is
						// within the team
						Point cornerxy = getCurrentChampion().getLocation();
						int cornerxy_X = getCurrentChampion().getLocation().x - 1;
						int cornerxy_Y = getCurrentChampion().getLocation().y - 1;

						if (board[cornerxy_Y][cornerxy_X] instanceof Champion) {
							if (whoContainCurrent().getTeam().contains(
									board[cornerxy_Y][cornerxy_X])) {
								targets.add((Damageable) board[cornerxy_Y][cornerxy_X]);
							}
						}

						// Checks if the top left corner slot is within
						// the team
						Point cornerxY = getCurrentChampion().getLocation();
						int cornerxY_X = getCurrentChampion().getLocation().x - 1;
						int cornerxY_Y = getCurrentChampion().getLocation().y + 1;

						if (board[cornerxY_Y][cornerxY_X] instanceof Champion) {
							if (whoContainCurrent().getTeam().contains(
									board[cornerxY_Y][cornerxY_X])) {
								targets.add((Damageable) board[cornerxY_Y][cornerxY_X]);
							}
						}
					}
					a.execute(targets);
				}

				else if (isDmg(a)) {
					if (a.getCastArea() == AreaOfEffect.TEAMTARGET) {

						targets.addAll(oppositeTeam().getTeam());
						for (Damageable d : targets) {
							if ((manhattanDist(d.getLocation().x,
									d.getLocation().y, getCurrentChampion()
											.getLocation().x,
									getCurrentChampion().getLocation().y) > a
									.getCastRange())) {
								targets.remove(d);
							}
						}
						a.execute(targets);
					}

					// Calculate distance is better (manhattan)
					if (a.getCastArea() == AreaOfEffect.SURROUND) {
						Point currentLoc = getCurrentChampion().getLocation();

						targets.addAll(whoContainCurrent().getTeam());
						for (Damageable d : targets) {
							if ((manhattanDist(d.getLocation().x,
									d.getLocation().y, getCurrentChampion()
											.getLocation().x,
									getCurrentChampion().getLocation().y) > a
									.getCastRange())) {
								targets.remove(d);
							}
						}

						// Checks if the top right corner slot is within
						// the team
						Point cornerXY = getCurrentChampion().getLocation();
						int cornerXY_X = getCurrentChampion().getLocation().x + 1;
						int cornerXY_Y = getCurrentChampion().getLocation().y + 1;

						if (board[cornerXY_Y][cornerXY_X] instanceof Champion) {
							if (oppositeTeam().getTeam().contains(
									board[cornerXY_Y][cornerXY_X])) {
								targets.add((Damageable) board[cornerXY_Y][cornerXY_X]);
							}
						}

						// Checks if the bottom right corner slot is
						// within the team
						Point cornerXy = getCurrentChampion().getLocation();
						int cornerXy_X = getCurrentChampion().getLocation().x + 1;
						int cornerXy_Y = getCurrentChampion().getLocation().y - 1;

						if (board[cornerXY_Y][cornerXY_X] instanceof Champion) {
							if (oppositeTeam().getTeam().contains(
									board[cornerXy_Y][cornerXy_X])) {
								targets.add((Damageable) board[cornerXy_Y][cornerXy_X]);
							}
						}

						// Checks if the bottom left corner slot is
						// within the team
						Point cornerxy = getCurrentChampion().getLocation();
						int cornerxy_X = getCurrentChampion().getLocation().x - 1;
						int cornerxy_Y = getCurrentChampion().getLocation().y - 1;

						if (board[cornerxy_Y][cornerxy_X] instanceof Champion) {
							if (oppositeTeam().getTeam().contains(
									board[cornerxy_Y][cornerxy_X])) {
								targets.add((Damageable) board[cornerxy_Y][cornerxy_X]);
							}
						}

						// Checks if the top left corner slot is within
						// the team
						Point cornerxY = getCurrentChampion().getLocation();
						int cornerxY_X = getCurrentChampion().getLocation().x - 1;
						int cornerxY_Y = getCurrentChampion().getLocation().y + 1;

						if (board[cornerxY_Y][cornerxY_X] instanceof Champion) {
							if (oppositeTeam().getTeam().contains(
									board[cornerxY_Y][cornerxY_X])) {
								targets.add((Damageable) board[cornerxY_Y][cornerxY_X]);
							}
						}
					}
					a.execute(targets);

				} else if (isCC(a)) {

					CrowdControlAbility ccA = (CrowdControlAbility) a;
					if (ccA.getEffect().getType() == EffectType.DEBUFF) {
						if (a.getCastArea() == AreaOfEffect.SURROUND) {
							Point currentLoc = getCurrentChampion()
									.getLocation();

							targets.addAll(oppositeTeam().getTeam());
							for (Damageable d : targets) {
								if ((manhattanDist(d.getLocation().x,
										d.getLocation().y, getCurrentChampion()
												.getLocation().x,
										getCurrentChampion().getLocation().y) > a
										.getCastRange())) {
									targets.remove(d);
								}
							}

							ArrayList<Champion> targetChamps = new ArrayList<Champion>();
							for (Damageable damageable : targets) {
								if (damageable instanceof Champion)
									targetChamps.add((Champion) damageable);
							}

							for (int i = 0; i < targetChamps.size(); i++) {
								if (targetChamps.get(i).getAppliedEffects()
										.get(i) instanceof Shield) {
									targetChamps.remove(i);
									targetChamps
											.get(i)
											.getAppliedEffects()
											.remove(targetChamps.get(i)
													.getAppliedEffects().get(i));
								}
							}

							// Checks if the top right corner slot is within
							// the team
							Point cornerXY = getCurrentChampion().getLocation();
							int cornerXY_X = getCurrentChampion().getLocation().x + 1;
							int cornerXY_Y = getCurrentChampion().getLocation().y + 1;

							if (board[cornerXY_Y][cornerXY_X] instanceof Champion) {
								if (oppositeTeam().getTeam().contains(
										board[cornerXY_Y][cornerXY_X])) {
									targets.add((Damageable) board[cornerXY_Y][cornerXY_X]);
								}
							}

							// Checks if the bottom right corner slot is
							// within the team
							Point cornerXy = getCurrentChampion().getLocation();
							int cornerXy_X = getCurrentChampion().getLocation().x + 1;
							int cornerXy_Y = getCurrentChampion().getLocation().y - 1;

							if (board[cornerXY_Y][cornerXY_X] instanceof Champion) {
								if (oppositeTeam().getTeam().contains(
										board[cornerXy_Y][cornerXy_X])) {
									targets.add((Damageable) board[cornerXy_Y][cornerXy_X]);
								}
							}

							// Checks if the bottom left corner slot is
							// within the team
							Point cornerxy = getCurrentChampion().getLocation();
							int cornerxy_X = getCurrentChampion().getLocation().x - 1;
							int cornerxy_Y = getCurrentChampion().getLocation().y - 1;

							if (board[cornerxy_Y][cornerxy_X] instanceof Champion) {
								if (oppositeTeam().getTeam().contains(
										board[cornerxy_Y][cornerxy_X])) {
									targets.add((Damageable) board[cornerxy_Y][cornerxy_X]);
								}
							}

							// Checks if the top left corner slot is within
							// the team
							Point cornerxY = getCurrentChampion().getLocation();
							int cornerxY_X = getCurrentChampion().getLocation().x - 1;
							int cornerxY_Y = getCurrentChampion().getLocation().y + 1;

							if (board[cornerxY_Y][cornerxY_X] instanceof Champion) {
								if (oppositeTeam().getTeam().contains(
										board[cornerxY_Y][cornerxY_X])) {
									targets.add((Damageable) board[cornerxY_Y][cornerxY_X]);
								}
							}
						}
						a.execute(targets);

					}

					else if (ccA.getEffect().getType() == EffectType.BUFF) {
						if (a.getCastArea() == AreaOfEffect.SURROUND) {
							Point currentLoc = getCurrentChampion()
									.getLocation();

							targets.addAll(whoContainCurrent().getTeam());
							for (Damageable d : targets) {
								if ((manhattanDist(d.getLocation().x,
										d.getLocation().y, getCurrentChampion()
												.getLocation().x,
										getCurrentChampion().getLocation().y) > a
										.getCastRange())) {
									targets.remove(d);
								}
							}

							ArrayList<Champion> targetChamps = new ArrayList<Champion>();
							for (Damageable damageable : targets) {
								if (damageable instanceof Champion)
									targetChamps.add((Champion) damageable);
							}

							// Checks if the top right corner slot is within
							// the team
							Point cornerXY = getCurrentChampion().getLocation();
							int cornerXY_X = getCurrentChampion().getLocation().x + 1;
							int cornerXY_Y = getCurrentChampion().getLocation().y + 1;

							if (board[cornerXY_Y][cornerXY_X] instanceof Champion) {
								if (oppositeTeam().getTeam().contains(
										board[cornerXY_Y][cornerXY_X])) {
									targets.add((Damageable) board[cornerXY_Y][cornerXY_X]);
								}
							}

							// Checks if the bottom right corner slot is
							// within the team
							Point cornerXy = getCurrentChampion().getLocation();
							int cornerXy_X = getCurrentChampion().getLocation().x + 1;
							int cornerXy_Y = getCurrentChampion().getLocation().y - 1;

							if (board[cornerXY_Y][cornerXY_X] instanceof Champion) {
								if (oppositeTeam().getTeam().contains(
										board[cornerXy_Y][cornerXy_X])) {
									targets.add((Damageable) board[cornerXy_Y][cornerXy_X]);
								}
							}

							// Checks if the bottom left corner slot is
							// within the team
							Point cornerxy = getCurrentChampion().getLocation();
							int cornerxy_X = getCurrentChampion().getLocation().x - 1;
							int cornerxy_Y = getCurrentChampion().getLocation().y - 1;

							if (board[cornerxy_Y][cornerxy_X] instanceof Champion) {
								if (oppositeTeam().getTeam().contains(
										board[cornerxy_Y][cornerxy_X])) {
									targets.add((Damageable) board[cornerxy_Y][cornerxy_X]);
								}
							}

							// Checks if the top left corner slot is within
							// the team
							Point cornerxY = getCurrentChampion().getLocation();
							int cornerxY_X = getCurrentChampion().getLocation().x - 1;
							int cornerxY_Y = getCurrentChampion().getLocation().y + 1;

							if (board[cornerxY_Y][cornerxY_X] instanceof Champion) {
								if (oppositeTeam().getTeam().contains(
										board[cornerxY_Y][cornerxY_X])) {
									targets.add((Damageable) board[cornerxY_Y][cornerxY_X]);
								}
							}
						}
						a.execute(targets);

					}
				}
			} else
				throw new NotEnoughResourcesException();
		} else
			throw new NotEnoughResourcesException();

	}

	public void castAbility(Ability a, Direction d)
			throws NotEnoughResourcesException, AbilityUseException,
			CloneNotSupportedException {
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
						dist = manhattanDist(targets.get(l).getLocation().x,
								targets.get(l).getLocation().y,
								getCurrentChampion().getLocation().x,
								getCurrentChampion().getLocation().y);
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
						dist1 = manhattanDist(targets.get(l).getLocation().x,
								targets.get(l).getLocation().y,
								getCurrentChampion().getLocation().x,
								getCurrentChampion().getLocation().y);
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
						dist2 = manhattanDist(targets.get(l).getLocation().x,
								targets.get(l).getLocation().y,
								getCurrentChampion().getLocation().x,
								getCurrentChampion().getLocation().y);
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
						dist3 = manhattanDist(targets.get(l).getLocation().x,
								targets.get(l).getLocation().y,
								getCurrentChampion().getLocation().x,
								getCurrentChampion().getLocation().y);
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
			throws AbilityUseException, NotEnoughResourcesException,
			CloneNotSupportedException {
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
			dist = manhattanDist(x, y, getCurrentChampion().getLocation().x,
					getCurrentChampion().getLocation().y);
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
		if (firstPlayer.getLeader() == getCurrentChampion()) {
			if (firstLeaderAbilityUsed)
				throw new LeaderAbilityAlreadyUsedException();
			ArrayList<Champion> targetChamp = new ArrayList<Champion>();
			if (getCurrentChampion() instanceof Hero) {
				targetChamp.addAll(firstPlayer.getTeam());
				getCurrentChampion().useLeaderAbility(targetChamp);
			} else if (getCurrentChampion() instanceof Villain) {
				for (Champion champion : secondPlayer.getTeam()) {
					if (champion.getCurrentHP() <= (int) (0.3 * champion
							.getMaxHP()))
						targetChamp.add(champion);
					getCurrentChampion().useLeaderAbility(targetChamp);
				}
			} else if (getCurrentChampion() instanceof AntiHero) {
				targetChamp.addAll(firstPlayer.getTeam());
				targetChamp.addAll(secondPlayer.getTeam());
				targetChamp.remove(firstPlayer.getLeader());
				targetChamp.remove(secondPlayer.getLeader());
				getCurrentChampion().useLeaderAbility(targetChamp);
			}
			getCurrentChampion().useLeaderAbility(targetChamp);
			firstLeaderAbilityUsed = true;
		} else if (secondPlayer.getLeader().equals(getCurrentChampion())) {
			if (secondLeaderAbilityUsed)
				throw new LeaderAbilityAlreadyUsedException();
			ArrayList<Champion> targetChamp = new ArrayList<Champion>();
			if (getCurrentChampion() instanceof Hero) {
				targetChamp.addAll(secondPlayer.getTeam());
				getCurrentChampion().useLeaderAbility(targetChamp);
			} else if (getCurrentChampion() instanceof Villain) {
				for (Champion champion : firstPlayer.getTeam()) {
					if (champion.getCurrentHP() <= (int) (0.3 * champion
							.getMaxHP()))
						targetChamp.add(champion);
					getCurrentChampion().useLeaderAbility(targetChamp);
				}
			} else if (getCurrentChampion() instanceof AntiHero) {
				targetChamp.addAll(firstPlayer.getTeam());
				targetChamp.addAll(secondPlayer.getTeam());
				targetChamp.remove(firstPlayer.getLeader());
				targetChamp.remove(secondPlayer.getLeader());
				getCurrentChampion().useLeaderAbility(targetChamp);
			}
			getCurrentChampion().useLeaderAbility(targetChamp);
			secondLeaderAbilityUsed = true;

		} else
			throw new LeaderNotCurrentException();
	}

	// Method is called when the current champion decides to end his turn
	public void endTurn() {
		// removes the current Champion form the turnOrder Queue
		turnOrder.remove();

		if (turnOrder.isEmpty())
			prepareChampionTurns();
		Champion c = getCurrentChampion();
		if (c.getAppliedEffects().size() > 0) {
			for (int l = 0; l < c.getAppliedEffects().size(); l++) {
				c.getAppliedEffects()
						.get(l)
						.setDuration(
								c.getAppliedEffects().get(l).getDuration() - 1);
				if (c.getAppliedEffects().get(l).getDuration() == 0) {
					c.getAppliedEffects().get(l).remove(c);
					c.getAppliedEffects().remove(l);
					l--;
				}

			}
		}
		for (Ability ability : c.getAbilities()) {
			if (ability.getCurrentCooldown() > 0)
				ability.setCurrentCooldown(ability.getCurrentCooldown() - 1);
		}
		c.setCurrentActionPoints(c.getMaxActionPointsPerTurn());
		if (c.getCondition() == Condition.INACTIVE)
			endTurn();
	}
}
