package model.world;

import java.util.ArrayList;
import model.effects.*;

public class AntiHero extends Champion {

  public AntiHero(
    String name,
    int maxHP,
    int mana,
    int maxActions,
    int speed,
    int attackRange,
    int attackDamage
  ) {
    super(name, maxHP, mana, maxActions, speed, attackRange, attackDamage);
    setCurrentHP(maxHP);
    setCondition(Condition.ACTIVE);
  }

  public void useLeaderAbility(ArrayList<Champion> targets) {
    for (Champion champion : targets) {
      Stun s = new Stun(2);
      champion.getAppliedEffects().add(s);
      s.apply(champion);
    }
  }
}
