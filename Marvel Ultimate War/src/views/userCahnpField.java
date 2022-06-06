package views;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.xml.stream.FactoryConfigurationError;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

public class userCahnpField extends JFrame implements ActionListener {

    JTextField textField;
    JButton b;
    JLabel p1ImageLabel;
    public userCahnpField(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        Icon p1Image = new ImageIcon("C:\\M.Ismail\\Coding\\WarGameMarvel\\Marvel Ultimate War\\src\\p1.png");
        // JLabel label = new JLabel();
        // label.setIcon(p1Image);
        b = new JButton("Submit");
        b.addActionListener(this);
        // p1ImageLabel = new JLabel();
        // Icon p1Image = new ImageIcon("p1.png");
        // p1ImageLabel.setIcon(p1Image);

        textField = new JTextField();
        // textField.add(p1ImageLabel);
        // textField.add(label);
        textField.setPreferredSize(new Dimension(250, 40));
        textField.setFont(new Font("Times New Roman", Font.PLAIN, 35));
        textField.setForeground(new Color(32, 183, 226));
        textField.setBackground(new Color(2, 48, 61));
        textField.setCaretColor(new Color(32, 183, 226));

        this.add(b);
        this.add(textField);
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b){
            textField.setEditable(false);
            b.setEnabled(false);
            this.dispose();
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(EXIT_ON_CLOSE);
            JLabel label = new JLabel();
            label.setText("Welcome " + textField.getText());
            f.add(label);
            f.setSize(200, 100);
            f.setVisible(true);
            
        }
        
    }

    public static void main(String[] args) {
        userCahnpField u = new userCahnpField();
        
    }
    
}
