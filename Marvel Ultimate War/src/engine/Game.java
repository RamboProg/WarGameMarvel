package engine;

import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import model.abilities.*;
import model.effects.*;
import model.world.*;

public class Game {
  private Player firstPlayer;
  private Player secondPlayer;
  private boolean firstLeaderAbilityUsed;
  private boolean secondLeaderAbilityUsed;
  private Object[][] board;
  private static ArrayList<Champion> availableChampions;
  private static ArrayList<Ability> availableAbilities;
  private PriorityQueue turnOrder;
  private static final int BOARDHEIGHT = 5;
  private static final int BOARDWIDTH = 5;

  public Game(Player firstPlayer, Player secondPlayer) throws IOException {
    this.firstPlayer = firstPlayer;
    this.secondPlayer = secondPlayer;
    board = new Object[BOARDHEIGHT][BOARDWIDTH];
    availableChampions = new ArrayList<Champion>();
    availableAbilities = new ArrayList<Ability>();
    placeChampions();
    placeCovers();
    this.turnOrder =
      new PriorityQueue(
        firstPlayer.getTeam().size() + secondPlayer.getTeam().size()
      );
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
    }

    for (int j = 0; j < secondPlayer.getTeam().size(); j++) {
      board[4][j + 1] = secondPlayer.getTeam().get(j);
      secondPlayer.getTeam().get(j).setLocation(new Point(4, j + 1));
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
        HealingAbility a = new HealingAbility(
          name,
          manaCost,
          baseCooldown,
          castRange,
          areaOfEffect,
          requiredActionsPerTurn,
          healAmount
        );
        availableAbilities.add(a);
      } else {
        if (result[0].equals("DMG")) {
          int damageAmount = Integer.parseInt(result[7]);
          DamagingAbility a = new DamagingAbility(
            name,
            manaCost,
            baseCooldown,
            castRange,
            areaOfEffect,
            requiredActionsPerTurn,
            damageAmount
          );
          availableAbilities.add(a);
        } else {
          // String effectName = result[7];
          int effectDuration = Integer.parseInt(result[8]);

          switch (result[7]) {
            case "Disarm":
              Disarm d = new Disarm(effectDuration);
              CrowdControlAbility cc2 = new CrowdControlAbility(
                name,
                manaCost,
                baseCooldown,
                castRange,
                areaOfEffect,
                requiredActionsPerTurn,
                d
              );
              availableAbilities.add(cc2);
              break;
            case "Silence":
              Silence s = new Silence(effectDuration);
              CrowdControlAbility cc3 = new CrowdControlAbility(
                name,
                manaCost,
                baseCooldown,
                castRange,
                areaOfEffect,
                requiredActionsPerTurn,
                s
              );
              availableAbilities.add(cc3);
              break;
            case "Root":
              Root r = new Root(effectDuration);
              CrowdControlAbility cc4 = new CrowdControlAbility(
                name,
                manaCost,
                baseCooldown,
                castRange,
                areaOfEffect,
                requiredActionsPerTurn,
                r
              );
              availableAbilities.add(cc4);
              break;
            case "Shock":
              Shock sh = new Shock(effectDuration);
              CrowdControlAbility cc5 = new CrowdControlAbility(
                name,
                manaCost,
                baseCooldown,
                castRange,
                areaOfEffect,
                requiredActionsPerTurn,
                sh
              );
              availableAbilities.add(cc5);
              break;
            case "Stun":
              Stun st = new Stun(effectDuration);
              CrowdControlAbility cc1 = new CrowdControlAbility(
                name,
                manaCost,
                baseCooldown,
                castRange,
                areaOfEffect,
                requiredActionsPerTurn,
                st
              );
              availableAbilities.add(cc1);
              break;
            case "PowerUp":
              PowerUp p = new PowerUp(effectDuration);
              CrowdControlAbility cc7 = new CrowdControlAbility(
                name,
                manaCost,
                baseCooldown,
                castRange,
                areaOfEffect,
                requiredActionsPerTurn,
                p
              );
              availableAbilities.add(cc7);
              break;
            case "Shield":
              Shield shi = new Shield(effectDuration);
              CrowdControlAbility cc8 = new CrowdControlAbility(
                name,
                manaCost,
                baseCooldown,
                castRange,
                areaOfEffect,
                requiredActionsPerTurn,
                shi
              );
              availableAbilities.add(cc8);
              break;
            case "SpeedUp":
              SpeedUp su = new SpeedUp(effectDuration);
              CrowdControlAbility cc9 = new CrowdControlAbility(
                name,
                manaCost,
                baseCooldown,
                castRange,
                areaOfEffect,
                requiredActionsPerTurn,
                su
              );
              availableAbilities.add(cc9);
              break;
            case "Embrace":
              Embrace e = new Embrace(effectDuration);
              CrowdControlAbility cc10 = new CrowdControlAbility(
                name,
                manaCost,
                baseCooldown,
                castRange,
                areaOfEffect,
                requiredActionsPerTurn,
                e
              );
              availableAbilities.add(cc10);
              break;
            case "Dodge":
              Dodge ddg = new Dodge(effectDuration);
              CrowdControlAbility cc6 = new CrowdControlAbility(
                name,
                manaCost,
                baseCooldown,
                castRange,
                areaOfEffect,
                requiredActionsPerTurn,
                ddg
              );
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
        AntiHero a = new AntiHero(
          name,
          maxHP,
          mana,
          actions,
          speed,
          attackRange,
          attackDamage
        );
        a.getAbilities().add(locateAbility(ability1Name));
        a.getAbilities().add(locateAbility(ability2Name));
        a.getAbilities().add(locateAbility(ability3Name));
        a.setSpeed(speed);
        availableChampions.add(a);
      } else {
        if (result[0].equals("H")) {
          Hero h = new Hero(
            name,
            maxHP,
            mana,
            actions,
            speed,
            attackRange,
            attackDamage
          );
          h.getAbilities().add(locateAbility(ability1Name));
          h.getAbilities().add(locateAbility(ability2Name));
          h.getAbilities().add(locateAbility(ability3Name));
          h.setSpeed(speed);
          availableChampions.add(h);
        } else {
          Villain v = new Villain(
            name,
            maxHP,
            mana,
            actions,
            speed,
            attackRange,
            attackDamage
          );
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
      if (ability.getName().equals(abilityName)) return ability;
    }
    return null;
  }

  private void prepareChampionTurns() {
    for (Champion c : firstPlayer.getTeam()) {
      if (c.getCondition() != Condition.KNOCKEDOUT) turnOrder.insert(c);
    }
    for (Champion c : secondPlayer.getTeam()) {
      if (c.getCondition() != Condition.KNOCKEDOUT) turnOrder.insert(c);
    }
  }

  public Champion getCurrentChampion() {
    return (Champion) turnOrder.peekMin();
  }

  public Player checkGameOver() {
    if(firstPlayer.getTeam().size() == 0)
      return secondPlayer;
    if(secondPlayer.getTeam().size() == 0)
      return firstPlayer;
    return null;
  }

  public void move(Direction d)
    throws UnallowedMovementException, NotEnoughResourcesException {
      Champion c = getCurrentChampion();
      int targetX;
      int targetY;
      if (d == Direction.UP) {
        targetX = c.getLocation().x + 1;
        targetY = c.getLocation().y;
      } else if (d == Direction.DOWN) {
        targetX = c.getLocation().x - 1;
        targetY = c.getLocation().y;
      } else if (d == Direction.LEFT) {
        targetX = c.getLocation().x;
        targetY = c.getLocation().y - 1;
      } else {
        targetX = c.getLocation().x;
        targetY = c.getLocation().y + 1;
      }
      if (c.getCurrentActionPoints() < 1)
        throw new NotEnoughResourcesException(
            "You need at least 1 action point in order to move in any direction.");
      if (targetX < 0 || targetX > 4 || targetY < 0 || targetY > 4)
        throw new UnallowedMovementException("You can only move inside the borders of the game.");
      if (c.getCondition() == Condition.ROOTED)
        throw new UnallowedMovementException("You cannot move while rooted.");
      if (board[targetX][targetY] != null)
        throw new UnallowedMovementException("You cannot move to an unempty cell.");
      c.setCurrentActionPoints(c.getCurrentActionPoints() - 1);
      board[c.getLocation().x][c.getLocation().y] = null;
      board[targetX][targetY] = c;
      c.setLocation(new Point(targetX, targetY));
  }

  private static int manhattanDist(Point m, Point n) {
    int dist = Math.abs(m.x - n.x) + Math.abs(m.y - n.y);
    return dist;
  }


  private boolean checkFriendly(Damageable d) {
		Champion c = getCurrentChampion();
		if ((firstPlayer.getTeam().contains(c) && firstPlayer.getTeam().contains(d))
				|| (secondPlayer.getTeam().contains(c) && secondPlayer.getTeam().contains(d)))
			return true;
		return false;
	}
  private boolean checkBonus(Damageable d) {
		Champion c = getCurrentChampion();
		if ((c instanceof Hero && d instanceof Hero) || (c instanceof Villain && d instanceof Villain)
				|| (c instanceof AntiHero && d instanceof AntiHero) || d instanceof Cover)
			return false;
		return true;
	}
  private void clear(Damageable d) {
		Point location = d.getLocation();
		if (d instanceof Champion) {
			ArrayList<Champion> temp = new ArrayList<>();
			while (!turnOrder.isEmpty()) {
				temp.add((Champion) turnOrder.remove());
			}
			temp.remove(d);
			while (!temp.isEmpty()) {
				turnOrder.insert(temp.remove(0));
			}
			firstPlayer.getTeam().remove(d);
			secondPlayer.getTeam().remove(d);
		}
		board[location.x][location.y] = null;
	}


  public void attack(Direction d)
    throws ChampionDisarmedException, InvalidTargetException, NotEnoughResourcesException {
    
      Champion c = getCurrentChampion();
      for (Effect e : c.getAppliedEffects()) {
        if (e instanceof Disarm)
          throw new ChampionDisarmedException();
      }
      if (c.getCurrentActionPoints() < 2)
        throw new NotEnoughResourcesException();
      int i = 1;
      boolean found = false;
      Damageable target = null;
      while (i <= c.getAttackRange() && !found) {
        if (d == Direction.DOWN && c.getLocation().x - i >= 0
            && board[c.getLocation().x - i][c.getLocation().y] != null) {
          target = (Damageable) board[c.getLocation().x - i][c.getLocation().y];
          if (!checkFriendly(target))
            found = true;
        } else if (d == Direction.UP && c.getLocation().x + i <= 4
            && board[c.getLocation().x + i][c.getLocation().y] != null) {
          target = (Damageable) board[c.getLocation().x + i][c.getLocation().y];
          if (!checkFriendly(target))
            found = true;
        } else if (d == Direction.LEFT && c.getLocation().y - i >= 0
            && board[c.getLocation().x][c.getLocation().y - i] != null) {
          target = (Damageable) board[c.getLocation().x][c.getLocation().y - i];
          if (!checkFriendly(target))
            found = true;
        } else if (d == Direction.RIGHT && c.getLocation().y + i <= 4
            && board[c.getLocation().x][c.getLocation().y + i] != null) {
          target = (Damageable) board[c.getLocation().x][c.getLocation().y + i];
          if (!checkFriendly(target))
            found = true;
        }
        i++;
      }
      if (found) {
        c.setCurrentActionPoints(c.getCurrentActionPoints() - 2);
        if (target instanceof Champion) {
          for (Effect e : ((Champion) target).getAppliedEffects()) {
            if (e instanceof Shield) {
              e.remove((Champion) target);
              ((Champion) target).getAppliedEffects().remove(e);
              return;
            }
          }
          for (Effect e : ((Champion) target).getAppliedEffects()) {
            if (e instanceof Dodge) {
              int rand = (int) (Math.random() * 10);
              if (rand < 5) {
                return;
              }
            }
          }
        }
        if (checkBonus(target)) {
          target.setCurrentHP(target.getCurrentHP() - (int) (c.getAttackDamage() * 1.5));
        } else
          target.setCurrentHP(target.getCurrentHP() - c.getAttackDamage());
        if (target.getCurrentHP() == 0) {
          clear(target);
        }
      }
    }
  


  public void validateCastAbility(Ability a) throws AbilityUseException, NotEnoughResourcesException {
		Champion c = getCurrentChampion();
		if (!c.getAbilities().contains(a))
			throw new AbilityUseException("You can only use abilities your current champion has.");
		boolean sil = false;
		for (Effect e : c.getAppliedEffects()) {
			if (e instanceof Silence)
				sil = true;
		}
		if (sil)
			throw new AbilityUseException("You cannot use abilities while your champion is silenced.");
		if (c.getMana() < a.getManaCost())
			throw new NotEnoughResourcesException("You cannot use abilities you do not have enough mana for.");
		if (c.getCurrentActionPoints() < a.getRequiredActionPoints())
			throw new NotEnoughResourcesException("You cannot use abilities you do not have enough action points for.");
		if (a.getCurrentCooldown() > 0)
			throw new AbilityUseException("You cannot use abilities that are still in cooldown.");
	}

  private boolean checkRange(int range, Damageable d) {
		int dis = manhattanDist(d.getLocation(), getCurrentChampion().getLocation());
		if (dis <= range)
			return true;
		return false;
	}

  private ArrayList<Damageable> getTargets(Ability a) {
		ArrayList<Damageable> targets = new ArrayList<Damageable>();
		Point center = getCurrentChampion().getLocation();
		if (a.getCastArea() == AreaOfEffect.SURROUND) {
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (center.x + i >= 0 && center.x + i <= 4 && center.y + j >= 0 && center.y + j <= 4
							&& !(i == 0 && j == 0) && board[center.x + i][center.y + j] != null) {
						targets.add((Damageable) board[center.x + i][center.y + j]);
					}
				}
			}
		} else if (a.getCastArea() == AreaOfEffect.SELFTARGET) {
			targets.add(getCurrentChampion());
		} else if (a.getCastArea() == AreaOfEffect.TEAMTARGET) {
			for (Champion c2 : secondPlayer.getTeam()) {
				if (checkRange(a.getCastRange(), c2))
					targets.add(c2);
			}
			for (Champion c2 : firstPlayer.getTeam()) {
				if (checkRange(a.getCastRange(), c2))
					targets.add(c2);
			}
		}
		return targets;
	}
  private ArrayList<Damageable> filterTargets(ArrayList<Damageable> t, Ability a) {
		ArrayList<Damageable> targets = new ArrayList<Damageable>();
		for (Damageable d : t) {
			if (a instanceof DamagingAbility && !checkFriendly(d)) {
				targets.add(d);
			} else if (a instanceof HealingAbility && checkFriendly(d)) {
				targets.add(d);
			} else if (a instanceof CrowdControlAbility
					&& ((CrowdControlAbility) a).getEffect().getType() == EffectType.BUFF && d instanceof Champion
					&& checkFriendly(d)) {
				targets.add(d);
			} else if (a instanceof CrowdControlAbility
					&& ((CrowdControlAbility) a).getEffect().getType() == EffectType.DEBUFF && d instanceof Champion
					&& !checkFriendly(d)) {
				targets.add(d);
			}
		}
		if (a instanceof DamagingAbility) {
			for (int i = 0; i < targets.size(); i++) {
				if (targets.get(i) instanceof Champion) {
					for (Effect eff : ((Champion) targets.get(i)).getAppliedEffects()) {
						if (eff instanceof Shield) {
							((Champion) targets.get(i)).getAppliedEffects().remove(eff);
							eff.remove(((Champion) targets.get(i)));
							targets.remove(i);
							i--;
							break;
						}
					}
				}
			}
		}
		return targets;
	}
  private void clean(ArrayList<Damageable> targets) {
		for (Damageable d : targets) {
			if (d.getCurrentHP() <= 0)
				clear(d);
		}
	}

