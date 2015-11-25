/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javameuidemo;

import java.io.IOException;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.*;

/**
 * @author peter
 */
public class Midlet extends MIDlet implements ItemCommandListener{
    
    private Form formMain;

    public void startApp() {
        formMain = new Form("UI Demo");
        
        TextField tfAge = new TextField("Kor:", "", 4, TextField.DECIMAL);
        formMain.append(tfAge);
        
        Gauge gauge = new Gauge("Progress:", true, 100, 8);
        formMain.append(gauge);
        
        try {
            Image img = Image.createImage("/res/test.png");
            ImageItem imgItem = new ImageItem("Kép:", img,
                Item.LAYOUT_CENTER, "nincs kép");
            formMain.append(imgItem);
        } catch (IOException ex) {
            ex.printStackTrace();
            formMain.append(ex.getMessage());
        }
        
        formMain.append(new MyUIElement("Hello"));
        
        
        StringItem btnOk = new StringItem("", "Press me", Item.BUTTON);
        btnOk.setItemCommandListener(this);
        Command cmdOk = new Command("Ok", Command.OK, 0);
        btnOk.setDefaultCommand(cmdOk);
        formMain.append(btnOk);
        
        
        Display.getDisplay(this).setCurrent(formMain);
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Item item) {
        formMain.append("#");
    }
}
