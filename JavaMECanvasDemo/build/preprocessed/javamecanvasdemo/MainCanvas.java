/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javamecanvasdemo;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author peter
 */
public class MainCanvas extends Canvas {
    
    private Midlet midlet;
    private int x = 40;
    private int y = 40;
    private int dX = 5;
    private final int R_CIRCLE = 50;
    private boolean enabled = true;
    
    private class GameThread extends Thread {
        public void run() {
            while (enabled) {
                if (dX == 5) {
                    if (x+R_CIRCLE>getWidth()) {
                        dX = -5;
                    }
                    x+=dX;
                        repaint();
                } else if (dX == -5) {
                    if (x<0) {
                        dX = 5;
                        
                    }
                    x+= dX;
                    repaint();
                }
                
                try {
                    sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    
    
    public MainCanvas(Midlet midletMain) {
        this.midlet = midletMain;
        new GameThread().start();
    }

    protected void paint(Graphics g) {
        g.setColor(0,0,0);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(255, 0, 0);
        g.fillArc(x, y, R_CIRCLE, R_CIRCLE, 0, 360);
        
        g.setColor(255, 255, 255);
        g.drawString("Pontszám: 0", 5, 5, 0);
        
    }
    
    public void stopGame() {
        enabled = false;
    }

    protected void pointerDragged(int x, int y) {
        this.x=x;
        this.y=y;
        repaint();
    }
    
    

    protected void keyPressed(int keyCode) {
        switch(keyCode) {
            case -3:
                x -= 5;
                break;
            case -4:
                x += 5;
                break;
            default:
                break;
        }
        
        repaint();
    }

}
