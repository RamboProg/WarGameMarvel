package views;

import engine.Game;
import engine.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;



public class Controller implements ActionListener {
  private Game model;
  private View view;

  public Controller() {
    view = new View(this);  
    view.popUpUserEntry();
    Player p1 = new Player(view.p1Name);
    Player p2 = new Player(view.p2Name);
    try {
      model = new Game(p1, p2);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    
    Object[][] board = model.getBoard();
    view.updateBoard(board);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    view.popUpChampChoice();
    
  }

  public View getView(){
    return view;
  }

  public static void main(String[] args) {
      new Controller();
  
  }
}
