package views;

import engine.Game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;

public class Controller implements ActionListener {
  private Game model;
  private View view;

  public Controller() throws IOException {
    view.popUpUserEntry();
    model = new Game(view.getP1(), view.getP2());
    view = new View(view.listener, view.mouseListener);

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
    try {
      new Controller();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
