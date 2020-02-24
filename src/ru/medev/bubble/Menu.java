package ru.medev.bubble;

import java.awt.*;

public class Menu {

    //Field
    private int buttonWight = 120;
    private int buttonHeight = 60;
    private Color color = Color.WHITE;
    public String s;
    private int transp = 0;

    //Constructor
    public Menu(){
        s = "PLAY";

    }
    //Function
    public void update(){
        if     (GamePanel.mouseX > (GamePanel.WIDTH/2 - buttonWight/2) &&
                GamePanel.mouseX < (GamePanel.WIDTH/2+buttonWight/2) &&
                GamePanel.mouseY > (GamePanel.HEIGHT/2 - buttonHeight/2) &&
                GamePanel.mouseY < (GamePanel.HEIGHT/2 + buttonHeight/2)){
            transp = 140;
            if(GamePanel.leftMouse){
                GamePanel.state = GamePanel.STATES.PLAY;
            }
        } else {transp = 0;}
    }

    public void draw(Graphics2D g){
        g.setColor(Color.BLACK);
        g.fillRect(GamePanel.WIDTH/2-buttonWight/2,
                GamePanel.HEIGHT/2 - buttonHeight/2, buttonWight,
                buttonHeight);
        g.setColor(new Color(255,255,255,transp));
        g.fillRect(GamePanel.WIDTH/2-buttonWight/2,
                GamePanel.HEIGHT/2 - buttonHeight/2, buttonWight,
                buttonHeight);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.BOLD, 30));
        long length = (int)g.getFontMetrics().getStringBounds(s,g).getWidth();
        g.drawString(s, (int)(GamePanel.WIDTH/2-length/2), (int)(GamePanel.HEIGHT/2 + buttonHeight/6));
    }

}
