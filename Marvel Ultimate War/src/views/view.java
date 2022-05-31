package views;

import engine.Game;
import engine.Player;
import model.world.Champion;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
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
import javax.swing.event.MouseInputListener;
public class View extends JFrame {
  protected ActionListener listener;
  protected MouseInputListener mouseListener;
  private JPanel boardPanel;
  private JFrame mainGame;
  private JFrame firstnameInput;
  private JFrame secondnameInput;

  //for choosing champions
  private JFrame championChoice;
  private JPanel champChoice;
  private JPanel p1Panel;
  private JPanel p2Panel;
  private JPanel infoPanel;

  private JPanel gameBoard;

  protected String p1Name;
  protected String p2Name;


  public View(ActionListener l) {
    ArrayList a = Game.getAvailableChampions();
    this.listener = l;
    popUpUserEntry();
    popUpChampChoice();
    //createBoard(Game.getBoard());
    setTitle("Marvel War Game");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setExtendedState(JFrame.MAXIMIZED_BOTH); //Maximises screen
    setBounds(0, 50, 1280, 720); //when i de-maximise
    setVisible(true);

    
    gameBoard = new JPanel(new GridLayout(5,5));
  }



  public void createBoard(Object[][] board) {
    boardPanel = new JPanel(new GridLayout(Game.getBoardheight(), Game.getBoardwidth()));
    boardPanel.setBounds(80, 40, 200, 200);
    for (int i = 0; i < Game.getBoardwidth(); i++) {
      for (int j = 0; j < Game.getBoardheight(); j++) {
         

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
          icon = new ImageIcon("captain_america.png");
          b = new JButton("Captain America", icon);
          break;
        case 1:
          icon = new ImageIcon("deadpool.png");
          b = new JButton(icon);
          break;
        case 2:
          icon = new ImageIcon("dr_strange.png");
          b = new JButton(icon);
          break;
        case 3:
          icon = new ImageIcon("electro.png");
          b = new JButton(icon);
          break;
        case 4:
          icon = new ImageIcon("ghost_rider.png");
          b = new JButton(icon);
          break;
        case 5:
          icon = new ImageIcon("hela.png");
          b = new JButton(icon);
          break;
        case 6:
          icon = new ImageIcon("hulk.png");
          b = new JButton(icon);
          break;
        case 7:
          icon = new ImageIcon("iceman.png");
          b = new JButton(icon);
          break;
        case 8:
          icon = new ImageIcon("iron_man.png");
          b = new JButton(icon);
          break;
        case 9:
          icon = new ImageIcon("loki.png");
          b = new JButton(icon);
          break;
        case 10:
          icon = new ImageIcon("quicksilver.png");
          b = new JButton(icon);
          break;
        case 11:
          icon = new ImageIcon("spider_man.png");
          b = new JButton(icon);
          break;
        case 12:
          icon = new ImageIcon("thor.png");
          b = new JButton(icon);
          break;
        case 13:
          icon = new ImageIcon("venom.png");
          b = new JButton(icon);
          break;
        case 14:
          icon = new ImageIcon("yellow_jacket.png");
          b = new JButton(icon);
          break;
      }
    }
    return b;
  }

  //The window where both players choose their champions
  public void popUpChampChoice() {
    championChoice = new JFrame();
    championChoice.setLayout(new BorderLayout());
    //championChoice.getContentPane().setBackground(new java.awt.Color(204, 166, 166));  
    //Shows the available Champions
    champChoice = new JPanel(new GridLayout(3, 5));
    ImageIcon p1Image = new ImageIcon("p1.png");
    ImageIcon p2Image = new ImageIcon("p2.png");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 5; j++) {
         champName(j+i);
      }
    }

    

    //Shows P1
    p1Panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
    p1Panel.setBackground(new java.awt.Color(22, 90, 247));
    JLabel p1Pic = new JLabel("Player 1", p2Image, JLabel.CENTER);
    p1Panel.add(p1Pic);

    //Shows P2
    p2Panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
    p2Panel.setBackground(new java.awt.Color(247, 22, 82));
    JLabel p2Pic = new JLabel("Player 2", p1Image, JLabel.CENTER);
    p2Panel.add(p2Pic);

    //Panel in the middle that shows the champion's info when hovered over
    infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel infoLabel = new JLabel();
        
  }

  //Prompts the users to enter their names
  public void popUpUserEntry() {
    firstnameInput = new JFrame();
    String firstName = JOptionPane.showInputDialog(firstnameInput, "Enter 1st Player's name: ");
    p1Name = (firstName);

    secondnameInput = new JFrame();
    String secondName = JOptionPane.showInputDialog(secondnameInput, "Enter 2nd Player's name: ");
    p2Name = (secondName);
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
    this.add(this.boardPanel);
    this.revalidate();
    this.repaint();
  }

  // public static void main(String[] args){
  //   JButton b = new JButton("Rambo");
  //   System.out.println(b.getActionCommand());
  // }
}
