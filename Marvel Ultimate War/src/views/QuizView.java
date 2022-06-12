package views;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.abilities.Ability;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import java.awt.GridLayout;
import java.awt.FlowLayout;



public class QuizView extends JFrame {
  private JPanel abilityWindow;
  protected JButton abilityName;
  protected JButton abilityType;
  protected JButton counter;
  protected static JButton next;
  private Ability ability;
  private String abilityTypeName;
  private int counterNumber;
  private ActionListener l;

  public QuizView(ActionListener l) {
    this.l = l;
    this.setTitle("Quiz");
    this.setVisible(true);
    this.setLayout(new GridLayout(2, 2));
    this.setBounds(0, 50, 1280, 720);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    
  }

  public Ability getRandomAbility(ArrayList<Ability> aList) {
    double rndDouble = Math.random() * aList.size();
    int rnd = (int) rndDouble;

    for (int i = 0; i < aList.size(); i++) {
      if (i == rnd) {
        ability = aList.get(i);
        counterNumber = i;

        if (ability instanceof DamagingAbility) abilityTypeName =
          "Damaging Ability";

        if (ability instanceof HealingAbility) abilityTypeName =
          "HealingAbility";
        
        if (ability instanceof CrowdControlAbility) abilityTypeName =
          "CrowdControlAbility";
      }
    }
    return ability;
  }

  public void createWindow() {
    abilityWindow = new JPanel();
    abilityWindow.setLayout(new GridLayout(2, 2));
    
    getRandomAbility(QuizController.model.getAvailableAbilities());
    abilityName = new JButton(ability.getName());
    abilityType = new JButton(abilityTypeName);
    counter = new JButton("" + counterNumber);
    next = new JButton("Next");
    next.setActionCommand("next");

    next.addActionListener(l);

    abilityWindow.add(abilityName);
    abilityWindow.add(abilityType);
    abilityWindow.add(counter);
    abilityWindow.add(next);

    
    this.add(abilityWindow);
    this.revalidate();
    this.repaint();
  }
  
  public void updateWindow(){
    this.abilityWindow.removeAll();      
    getRandomAbility(QuizController.model.getAvailableAbilities());
    abilityName = new JButton(ability.getName());
    abilityType = new JButton(abilityTypeName);
    counter = new JButton("" + counterNumber);
    next = new JButton("Next");
    next.setActionCommand("next");
    
    next.addActionListener(l);

    abilityWindow.add(abilityName);
    abilityWindow.add(abilityType);
    abilityWindow.add(counter);
    abilityWindow.add(next);

    this.add(abilityWindow);
    this.revalidate();
    this.repaint();
}
}
