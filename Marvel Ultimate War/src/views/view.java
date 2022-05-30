package views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.print.attribute.standard.PagesPerMinute;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;

import engine.Game;
import engine.Player;

public class View extends JFrame {
    private ActionListener listener;
	private JPanel boardPanel;
    
    //for choosing champions
    private JFrame championChoice;
    private JPanel champChoice;
    private JPanel p1Panel;
    private JPanel p2Panel;
    private JPanel infoPanel;

    private JPanel firstNamePanel;
    private JPanel secondNamePanel;

    Player p1;
    Player p2;
    
    public View(ActionListener l){
        this.listener = l;

        setTitle("Marvel War Game");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); //Maximises screen
		setBounds(0, 50, 1280, 720);//when i de-maximise
		setVisible(true);

        boardPanel = new JPanel(new GridLayout(Game.getBoardheight(), Game.getBoardwidth()));
        boardPanel.setBounds(80, 40, 200, 200);
    }

    public void creatBoard(Object[][] board){
        for(int i = 0; i < Game.getBoardwidth(); i++){
            for(int j = 0; j < Game.getBoardheight(); j++){
                //add buttons to the game board

            }
        }
    }

    //The window where both players choose their champions
    public void popUpChampChoice(){
        //Shows the available Champions
        champChoice = new JPanel(new GridLayout(3, 5));
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 5; j++){
                JButton b = new JButton();

            }
        }
        
        //Shows P1
        p1Panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        
        //Shows P2 
        p2Panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        //Panel in the middle that shows the champion's info when hovered over
        infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));




    }

    //Prompts the users to enter their names
    public void popUpUserEntry() {
        JTextField firstName = new JTextField(20);
        JLabel label1 = new JLabel("Enter 1st Player's name: ");
        firstNamePanel.add(label1, firstName);
        firstNamePanel.setBounds(10, 10, 80, 25);
        p1 = new Player(firstName.getText());

        JTextField secondName = new JTextField(20);
        JLabel label2 = new JLabel("Enter the 2nd Player's name: ");
        secondNamePanel.add(label2, secondName);
        secondNamePanel.setBounds(10, 10, 80, 25);
        p2 = new Player(secondName.getText());
	}

    public void updateBoard(Object[][] board) {
        //TO-DO Figure out what to put here
        for(int i = 0; i < Game.getBoardwidth(); i++){
            for(int j = 0; j < Game.getBoardheight(); j++){
                if((i > 0 && i < 4) && j == 0){
                    //JButton b = new JButton(i, j);
                    
                }

            }
        }
        
        //Refresh and tell java that something changed
		this.add(this.boardPanel);
		this.revalidate();
		this.repaint();
    }

    
}
