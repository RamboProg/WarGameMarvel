package engine;

import javax.swing.*;

public class FirstWindow {
	public static void main(String[] args) {
		JFrame myWindow = new JFrame(); // Creating the a Window object
		myWindow.setSize(400, 200);
		myWindow.setVisible(true);

		myWindow.setTitle("My first Wndow");

		JLabel l = new JLabel("wa7wa7 teezo na3ma");
		myWindow.add(l);

		myWindow.getContentPane().add(l); // was used a long timed ago
		
	}
}
