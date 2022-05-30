package views;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class idk extends JFrame {
   private JLabel label;
   public idk() {
      setTitle("MouseOver Test");
      setLayout(new FlowLayout());
      label = new JLabel("Move the mouse moves over this JLabel");
      label.setOpaque(true);
      add(label);
      label.addMouseListener(new MouseAdapter() {
         public void mouseEntered(MouseEvent evt) {
            Color c = label.getBackground(); // When the mouse moves over a label, the background color changed.
            label.setBackground(label.getForeground());
            label.setForeground(c);
         }
         public void mouseExited(MouseEvent evt) {
            Color c = label.getBackground();
            label.setBackground(label.getForeground());
            label.setForeground(c);
         }
      });
      setSize(400, 275);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLocationRelativeTo(null);
      setVisible(true);
   }
   public static void main(String[] args) {
      new idk();
   }
}

