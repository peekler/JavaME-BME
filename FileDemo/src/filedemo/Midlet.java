/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filedemo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.*;

/**
 * @author peter
 */
public class Midlet extends MIDlet implements CommandListener {

    private Command cmdRead;
    private Command cmdWrite;
    private Form formMain;

    public void startApp() {
        formMain = new Form("File Demo");

        cmdRead = new Command("Read", Command.SCREEN, 0);
        formMain.addCommand(cmdRead);
        cmdWrite = new Command("Write", Command.SCREEN, 0);
        formMain.addCommand(cmdWrite);
        formMain.setCommandListener(this);

        Display.getDisplay(this).setCurrent(formMain);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
        if (c.equals(cmdRead)) {
            byte[] data = readFileContent("root1/data/test.txt");
            formMain.append(new String(data));
        } else if (c.equals(cmdWrite)) {
            writeFileContent("root1/data/test.txt","BevMob");
        }
    }

    private void writeFileContent(String path, String content) {
        FileConnection conn = null;
        OutputStream os = null;
        try {
            conn = (FileConnection) Connector.open("file:///" 
                    + path, Connector.WRITE);
            
            os = conn.openOutputStream();
            os.write(content.getBytes());
            os.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    private byte[] readFileContent(String path) {
        FileConnection conn = null;
        InputStream in = null;
        try {
            conn = (FileConnection) Connector.open("file:///" + path,
                    Connector.READ);

            in = conn.openInputStream();
            byte[] content = new byte[(int) conn.fileSize()];
            in.read(content);
            return content;
        } catch (IOException ex) {
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
