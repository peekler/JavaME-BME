/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javamecanvasdemo;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.*;

/**
 * @author peter
 */
public class Midlet extends MIDlet implements CommandListener{
    
    private Command cmdExit;
    private MainCanvas mainCanvas;

    public void startApp() {
        mainCanvas = new MainCanvas(this);
        
        cmdExit = new Command("Exit", Command.EXIT, 0);
        mainCanvas.addCommand(cmdExit);
        mainCanvas.setCommandListener(this);
        
        Display.getDisplay(this).setCurrent(mainCanvas);
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
        mainCanvas.stopGame();
    }

    public void commandAction(Command c, Displayable d) {
        if (c.equals(cmdExit)) {
            exitApp();
        }
    }

    void exitApp() {
        destroyApp(true);
        notifyDestroyed();
    }
}
