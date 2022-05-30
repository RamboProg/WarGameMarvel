package views;

import engine.Game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;

public class Controller implements ActionListener {
  private Game model;
  private View view;

  public Controller() {
    // TODO Auto-generated catch block
    this.model = new Game(view.p1, view.p2);

    //Passing the controller as a parameter (Passing itself)
    this.view = new View(this);
    Object[][] board = model.getBoard();
    view.updateBoard(board);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    view.popUpUserEntry();
    view.popUpChampChoice();
  }

  public static void main(String[] args) {
    new Controller();
  }
}
