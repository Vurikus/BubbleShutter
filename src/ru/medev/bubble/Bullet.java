package ru.medev.bubble;

import java.awt.*;

public class Bullet {

    //Field
    private double x;
    private double y;
    private double bulletDX;
    private double bulletDY;
    private double distDX;
    private double distDY;
    private double dist;

    private int r = 2;
    private int speed = 20;

    private Color color = Color.WHITE;

    //Constructor
    public Bullet(){
        x = GamePanel.gamer.getX();
        y = GamePanel.gamer.getY();
        distDX = GamePanel.mouseX - x;
        distDY = GamePanel.mouseY - y;
        dist = Math.sqrt(distDX*distDX + distDY*distDY);
        bulletDX = (distDX/dist)*speed;
        bulletDY = (distDY/dist)*speed;
    }

    //Function
    public void update(){
        y +=bulletDY;
        x +=bulletDX;

    }

    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillOval((int)x,(int)y,r,2*r);
    }

    public boolean remove(){
        if(y<0 && y>GamePanel.HEIGHT && x<0 && x>GamePanel.WIDTH){
            return true;
        }
        return false;
    }

    public double getX() {return x;}
    public double getY() {return y;}
    public int getR(){return r;}
}
