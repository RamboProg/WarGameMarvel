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
import java.awt.Label;
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

  protected JButton b;
  protected ChampionButton cbClass;
  protected ArrayList<ChampionButton> cb = new ArrayList<ChampionButton>();
  protected JButton saveButton;

  //for choosing champions
  protected JFrame championChoice;
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

  protected ChampionButton cb_0;
  protected ChampionButton cb_1;
  protected ChampionButton cb_2;
  protected ChampionButton cb_3;
  protected ChampionButton cb_4;
  protected ChampionButton cb_5;
  protected ChampionButton cb_6;
  protected ChampionButton cb_7;
  protected ChampionButton cb_8;
  protected ChampionButton cb_9;
  protected ChampionButton cb_10;
  protected ChampionButton cb_11;
  protected ChampionButton cb_12;
  protected ChampionButton cb_13;
  protected ChampionButton cb_14;

  protected JLabel champ1;
  protected JLabel champ2;
  protected JLabel champ3;

  protected JFrame leaderChoice;
  protected JPanel p1LeaderChoice;
  protected JPanel p2LeaderChoice;
  protected ChampionButton cbLeader1;
  protected ChampionButton cbLeader2;
  protected ChampionButton cbLeader3;
  protected ChampionButton cbLeader4;
  protected ChampionButton cbLeader5;
  protected ChampionButton cbLeader6;
  protected ArrayList<ChampionButton> cbLeaders;
  protected JButton saveLeaderButton;

  protected JButton attack;
  protected JButton up;
  protected JButton down;
  protected JButton left;
  protected JButton right;
  protected JButton castAb1;
  protected JButton castAb2;
  protected JButton castAb3;
  protected JPanel north;
  protected JPanel west;
  protected JPanel east;
  protected JPanel south;
  protected ChampionButton p1Champ1;
  protected ChampionButton p1Champ2;
  protected ChampionButton p1Champ3;
  protected ChampionButton p2Champ1;
  protected ChampionButton p2Champ2;
  protected ChampionButton p2Champ3;

  public View(ActionListener l) {
    this.listener = l;

    gameBoard = new JPanel(new GridLayout(5, 5));
  }

  public void createBoard(Object[][] board) {
    this.setLayout(new BorderLayout());
    this.setTitle("Marvel War Game");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setExtendedState(JFrame.MAXIMIZED_BOTH); //Maximises screen
    this.setBounds(0, 50, 1280, 720); //when i de-maximise
    this.setVisible(true);
    up = new JButton("Up");
    down = new JButton("Down");
    left = new JButton("left");
    right =  new JButton("right");
    up.addActionListener(listener);
    down.addActionListener(listener);
    left.addActionListener(listener);
    right.addActionListener(listener);

    attack = new JButton("Attack");
    attack.setActionCommand("Attack");
    attack.addActionListener(listener);

    castAb1 = new JButton("Ability 1");
    castAb1.setActionCommand("Ability 1");
    castAb1.addActionListener(listener);
    castAb2 = new JButton("Ability 2");
    castAb2.setActionCommand("Ability 2");
    castAb2.addActionListener(listener);
    castAb3 = new JButton("Ability 3");
    castAb3.setActionCommand("Ability 3");
    castAb3.addActionListener(listener);

    north = new JPanel(new GridLayout());
    north.add(up);
    north.add(down);
    north.add(left);
    north.add(right);
    north.add(attack);

    west = new JPanel(new FlowLayout());
    east = new JPanel(new FlowLayout());

    south = new JPanel(new GridLayout(1,3));
    south.add(castAb1);
    south.add(castAb2);
    south.add(castAb3);






    
    
    
    Icon iconCover = new ImageIcon("Marvel Ultimate War\\src\\Cover.png");
    
    for (int i = 0; i < Game.getBoardwidth(); i++) {
      for (int j = 0; j < Game.getBoardheight(); j++) {
        if (board[i][j] instanceof Cover) {
          b = new JButton("Cover", iconCover);
          gameBoard.add(b);
        } if(board[i][j] == null){
          b = new JButton();
          gameBoard.add(b);
        } if(board[i][j] instanceof Champion){
          b = new JButton(((Champion)board[i][j]).getName());
          gameBoard.add(b);
        }
      }
    }
    // p1Champ1 = new ChampionButton(((Champion)board[1][0]).getName());
    // p1Champ1.setChampButton(Controller.model.getFirstPlayer().getTeam().get(0));
    // p1Champ2 = new ChampionButton(((Champion)board[2][0]).getName());
    // p1Champ1.setChampButton(Controller.model.getFirstPlayer().getTeam().get(1));
    // p1Champ3 = new ChampionButton(((Champion)board[3][0]).getName());
    // p1Champ1.setChampButton(Controller.model.getFirstPlayer().getTeam().get(2));
    // p2Champ1 = new ChampionButton(((Champion)board[1][4]).getName());
    // p1Champ1.setChampButton(Controller.model.getSecondPlayer().getTeam().get(0));
    // p2Champ2 = new ChampionButton(((Champion)board[2][4]).getName());
    // p1Champ1.setChampButton(Controller.model.getSecondPlayer().getTeam().get(1));
    // p2Champ3 = new ChampionButton(((Champion)board[3][4]).getName());
    // p1Champ1.setChampButton(Controller.model.getSecondPlayer().getTeam().get(2));
    // gameBoard.add(p1Champ1);
    // gameBoard.add(p1Champ2);
    // gameBoard.add(p1Champ3);
    // gameBoard.add(p2Champ1);
    // gameBoard.add(p2Champ2);
    // gameBoard.add(p2Champ3);
    east.add(p2Panel);
    west.add(p1Panel);
    for(int i = 0; i< controller.model.getFirstPlayer().getTeam().size();i++){
      JLabel l = new JLabel(controller.model.getFirstPlayer().getTeam().get(i).getName()); 
      west.add(l);
    }
    for(int i = 0; i< controller.model.getSecondPlayer().getTeam().size();i++){
      JLabel l = new JLabel(controller.model.getSecondPlayer().getTeam().get(i).getName());
      east.add(l);
    }
    



    this.add(north, BorderLayout.NORTH);
    this.add(south, BorderLayout.SOUTH);
    this.add(east, BorderLayout.EAST);
    this.add(west, BorderLayout.WEST);
    this.add(this.gameBoard, BorderLayout.CENTER);
    this.revalidate();
    this.repaint();
  }
  // private ChampionButton mainBoardChamp(){
  //   champChoiceGrid();
  //   for()


  // }

  private void initializeButton(ChampionButton cb, Champion c) {
    cb.addActionListener(this.listener);
    cb.setToolTipText(
      "Name: " +
      c.getName() +
      '\n' +
      ", Max HP: " +
      c.getMaxHP() +
      '\n' +
      ", Attack Damage: " +
      c.getAttackDamage() +
      '\n' +
      ", Attack Range: " +
      c.getAttackRange() +
      '\n' +
      ", Action Points Per Turn: " +
      c.getMaxActionPointsPerTurn() +
      '\n' +
      ", Mana: " +
      c.getMana() +
      '\n' +
      ", Speeeeed" +
      c.getSpeed() +
      '\n' +
      ",  Abilities: " +
      c.getAbilities().toString()
    );
  }

  private void champChoiceGrid() {
    cb_0 = new ChampionButton("Captain America");
    cb_0.setChampButton(Game.getAvailableChampions().get(0));
    cb_0.setText("Captain America");
    cb_0.setActionCommand("Captain America");
    initializeButton(cb_0, Game.getAvailableChampions().get(0));
    cb.add(cb_0);
    cb_1 = new ChampionButton("Deadpool");
    cb_1.setChampButton(Game.getAvailableChampions().get(1));
    cb_1.setText("Deadpool");
    cb_1.setActionCommand("Deadpool");
    initializeButton(cb_1, Game.getAvailableChampions().get(1));
    cb.add(cb_1);
    cb_2 = new ChampionButton("Dr. Strange");
    cb_2.setChampButton(Game.getAvailableChampions().get(2));
    cb_2.setText("Dr. Strange");
    cb_2.setActionCommand("Dr. Strange");
    initializeButton(cb_2, Game.getAvailableChampions().get(2));
    cb.add(cb_2);
    cb_3 = new ChampionButton("Electro");
    cb_3.setChampButton(Game.getAvailableChampions().get(3));
    cb_3.setText("Electro");
    cb_3.setActionCommand("Electro");
    initializeButton(cb_3, Game.getAvailableChampions().get(3));
    cb.add(cb_3);
    cb_4 = new ChampionButton("Ghost Rider");
    cb_4.setChampButton(Game.getAvailableChampions().get(4));
    cb_4.setText("Ghost Rider");
    cb_4.setActionCommand("Ghost Rider");
    initializeButton(cb_4, Game.getAvailableChampions().get(4));
    cb.add(cb_4);
    cb_5 = new ChampionButton("Hela");
    cb_5.setChampButton(Game.getAvailableChampions().get(5));
    cb_5.setText("Hela");
    cb_5.setActionCommand("Hela");
    initializeButton(cb_5, Game.getAvailableChampions().get(5));
    cb.add(cb_5);
    cb_6 = new ChampionButton("Hulk");
    cb_6.setChampButton(Game.getAvailableChampions().get(6));
    cb_6.setText("Hulk");
    cb_6.setActionCommand("Hulk");
    initializeButton(cb_6, Game.getAvailableChampions().get(6));
    cb.add(cb_6);
    cb_7 = new ChampionButton("Iceman");
    cb_7.setChampButton(Game.getAvailableChampions().get(7));
    cb_7.setText("Iceman");
    cb_7.setActionCommand("Iceman");
    initializeButton(cb_7, Game.getAvailableChampions().get(7));
    cb.add(cb_7);
    cb_8 = new ChampionButton("Iron Man");
    cb_8.setChampButton(Game.getAvailableChampions().get(8));
    cb_8.setText("Iron Man");
    cb_8.setActionCommand("Iron Man");
    initializeButton(cb_8, Game.getAvailableChampions().get(8));
    cb.add(cb_8);
    cb_9 = new ChampionButton("Loki");
    cb_9.setChampButton(Game.getAvailableChampions().get(9));
    cb_9.setText("Loki");
    cb_9.setActionCommand("Loki");
    initializeButton(cb_9, Game.getAvailableChampions().get(9));
    cb.add(cb_9);
    cb_10 = new ChampionButton("Quicksilver");
    cb_10.setChampButton(Game.getAvailableChampions().get(10));
    cb_10.setText("Quicksilver");
    cb_10.setActionCommand("Quicksilver");
    initializeButton(cb_10, Game.getAvailableChampions().get(10));
    cb.add(cb_10);
    cb_11 = new ChampionButton("Spider-Man");
    cb_11.setChampButton(Game.getAvailableChampions().get(11));
    cb_11.setText("Spider-Man");
    cb_11.setActionCommand("Spider-Man");
    initializeButton(cb_11, Game.getAvailableChampions().get(11));
    cb.add(cb_11);
    cb_12 = new ChampionButton("Thor");
    cb_12.setChampButton(Game.getAvailableChampions().get(12));
    cb_12.setText("Thor");
    cb_12.setActionCommand("Thor");
    initializeButton(cb_12, Game.getAvailableChampions().get(12));
    cb.add(cb_12);
    cb_13 = new ChampionButton("Venom");
    cb_13.setChampButton(Game.getAvailableChampions().get(13));
    cb_13.setText("Venom");
    cb_13.setActionCommand("Venom");
    initializeButton(cb_13, Game.getAvailableChampions().get(13));
    cb.add(cb_13);
    cb_14 = new ChampionButton("Yellow Jacket");
    cb_14.setChampButton(Game.getAvailableChampions().get(14));
    cb_14.setText("Yellow Jacket");
    cb_14.setActionCommand("Yellow Jacket");
    initializeButton(cb_14, Game.getAvailableChampions().get(14));
    cb.add(cb_14);
  }

  public void popUpP1entry() {
    firstnameInput = new JFrame();
    firstnameInput.setLayout(new FlowLayout());
    p1NameButton = new JButton("Enter name for Player 1");
    p1NameButton.addActionListener(this.listener);

    firstnameInput.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

    p1TextField = new JTextField(15);
    p1Name = p1TextField.getText();
    p1TextField.setEditable(true);
    p1TextField.setPreferredSize(new Dimension(250, 30));
    p1TextField.setFont(new Font("Times New Roman", Font.ITALIC, 25));
    p1TextField.setForeground(new Color(32, 183, 226));
    p1TextField.setBackground(new Color(2, 48, 61));
    p1TextField.setCaretColor(new Color(32, 183, 226));

    firstnameInput.add(p1NameButton);
    firstnameInput.add(p1TextField);
    firstnameInput.pack();
    firstnameInput.setLocationRelativeTo(null);
    firstnameInput.setVisible(true);
  }

  public void popUpP2entry() {
    secondnameInput = new JFrame();
    secondnameInput.setLayout(new FlowLayout());
    p2NameButton = new JButton("Enter name for Player 2");
    p2NameButton.addActionListener(this.listener);

    secondnameInput.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

    p2TextField = new JTextField(15);
    p2Name = p2TextField.getName();
    p2TextField.setEditable(true);
    p2TextField.setPreferredSize(new Dimension(250, 30));
    p2TextField.setFont(new Font("Times New Roman", Font.ITALIC, 25));
    p2TextField.setForeground(new Color(184, 9, 62));
    p2TextField.setBackground(new Color(61, 2, 33));
    p2TextField.setCaretColor(new Color(61, 2, 33));

    secondnameInput.add(p2NameButton);
    secondnameInput.add(p2TextField);
    secondnameInput.pack();
    secondnameInput.setLocationRelativeTo(null);
    secondnameInput.setVisible(true);
  }

  public void popUpChampChoice() {
    championChoice = new JFrame();
    championChoice.setLayout(new BorderLayout());

    //Shows the available Champions
    champChoice = new JPanel(new GridLayout(3, 5));
    // champ1 = new JLabel("" + Controller.model.getFirstPlayer().getTeam().get(0));
    // p1Panel.add(champ1);
    // champ2 = new JLabel("" + Controller.model.getFirstPlayer().getTeam().get(1));
    // p1Panel.add(champ2);
    // champ3 = new JLabel("" + Controller.model.getFirstPlayer().getTeam().get(2));
    // p1Panel.add(champ3);
    ImageIcon p1Image = new ImageIcon(
      "Marvel Ultimate War\\src\\mario_jump.gif"
    );
    ImageIcon p2Image = new ImageIcon(
      "Marvel Ultimate War\\src\\mario_car.gif"
    );
    champChoiceGrid();
    for (int i = 0; i < 15; i++) {
      champChoice.add(cb.get(i));
    }

    //Shows P1
    p1Panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
    p1Panel.setBackground(new java.awt.Color(22, 90, 247));
    JLabel p1Pic = new JLabel(p1Name, p1Image, JLabel.CENTER);
    p1Panel.add(p1Pic);

    //Shows P2
    p2Panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
    p2Panel.setBackground(new java.awt.Color(247, 22, 82));
    JLabel p2Pic = new JLabel(p2Name, p2Image, JLabel.CENTER);
    p2Panel.add(p2Pic);

    saveButton = new JButton("Save");
    saveButton.addActionListener(this.listener);
    saveButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
    saveButton.setForeground(new Color(38, 214, 26));
    saveButton.setBackground(new Color(2, 69, 42));

    //shows The whole window
    //p1Panel.add(champ2);
    // p1Panel.add(champ3);
    championChoice.add(p1Panel, BorderLayout.PAGE_START);
    championChoice.add(p2Panel, BorderLayout.PAGE_END);
    championChoice.add(champChoice, BorderLayout.CENTER);
    championChoice.add(saveButton, BorderLayout.LINE_END);
    championChoice.setVisible(true);
    championChoice.setExtendedState(JFrame.MAXIMIZED_BOTH);
    championChoice.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public void chooseLeader(){
    leaderChoice = new JFrame();
    p1LeaderChoice = new JPanel(new GridLayout(1, 3));
    p2LeaderChoice = new JPanel(new GridLayout(1,3));
    saveLeaderButton = new JButton("Save");
    saveLeaderButton.addActionListener(listener);
    saveLeaderButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
    saveLeaderButton.setBackground(new Color(2, 69, 42));
    saveLeaderButton.setForeground(new Color(38, 214, 26));

    cbLeader1.addActionListener(listener);
    cbLeader2.addActionListener(listener);
    cbLeader3.addActionListener(listener);
    cbLeader4.addActionListener(listener);
    cbLeader5.addActionListener(listener);
    cbLeader6.addActionListener(listener);

    cbLeaders.add(cbLeader1);
    cbLeaders.add(cbLeader2);
    cbLeaders.add(cbLeader3);
    cbLeaders.add(cbLeader4);
    cbLeaders.add(cbLeader5);
    cbLeaders.add(cbLeader6);

    p1LeaderChoice.add(cbLeader1);
    p1LeaderChoice.add(cbLeader2);
    p1LeaderChoice.add(cbLeader3);
    p2LeaderChoice.add(cbLeader4);
    p2LeaderChoice.add(cbLeader5);
    p2LeaderChoice.add(cbLeader6);

    leaderChoice.add(p1LeaderChoice);
    leaderChoice.add(p2LeaderChoice);
    leaderChoice.setVisible(true);
    leaderChoice.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

    


  }

  public void updateBoard(Object[][] board) {
    this.gameBoard.removeAll();
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
