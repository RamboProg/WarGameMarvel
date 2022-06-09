package views;

import javax.swing.JButton;

import model.world.Champion;

public class ChampionButton extends JButton {
    private Champion c;
    private String name;

    public ChampionButton(String name){
        this.name = name;
        setName(name);

    }


    public Champion getChampButton() {
        return this.c;
    }

    public void setChampButton(Champion c) {
        this.c = c;
    }


}
