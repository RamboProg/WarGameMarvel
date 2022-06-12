package views;

import engine.*;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import model.world.Champion;
import model.world.Direction;


public class controller implements ActionListener {
  protected static Game model;
  private View view;
  private Object[][] board;
  private Player p1;
  private Player p2;
  private int clickCount = 0;

  private boolean flag1 = false;
  private boolean flag2 = false;

  public controller() throws IOException {
    view = new View(this);
    p1 = new Player(view.p1Name);
    p2 = new Player(view.p2Name);

    model = new Game(p1, p2);
    view.popUpP1entry();
    board = model.getBoard();
  }

  private void updateChampionChoice(ActionEvent e) {
    for (int i = 0; i < 15; i++) {
      Champion c = Game.getAvailableChampions().get(i);
      if (((JButton) (e.getSource())) == (view.cb.get(i))) {
        if (e.getActionCommand().equals(view.cb.get(i).getActionCommand())) {
          clickCount++;
          if (clickCount == 1) {
            p1.getTeam().add(c);
            p1.setLeader(c);
            // view.champ1 = new JLabel(c.getName());
            view.cb.get(i).setEnabled(false);
          }
          if (clickCount == 2) {
            p1.getTeam().add(c);
            // view.champ2 = new JLabel(c.getName());
            view.cb.get(i).setEnabled(false);
          }
          if (clickCount == 3) {
            p1.getTeam().add(c);
            // view.champ3 = new JLabel(c.getName());
            view.cb.get(i).setEnabled(false);
          }
          if (clickCount == 4) {
            p2.getTeam().add(c);
            p2.setLeader(c);
            view.cb.get(i).setEnabled(false);
          }
          if (clickCount == 5) {
            p2.getTeam().add(c);
            view.cb.get(i).setEnabled(false);
          }
          if (clickCount == 6) {
            p2.getTeam().add(c);
            for (int j = 0; j < 15; j++) {
              view.cb.get(j).setEnabled(false);
            }
          }
        }
      }
    }

    if (e.getSource() == view.saveButton) {
      view.championChoice.dispose();
    }
  }

  private void attackDir(ActionEvent e){

  }

  
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == view.p1NameButton) {
      view.firstnameInput.dispose();
      view.popUpP2entry();
    }
    if (e.getSource() == view.p2NameButton) {
      view.secondnameInput.dispose();
      view.popUpChampChoice();
    }
    updateChampionChoice(e);

    if(e.getSource() == view.saveLeaderButton){
      view.leaderChoice.dispose();
      try {
        model = new Game(p1, p2);
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    }
    


  

    if (e.getSource() == view.saveButton) {
      view.championChoice.dispose();
      view.createBoard(board);
    }

    if(e.getActionCommand().equals(view.up.getActionCommand())){
      try {
        model.move(Direction.UP);
      } catch (UnallowedMovementException | NotEnoughResourcesException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    }

    if(e.getActionCommand().equals(view.left.getActionCommand())){
      try {
        model.move(Direction.LEFT);
      } catch (UnallowedMovementException | NotEnoughResourcesException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    }

    if(e.getActionCommand().equals(view.down.getActionCommand())){
      try {
        model.move(Direction.DOWN);
      } catch (UnallowedMovementException | NotEnoughResourcesException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    }

    if(e.getActionCommand().equals(view.right.getActionCommand())){
      try {
        model.move(Direction.RIGHT);
      } catch (UnallowedMovementException | NotEnoughResourcesException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    }

    if(e.getActionCommand().equals(view.attack.getActionCommand())){
      

    }
    

  }

  public View getView() {
    return view;
  }

  public static void main(String[] args) throws IOException {
    new controller();
  }
}
