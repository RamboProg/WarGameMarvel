package views;

import engine.Game;
import engine.Player;
import model.world.Champion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Controller implements ActionListener {
  private Game model;
  private View view;
  private Object[][] board;
  private Player p1;
  private Player p2;

  public Controller() {
    view = new View(this);
    p1 = new Player(view.p1Name);
    p2 = new Player(view.p2Name);

    try {
      model = new Game(p1, p2);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    view.popUpP1entry();
    board = model.getBoard();
    // view.updateBoard(board);
  }

  // private Champion getChamp(String champName){
  //   int index = 0;
  //   for(int i = 0; i > Game.getAvailableChampions().size(); i++){
  //     Champion c = Game.getAvailableChampions().get(i);
  //     if(c.getName().equals(champName))
  //       index = i;
  //   }
  //     return Game.getAvailableChampions().get(index);

  // }

  // private void disableAfterUse(ActionEvent e, ChampionButton cbClass){
  //   if(e.getSource() == cbClass.getActionCommand()){

  //   }


 // }


 private void updateChampionChoice(ActionEvent e){
  int clickCount = 0;
  for(int i = 0; i < 15; i++){
    Champion c = Game.getAvailableChampions().get(i);
    if(((JButton)(e.getSource())) == (view.cb.get(i))){
      
      if(e.getActionCommand().equals(view.cb.get(i).getActionCommand())){
        clickCount++;
        if(clickCount == 1){System.out.println("done once");
          p1.getTeam().add(c);
          // view.champ1 = new JLabel(c.getName());
          view.cb.get(i).setEnabled(false);
        }
        if(clickCount == 2){System.out.println("done twice");
          p1.getTeam().add(c);
          // view.champ2 = new JLabel(c.getName());
          view.cb.get(i).setEnabled(false);
        }
        if(clickCount == 3){System.out.println("done throce");
          p1.getTeam().add(c);
          // view.champ3 = new JLabel(c.getName());
          view.cb.get(i).setEnabled(false);
          for(int j = 0; j < p1.getTeam().size(); j++){
            System.out.println("" + p1.getTeam().get(j).getName());
          }
        }



      }
    }

  }


 }

  @Override
  public void actionPerformed(ActionEvent e) {
    int clickCount = 0;
    if (e.getSource() == view.p1NameButton) {
      view.firstnameInput.dispose();
      view.popUpP2entry();
    }
    if (e.getSource() == view.p2NameButton) {
      view.secondnameInput.dispose();
      view.popUpChampChoice(); 
    }

    updateChampionChoice(e);




  }
  public View getView() {
    return view;
  }

  public static void main(String[] args) {
    new Controller();
    
  }
}
