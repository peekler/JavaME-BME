/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediademo;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Item;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.ToneControl;
import javax.microedition.media.control.VideoControl;
import javax.microedition.midlet.*;

/**
 * @author peter
 */
public class Midlet extends MIDlet implements CommandListener {

    private Form formMain;
    private Command cmdMediaInfos;
    private Command cmdPlayTone;
    private Command cmdCameraOnForm;
    private Command cmdTakePhoto;
    
    private VideoControl videoControl = null;

    public void startApp() {
        formMain = new Form("Media Demo");

        cmdMediaInfos = new Command("Media Info", Command.SCREEN, 0);
        formMain.addCommand(cmdMediaInfos);
        cmdPlayTone = new Command("Play tone", Command.SCREEN, 1);
        formMain.addCommand(cmdPlayTone);
        cmdCameraOnForm = new Command("CameraOnForm", Command.SCREEN, 2);
        formMain.addCommand(cmdCameraOnForm);
        cmdTakePhoto = new Command("Take photo", Command.OK, 0);
        formMain.addCommand(cmdTakePhoto);
        formMain.setCommandListener(this);

        Display.getDisplay(this).setCurrent(formMain);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
        if (c.equals(cmdMediaInfos)) {
            showMediaInfos();
        } else if (c.equals(cmdPlayTone)) {
            playTone();
        } else if (c.equals(cmdCameraOnForm)) {
            showCameraOnForm();
        } else if (c.equals(cmdTakePhoto)) {
            new MySnapShotTaker().start();
        }
    }

    private void playTone() {
        try {
            // hang lejátszása 4000 ms hosszan 100-as hangerővel
            Manager.playTone(ToneControl.C4, 4000, 100);
        } catch (MediaException me) {
            me.printStackTrace();
        }
    }

    private void showMediaInfos() {
        formMain.append("Midi device locator: " + Manager.MIDI_DEVICE_LOCATOR + "\n");
        formMain.append("Tone device locator: " + Manager.TONE_DEVICE_LOCATOR + "\n");

        // Kezelt tartalmak és protokollok lekérdezése
        String[] tempContentTypes
                = Manager.getSupportedContentTypes(null);
        for (int i = 0; i < tempContentTypes.length; i++) {
            String[] tempProtocols = Manager.getSupportedProtocols(tempContentTypes[i]);
            formMain.append("PROTOCOLS to: " + tempContentTypes[i] + "\n");
            for (int j = 0; i < tempProtocols.length; i++) {
                formMain.append(tempProtocols[j].toString() + "\n");
            }
        }

    }

    private void showCameraOnForm() {
        try {
            Player player = Manager.createPlayer("capture://video");
            player.realize();
            videoControl = (VideoControl) (
                    player.getControl("VideoControl"));
            if (videoControl != null) {
                formMain.append((Item) (videoControl.initDisplayMode(
                        VideoControl.USE_GUI_PRIMITIVE
                        ,null)));
            }
            player.start();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    
    class MySnapShotTaker extends Thread
    {
        public void run()
        {
            if (videoControl != null) {
                try {
                    byte[] snap = videoControl.getSnapshot("encoding=jpeg");
                    if (snap != null){                     
                        Image im = Image.createImage(snap, 0, snap.length);
                        Alert al = new Alert(
                                "Snapshot","Here's the snap",
                                im,AlertType.INFO);
                        al.setTimeout(2000);
                        Display.getDisplay(Midlet.this).setCurrent(
                                al, formMain);
                    }             
                }
                catch (MediaException ex){
                    ex.printStackTrace();
                }   
            }
        }
    }

}
