package views;
import javax.swing.*;

import engine.Game;
import engine.Player;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
public class QuizController implements ActionListener {
    protected static Game model;
    private Player p1;
    private Player p2;
    private QuizView view;

    
    public QuizController(){
        p1 = new Player("Nigga 1");
        p2 = new Player("Nigga 2");
        
        try {
            model = new Game(p1, p2);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        view = new QuizView(this);
        view.createWindow();

    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(QuizView.next.getActionCommand())){
            view.updateWindow();
        }
    }

    public static void main(String[] args) {
        new QuizController();
    }
    
}
