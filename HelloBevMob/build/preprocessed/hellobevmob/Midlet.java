/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellobevmob;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.*;


public class Midlet extends MIDlet implements CommandListener {
    
    private Command cmdExit;
    private Command cmdStart;
    private Command cmdStop;
    
    private Form formMain;
    private TextField tfNum;
    private boolean enabled = false;
    
    private class DemoThread extends Thread {
        public void run() {
            while (enabled) {
                formMain.append(tfNum.getString());
                
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    private void initUI() {
        formMain = new Form("Main menu");
        formMain.append("Hello BevMob csoport!");
        
        tfNum = new TextField("Visszamlálás:",
                "1", 3, TextField.NUMERIC);
        formMain.append(tfNum);
        
        
        cmdExit = new Command("Exit", Command.EXIT, 0);
        formMain.addCommand(cmdExit);
        cmdStart = new Command("Start", Command.SCREEN, 0);
        formMain.addCommand(cmdStart);
        cmdStop = new Command("Stop", Command.SCREEN, 1);
        formMain.addCommand(cmdStop);
        
        formMain.setCommandListener(this);
        Display.getDisplay(this).setCurrent(formMain);
    }
    
    public void startApp() {
        initUI();
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
        enabled = false;
    }
    
    public void commandAction(Command c, Displayable d) {
        if (c.equals(cmdExit)) {
            destroyApp(true);
            notifyDestroyed();
        } else if (c.equals(cmdStart)) {
            if (!enabled) {
                enabled = true;
                new DemoThread().start();
            }
        } else if (c.equals(cmdStop)) {
            enabled = false;
        }
    }
}
