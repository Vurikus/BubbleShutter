package ru.medev.bubble;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    //Field
    public static int WIDTH = 600;
    public static int HEIGHT = 600;

    public static int mouseX;
    public static int mouseY;

    public static boolean leftMouse = false;

    private Thread thread;

    private BufferedImage image;
    private Graphics2D g;

    private int FPS;
    private long timerFPS;
    private double millisToFPS;
    private int sleepTime;

    public enum STATES{
        MENU,
        PLAY
    }

    public static STATES state = STATES.MENU;

    private static GameBack background = new GameBack();
    public static Player gamer = new Player();
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Enemy> enemies;
    public static Wave wave = new Wave();
    public static Menu menu = new Menu();


    //Constructor
    public GamePanel(){
        super();
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setFocusable(true);
        requestFocus();
        addMouseMotionListener(new Listeners());
        addKeyListener(new Listeners());
        addMouseListener(new Listeners());
    }

    //Function
    public void start(){
        thread = new Thread(this);
        thread.start();
    }

    public void run() {

        FPS = 30;
        millisToFPS = 1000/FPS;
        sleepTime = 0;

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);



        bullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();


        Toolkit kit = Toolkit.getDefaultToolkit();
        BufferedImage buffered = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)buffered.getGraphics();
        g2.setColor(Color.WHITE);
        g2.drawOval(0,0,4,4);
        g2.drawLine(2,0,2,4);
        g2.drawLine(0,2,4,2);
        Cursor myCursor = kit.createCustomCursor(buffered, new Point(3,3), "myCursor");
        g2.dispose();

        while (true){

            this.setCursor(myCursor);

            timerFPS = System.nanoTime();
            if(state.equals(STATES.MENU)){
                background.update();
                background.draw(g);
                menu.update();
                menu.draw(g);
                gameDraw();
            }
            if(state.equals(STATES.PLAY)) {


                gameUpdate();
                gameRender();
                gameDraw();

                timerFPS = (System.nanoTime() - timerFPS) / 1000000;
                if (millisToFPS > timerFPS) {
                    sleepTime = (int) (millisToFPS - timerFPS);
                } else sleepTime = 1;
                try {
                    thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timerFPS = 0;
                sleepTime = 1;
            }
        }
    }

    public void gameUpdate (){
        background.update();
        gamer.update();
        for(int i = 0; i< bullets.size(); i++){
            bullets.get(i).update();
            boolean remove = bullets.get(i).remove();
            if (remove) {
                bullets.remove(i);
                i--;
            }
        }

        for (int i = 0; i<enemies.size(); i++){
            enemies.get(i).update();

        }

        for(int i = 0; i<enemies.size(); i++){
            Enemy e = enemies.get(i);
            double ex = e.getX();
            double ey = e.getY();
            for (int j = 0; j<bullets.size(); j++){
                Bullet b = bullets.get(j);
                double bx = b.getX();
                double by = b.getY();
                double dx = ex - bx;
                double dy = ey - by;
                double dist = Math.sqrt(dx*dx + dy*dy);
                if ((int)dist <= e.getR() + b.getR()){
                    e.hit();
                    bullets.remove(j);
                    j--;
                    boolean remove = e.remove();
                    if(remove) {
                        enemies.remove(i);
                        i--;
                        break;
                    }

                }
            }

        }

        for (int i=0; i< enemies.size(); i++){
            Enemy e = enemies.get(i);
            double ex = e.getX();
            double ey = e.getY();
            double px = gamer.getX();
            double py = gamer.getY();
            double dx = ex - px;
            double dy = ey - py;
            double dist = Math.sqrt(dx*dx + dy*dy);
            if ((int)dist<= e.getR()+gamer.getR()){
                e.hit();
                gamer.hit();
                boolean remove = e.remove();
                if(remove) {
                    enemies.remove(i);
                    i--;
                }

            }

        }
        wave.update();

    }

    public void gameRender(){
        background.draw(g);
        gamer.draw(g);
        for(int i = 0; i<bullets.size(); i++){
            bullets.get(i).draw(g);
        }
        for (int i = 0; i<enemies.size(); i++){
            enemies.get(i).draw(g);

        }
        if (wave.showWave())  wave.draw(g);
    }

    private void gameDraw(){
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0,0,null);
        g2.dispose();
    }
}
