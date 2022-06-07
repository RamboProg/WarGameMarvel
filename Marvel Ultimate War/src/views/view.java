package views;

import engine.Game;
import engine.Player;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.print.attribute.standard.PagesPerMinute;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.Border;
import javax.swing.event.MouseInputListener;
import model.world.Champion;
import model.world.Cover;

public class View extends JFrame {
  protected ActionListener listener;
  protected MouseInputListener mouseListener;
  private JPanel gameBoard;
  private JFrame mainGame;
  protected JFrame firstnameInput;
  protected JFrame secondnameInput;

  //for choosing champions
  private JFrame championChoice;
  private JPanel champChoice;
  private JPanel p1Panel;
  private JPanel p2Panel;

  //for entering players' names
  private JTextField p1TextField;
  protected JButton p1NameButton;
  private JTextField p2TextField;
  protected JButton p2NameButton;

  protected JFrame startScreen;
  protected JButton start;

  

  protected String p1Name;
  protected String p2Name;

  public View(ActionListener l) {
    this.listener = l;
    // popUpP1entry();
    // popUpP2entry();
    // popUpChampChoice();

    gameBoard = new JPanel(new GridLayout(5, 5));
  }

  public void createBoard(Object[][] board) {
    this.setTitle("Marvel War Game");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setExtendedState(JFrame.MAXIMIZED_BOTH); //Maximises screen
    this.setBounds(0, 50, 1280, 720); //when i de-maximise
    this.setVisible(true);
    this.gameBoard.removeAll();
    gameBoard.setBounds(80, 40, 200, 200);

    for (int i = 0; i < Game.getBoardwidth(); i++) {
      for (int j = 0; j < Game.getBoardheight(); j++) {
        if (board[i][j] instanceof Cover) {
          JButton b = new JButton("Cover");
            gameBoard.add(b);
        } else if (board[i][j] instanceof Champion) {
          JButton b = new JButton("Champion");
            gameBoard.add(b);
        } else {
          JButton b = new JButton();
            gameBoard.add(b);
        }
      }
    }
  }

  //Creates a button with the corresponding Champion icon
  private JButton champName(int index) {
    JButton b = new JButton();
    Icon icon;
    for (int i = 0; i < Game.getAvailableChampions().size(); i++) {
      switch (index) {
        case 0:
          b = new JButton("Captain America");
          break;
        case 1:
          b = new JButton("Deadpool");
          break;
        case 2:
          b = new JButton("Dr. Strange");
          break;
        case 3:
          b = new JButton("Electro");
          break;
        case 4:
          b = new JButton("Ghost Rider");
          break;
        case 5:
          b = new JButton("Hela");
          break;
        case 6:
          b = new JButton("Hulk");
          break;
        case 7:
          b = new JButton("Iceman");
          break;
        case 8:
          b = new JButton("Iron Man");
          break;
        case 9:
          b = new JButton("Loki");
          break;
        case 10:
          b = new JButton("Quicksilver");
          break;
        case 11:
          b = new JButton("Spider-Man");
          break;
        case 12:
          b = new JButton("Thor");
          break;
        case 13:
          b = new JButton("Venom");
          break;
        case 14:
          b = new JButton("Yellow Jacket");
          break;
      }
    }
    return b;
  }

  public void popUpP1entry() {
    firstnameInput = new JFrame();
    firstnameInput.setLayout(new FlowLayout());
    Icon p1Image = new ImageIcon("p1.png");
    p1NameButton = new JButton("Submit");
    p1NameButton.addActionListener(this.listener);
    JLabel p1ImageLabel = new JLabel();
    p1ImageLabel.setIcon(p1Image);

    firstnameInput.setDefaultCloseOperation(EXIT_ON_CLOSE);

    p1TextField = new JTextField("Player 1", 15);
    p1TextField.setEditable(true);
    p1TextField.setPreferredSize(new Dimension(250, 30));
    p1TextField.setFont(new Font("Times New Roman", Font.ITALIC, 25));
    p1TextField.setForeground(new Color(32, 183, 226));
    p1TextField.setBackground(new Color(2, 48, 61));
    p1TextField.setCaretColor(new Color(32, 183, 226));
    p1Name = p1TextField.getText();

    firstnameInput.add(p1NameButton);
    firstnameInput.add(p1TextField);
    firstnameInput.pack();
    firstnameInput.setVisible(true);
  }

  public void popUpP2entry() {
    secondnameInput = new JFrame();
    secondnameInput.setLayout(new FlowLayout());
    p2NameButton = new JButton("Submit");
    p2NameButton.addActionListener(this.listener);

    secondnameInput.setDefaultCloseOperation(EXIT_ON_CLOSE);

    p2TextField = new JTextField("Player 2", 15);
    p2TextField.setEditable(true);
    p2TextField.setPreferredSize(new Dimension(250, 30));
    p2TextField.setFont(new Font("Times New Roman", Font.ITALIC, 25));
    p2TextField.setForeground(new Color(184, 9, 62));
    p2TextField.setBackground(new Color(61, 2, 33));
    p2TextField.setCaretColor(new Color(61, 2, 33));
    p2Name = p2TextField.getName();

    secondnameInput.add(p2NameButton);
    secondnameInput.add(p2TextField);
    secondnameInput.pack();
    secondnameInput.setVisible(true);
    //secondnameInput.dispose();

  }

  //The window where both players choose their champions
  public void popUpChampChoice() {
    championChoice = new JFrame();
    championChoice.setLayout(new BorderLayout());

    //Shows the available Champions
    champChoice = new JPanel(new GridLayout(3, 5));
    ImageIcon p1Image = new ImageIcon(
      "Marvel Ultimate War\\src\\mario_jump.gif"
    );
    ImageIcon p2Image = new ImageIcon(
      "Marvel Ultimate War\\src\\mario_car.gif"
    );
    for (int i = 0; i < 16; i++) {
      champChoice.add(champName(i));
    }

    //Shows P1
    p1Panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
    p1Panel.setBackground(new java.awt.Color(22, 90, 247));
    JLabel p1Pic = new JLabel("Player 1", p1Image, JLabel.CENTER);
    p1Panel.add(p1Pic);

    //Shows P2
    p2Panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
    p2Panel.setBackground(new java.awt.Color(247, 22, 82));
    JLabel p2Pic = new JLabel("Player 2", p2Image, JLabel.CENTER);
    p2Panel.add(p2Pic);

    //shows The whole window
    championChoice.add(p1Panel, BorderLayout.NORTH);
    championChoice.add(p2Panel, BorderLayout.NORTH);
    championChoice.add(champChoice, BorderLayout.CENTER);
  }

  public void updateBoard(Object[][] board) {
    //TO-DO Figure out what to put here
    for (int i = 0; i < Game.getBoardwidth(); i++) {
      for (int j = 0; j < Game.getBoardheight(); j++) {
        if ((i > 0 && i < 4) && j == 0) {
          //JButton b = new JButton(i, j);

        }
      }
    }

    //Refresh and tell java that something changed
    this.add(this.gameBoard);
    this.revalidate();
    this.repaint();
  }
  // public static void main(String[] args){
  //   JButton b = new JButton("Rambo");
  //   System.out.println(b.getActionCommand());
  // }
}
