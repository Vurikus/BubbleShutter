package ru.medev.bubble;

import java.awt.*;

public class Player {

    //Field
    private double x;
    private double y;
    private int r;

    private double dx = 0;
    private double dy = 0;

    private int speed =5;
    private double health = 10;

    private Color color1;
    private Color color2;

    public static boolean up = false;
    public static boolean down = false;
    public static boolean left = false;
    public static boolean right = false;
    public static boolean isFiring = false;

    //Constructor
    public Player(){
        x = GamePanel.WIDTH/2;
        y = GamePanel.HEIGHT/2 + 50;

        r = 5;
        color1 = Color.WHITE;

    }

    //Function
    public double getX() {return x;}
    public double getY() {return y;}
    public int getR(){return r;}

    public void hit(){ health --;}

    public void update(){
        if(up && y>r)dy=-speed;
        if(down && y<GamePanel.HEIGHT-r) dy=speed;
        if(left && x>r)dx=-speed;
        if(right && x<GamePanel.WIDTH-r) dx=speed;
        if(up&&left||up&&right||down&&left||down&&right){
            dy = dy*Math.sin(Math.toRadians(45));
            dx = dx*Math.cos(Math.toRadians(45));
        }
        y+=dy;
        x+=dx;
        dy=0;
        dx=0;

        if(isFiring) {
            GamePanel.bullets.add(new Bullet());
        }
    }

    public void draw(Graphics2D g){
        g.setColor(color1);
        g.fillOval((int)(x-r), (int)(y-r), 2*r,2*r);
        g.setStroke(new BasicStroke(2));
        g.setColor(color1.darker());
        g.drawOval((int)(x-r), (int)(y-r), 2*r,2*r);
        g.setStroke(new BasicStroke(1));
    }
}
