package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import engine.Game;


public class Controller implements ActionListener{
    
    private Game model;
    private View view;

    public Controller(){
        model = new Game(model.getFirstPlayer(), model.getSecondPlayer());
        
        //Passing the controller as a parameter (Passing itself)
        view = new View(this); 
        Object[][] board = model.getBoard();
        view.updateBoard(board);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

    public static void main(String[] args) {
		new Controller();
	}



    
}