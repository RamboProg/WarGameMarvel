package views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;

import engine.Game;

public class View extends JFrame {
    private ActionListener listener;
	private JPanel board;
    private JPanel champChoice;

    public View(ActionListener l){
        this.listener = l;

        setTitle("Marvel War Game");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); //Maximises screen
		setBounds(0, 50, 1280, 720);//when i de-maximise
		setVisible(true);

        board = new JPanel(new GridLayout(Game.getBoardheight(), Game.getBoardwidth()));
    }

    public void popUpChampCoice(){
        champChoice = new JPanel(new GridLayout());

    }

    public void popUpUserEntry() {
        JTextField firstName = new JTextField(20);
        JLabel label1 = new JLabel("Enter 1st Player's name: ");

        JTextField secondName = new JTextField(20);
        JLabel label2 = new JLabel("Enter the 2nd Player's name: ");

	}

    public void updateBoard(Object[][] board) {
        //TO-DO Figure out what to put here
        
        
        //Refresh and tell java that sth changed
		this.add(this.board);
		this.revalidate();
		this.repaint();
    }

    
}
