/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javameuidemo;

import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author peter
 */
public class MyUIElement extends CustomItem{

    public MyUIElement(String label) {
        super(label);
    }
    
    protected int getMinContentHeight() {
        return 100;
    }

    protected int getMinContentWidth() {
        return 100;
    }

    protected int getPrefContentHeight(int width) {
        return 100;
    }

    protected int getPrefContentWidth(int height) {
        return 100;
    }

    protected void paint(Graphics g, int w, int h) {
        g.setColor(0,0,0);
        g.fillRect(0, 0, 80, 80);
    }
    
}