  private ArrayList<Damageable> getTargetsDirectional(Ability a, Direction d) {
		Point center = getCurrentChampion().getLocation();
		ArrayList<Damageable> targets = new ArrayList<Damageable>();
		for (int i = 1; i <= a.getCastRange(); i++) {
			if (d == Direction.UP && center.x + i < 5 && board[center.x + i][center.y] != null) {
				targets.add((Damageable) board[center.x + i][center.y]);
			} else if (d == Direction.DOWN && center.x - i >= 0 && board[center.x - i][center.y] != null) {
				targets.add((Damageable) board[center.x - i][center.y]);
			} else if (d == Direction.RIGHT && center.y + i < 5 && board[center.x][center.y + i] != null) {
				targets.add((Damageable) board[center.x][center.y + i]);
			} else if (d == Direction.LEFT && center.y - i >= 0 && board[center.x][center.y - i] != null) {
				targets.add((Damageable) board[center.x][center.y - i]);
			}
		}
		return targets;
	}

  public void castAbility(Ability a)
    throws AbilityUseException, NotEnoughResourcesException, CloneNotSupportedException {
      Champion c = getCurrentChampion();
		validateCastAbility(a);
		ArrayList<Damageable> temp = getTargets(a);
		ArrayList<Damageable> targets;
		if (a.getCastArea() == AreaOfEffect.SELFTARGET)
			targets = temp;
		else
			targets = filterTargets(temp, a);

		a.execute(targets);
		c.setCurrentActionPoints(c.getCurrentActionPoints() - a.getRequiredActionPoints());
		c.setMana(c.getMana() - a.getManaCost());
		a.setCurrentCooldown(a.getBaseCooldown());
		clean(targets);
    }

