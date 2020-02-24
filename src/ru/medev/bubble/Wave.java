package ru.medev.bubble;

import java.awt.*;

public class Wave {

    //Field
    private int waveNumber = 1;
    private long waveTimer = 0;
    private long waveDelay = 5000;
    private long waveTimerDiff = 0;
    private int waveMultiplier = 5;
    private String waveText;

    //Constructor
    public Wave(){

    }

    //Function
    public void createEnemies(){
        int enemyCount = waveNumber * waveMultiplier;
        if(waveNumber <= 2){
            while (enemyCount>0){
                int type = 1;
                int rank = 1;
                GamePanel.enemies.add(new Enemy(type, rank));
                enemyCount -= type*rank;
            }
        }
        if(waveNumber > 2){
            while (enemyCount>0){
                int type = 1;
                int rank = 1;
                GamePanel.enemies.add(new Enemy(type, rank));
                type = 1;
                rank = 2;
                GamePanel.enemies.add(new Enemy(type, rank));
                type = 2;
                rank = 1;
                GamePanel.enemies.add(new Enemy(type, rank));
                enemyCount -= type*rank;
            }
        }
        waveNumber++;
    }

    public void update(){
        if(GamePanel.enemies.size() == 0 && waveTimer == 0){
            waveTimer = System.nanoTime();
        }
        if (waveTimer>0){
            waveTimerDiff += (System.nanoTime() - waveTimer)/1000000;
            waveTimer = System.nanoTime();
        }
        if(waveTimerDiff > waveDelay){
            createEnemies();
            waveTimer = 0;
            waveTimerDiff = 0;
        }
    }
    public boolean showWave(){
        if (waveTimer != 0) return true;
        else return false;
    }

    public void draw(Graphics2D g){
        double divider = waveDelay/180;
        double alpha = waveTimerDiff/divider;
        alpha = 255*Math.sin(Math.toRadians(alpha));
        if (alpha<=0) alpha = 0;
        if (alpha>=255) alpha = 255;
        g.setFont(new Font("consolas", Font.PLAIN, 20));
        g.setColor(new Color(255,255,255, (int) alpha));
        waveText = "W A V E - " + waveNumber;
        int lenght = (int) g.getFontMetrics().getStringBounds(waveText, g).getWidth();
        g.drawString(waveText, GamePanel.WIDTH/2 - lenght/2, GamePanel.HEIGHT/2);
    }

}
