package ru.medev.bubble;

import java.awt.*;

public class GameBack {

    //Field
    private Color color;

    //Constructor
    public GameBack(){
        color = Color.BLUE;
    }

    //Function
    public void update(){

    }

    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillRect(0,0, GamePanel.WIDTH, GamePanel.HEIGHT);
    }
}


