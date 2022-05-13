package engine;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ButtonDemo extends JFrame implements ActionListener {

	public ButtonDemo(){
		super();
		setSize(400,100);
		getContentPane().setLayout(new FlowLayout());
		JButton button1 = new JButton();
		//button1.setActionListener(this);
		button1.setActionCommand("Red");
	}
	public static void main(String[] args){
		
	}
	
	public void actionPerformed(ActionEvent e){
		
	}
	}