  public void castAbility(Ability a, Direction d)
    throws NotEnoughResourcesException, AbilityUseException, CloneNotSupportedException {
      Champion c = getCurrentChampion();
      validateCastAbility(a);
      ArrayList<Damageable> temp = getTargetsDirectional(a, d);
      ArrayList<Damageable> targets = filterTargets(temp, a);
  
      a.execute(targets);
      c.setCurrentActionPoints(c.getCurrentActionPoints() - a.getRequiredActionPoints());
      c.setMana(c.getMana() - a.getManaCost());
      a.setCurrentCooldown(a.getBaseCooldown());
      clean(targets);
  }

  private ArrayList<Damageable> getTargetsSingle(Ability a, int x, int y)
			throws AbilityUseException, InvalidTargetException {
		if (board[x][y] == null)
			throw new InvalidTargetException("You cannot cast a single target ability on an empty cell.");
		Damageable target = (Damageable) board[x][y];
		if (target instanceof Cover && !(a instanceof DamagingAbility))
			throw new InvalidTargetException("You cannot use this ability on a cover.");
		if (checkFriendly(target) && (a instanceof DamagingAbility || (a instanceof CrowdControlAbility
				&& ((CrowdControlAbility) a).getEffect().getType() == EffectType.DEBUFF)))
			throw new InvalidTargetException("You cannot use this ability on a friendly champion.");
		if (!checkFriendly(target) && (a instanceof HealingAbility || (a instanceof CrowdControlAbility
				&& ((CrowdControlAbility) a).getEffect().getType() == EffectType.BUFF)))
			throw new InvalidTargetException("You cannot use this ability on an enemy champion.");
		if (!checkRange(a.getCastRange(), ((Damageable) board[x][y])))
			throw new AbilityUseException(
					"You can only use single target abilities on targets that are within ability range.");
		ArrayList<Damageable> targets = new ArrayList<>();
		targets.add(target);
		if (a instanceof DamagingAbility && target instanceof Champion) {
			for (Effect eff : ((Champion) target).getAppliedEffects()) {
				if (eff instanceof Shield) {
					eff.remove(((Champion) target));
					targets.remove(target);
					((Champion) target).getAppliedEffects().remove(eff);
					break;
				}
			}
		}
		return targets;
	}

