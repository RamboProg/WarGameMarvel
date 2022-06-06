package views;

import engine.Game;
import engine.Player;
import model.world.Champion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.border.Border;
import javax.swing.event.MouseInputListener;
import java.awt.Font;
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

  //for entering players' names
  private JTextField p1TextField;
  private JButton p1NameButton;
  private JTextField p2TextField;
  private JButton p2NameButton;

  protected JFrame startScreen;
  protected JButton start;
  
  private JPanel gameBoard;



  protected String p1Name;
  protected String p2Name;


  public View(ActionListener l) {
    ArrayList a = Game.getAvailableChampions();
    this.listener = l;
    // popUpP1entry();
    // popUpP2entry();
    // popUpChampChoice();
    //createBoard(Game.getBoard());
    // startScreen();

    /*
    setTitle("Marvel War Game");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setExtendedState(JFrame.MAXIMIZED_BOTH); //Maximises screen
    setBounds(0, 50, 1280, 720); //when i de-maximise
    setVisible(true);
    
    */
    
    gameBoard = new JPanel(new GridLayout(5,5));
  }

  public void startScreen(){
    startScreen = new JFrame();
    startScreen.setDefaultCloseOperation(EXIT_ON_CLOSE);
    startScreen.setLayout(new BorderLayout());
    
    start = new JButton("Start");
    start.addActionListener(this.listener);
    start.setContentAreaFilled(false);
    start.setBackground(new Color(179, 89, 0));
    startScreen.add(start, BorderLayout.CENTER);
    JLabel bgPic = new JLabel(new ImageIcon("Marvel Ultimate War\\src\\startScreen2.jpg"));
    startScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
    startScreen.setBounds(0, 50, 1280, 720);
    startScreen.setVisible(true);
    startScreen.add(bgPic);
    
    

    

  }

  public void createBoard(Object[][] board) {
    boardPanel = new JPanel(new GridLayout(Game.getBoardheight(), Game.getBoardwidth()));
    boardPanel.setBounds(80, 40, 200, 200);
    for (int i = 0; i < Game.getBoardwidth(); i++) {
      for (int j = 0; j < Game.getBoardheight(); j++) {
         JButton b = new JButton();

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
          b = new JButton(icon);
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
         champName(j+i);//fix
      }
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

    //Panel in the middle that shows the champion's info when hovered over
    infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel infoLabel = new JLabel();
        
  }

  //Prompts the users to enter their names
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
    //p1TextField.addActionListener(this.listener);
    //p1TextField.add(p1ImageLabel);
    p1TextField.setPreferredSize(new Dimension(250, 30));
    p1TextField.setFont(new Font("Times New Roman", Font.ITALIC, 25));    
    p1TextField.setForeground(new Color(32, 183, 226));
    p1TextField.setBackground(new Color(2, 48, 61));
    p1TextField.setCaretColor(new Color(32, 183, 226));
    
    firstnameInput.add(p1NameButton);
    firstnameInput.add(p1TextField);
    firstnameInput.pack();
    firstnameInput.setVisible(true);
    //firstnameInput.dispose();

  }
  
  public void popUpP2entry(){
    secondnameInput = new JFrame();
    secondnameInput.setLayout(new FlowLayout());
    // Icon p2Image = new ImageIcon("p2.png");
    p2NameButton = new JButton("Submit");
    p2NameButton.addActionListener(this.listener);
  
    secondnameInput.setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    p2TextField = new JTextField("Player 2", 15);
    p2TextField.setEditable(true);
    // p2TextField.addActionListener(this.listener);
    p2TextField.setPreferredSize(new Dimension(250, 30));
    p2TextField.setFont(new Font("Times New Roman", Font.ITALIC, 25));
    p2TextField.setForeground(new Color(184, 9, 62));
    p2TextField.setBackground(new Color(61, 2, 33));
    p2TextField.setCaretColor(new Color(61, 2, 33));
  
    secondnameInput.add(p2NameButton);
    secondnameInput.add(p2TextField);
    secondnameInput.pack();
    secondnameInput.setVisible(true);
    //secondnameInput.dispose();

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