  public void castAbility(Ability a, int x, int y)
    throws AbilityUseException, InvalidTargetException, NotEnoughResourcesException, CloneNotSupportedException {
      Champion c = getCurrentChampion();
      validateCastAbility(a);
      ArrayList<Damageable> targets = getTargetsSingle(a, x, y);
  
      a.execute(targets);
      c.setCurrentActionPoints(c.getCurrentActionPoints() - a.getRequiredActionPoints());
      c.setMana(c.getMana() - a.getManaCost());
      a.setCurrentCooldown(a.getBaseCooldown());
      clean(targets);
  }

  public void useLeaderAbility()
    throws LeaderNotCurrentException, LeaderAbilityAlreadyUsedException {
      Champion c = getCurrentChampion();
		ArrayList<Champion> targets = new ArrayList<>();
		if (firstPlayer.getLeader() == c) {
			if (firstLeaderAbilityUsed)
				throw new LeaderAbilityAlreadyUsedException("You can only use your leader ability once per game.");
			firstLeaderAbilityUsed = true;
			if (c instanceof Hero) {
				targets.addAll(firstPlayer.getTeam());
			} else if (c instanceof Villain) {
				for (Champion champion : secondPlayer.getTeam()) {
					if (champion.getCurrentHP() <= (int) (champion.getMaxHP() * 0.3))
						targets.add(champion);
				}
			} else {
				targets.addAll(firstPlayer.getTeam());
				targets.remove(firstPlayer.getLeader());
				targets.addAll(secondPlayer.getTeam());
				targets.remove(secondPlayer.getLeader());
			}
		} else if (secondPlayer.getLeader() == c) {
			if (secondLeaderAbilityUsed)
				throw new LeaderAbilityAlreadyUsedException();
			secondLeaderAbilityUsed = true;
			if (c instanceof Hero) {
				targets.addAll(secondPlayer.getTeam());
			} else if (c instanceof Villain) {
				for (Champion champion : firstPlayer.getTeam()) {
					if (champion.getCurrentHP() <= (int) (champion.getMaxHP() * 0.3))
						targets.add(champion);
				}
			} else {
				targets.addAll(firstPlayer.getTeam());
				targets.remove(firstPlayer.getLeader());
				targets.addAll(secondPlayer.getTeam());
				targets.remove(secondPlayer.getLeader());
			}
		} else {
			throw new LeaderNotCurrentException("You can only use your leader ability during your leader's turn.");
		}
		c.useLeaderAbility(targets);
  }

  private void updateChampionTimersAndActions() {
		Champion c = getCurrentChampion();
		for (Ability ability : c.getAbilities()) {
			if (ability.getCurrentCooldown() > 0)
				ability.setCurrentCooldown(ability.getCurrentCooldown() - 1);
		}
		for (int i = 0; i < c.getAppliedEffects().size(); i++) {
			Effect e = c.getAppliedEffects().get(i);
			e.setDuration(e.getDuration() - 1);
			if (e.getDuration() == 0) {
				e.remove(c);
				c.getAppliedEffects().remove(i);
				i--;
			}
		}
		c.setCurrentActionPoints(c.getMaxActionPointsPerTurn());
	}

  // Method is called when the current champion decides to end his turn
  public void endTurn() {
    turnOrder.remove();
		if (turnOrder.isEmpty())
			prepareChampionTurns();
		updateChampionTimersAndActions();
		if (getCurrentChampion().getCondition() == Condition.INACTIVE)
			endTurn();
  }

}

