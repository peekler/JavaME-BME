/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javamehttpweather;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.*;

/**
 * @author peter
 */
public class Midlet extends MIDlet implements CommandListener{

    private class WeatherThread extends Thread {
        
        private String city;
        
        public WeatherThread(String city) {
            this.city = city;
        }
        
        public void run() {
            HttpConnection hc = null;
            InputStream is = null; 
            try {
                hc = (HttpConnection) Connector.open("http://api.openweathermap.org/data/2.5/"
                        + "weather?q="
                        + city
                        + "&units=metric&appid=2de143494c0b295cca9337e1e96b00e0");
                is = hc.openInputStream();
                
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int ch;
                while((ch=is.read()) != -1) {
                    bos.write(ch);
                }
                
                String result = new String(
                        bos.toByteArray());
                
                formMain.append(result);
                
            } catch(Exception e) {
                formMain.append(e.getMessage());
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                
                if (hc != null) {
                    try {
                        hc.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
    
    
    
    private Form formMain;
    private Command cmdGet;
    private TextField tfCity;
    
    public void startApp() {
        formMain = new Form("Weather");
        
        cmdGet = new Command("Get weather", Command.OK, 0);
        formMain.addCommand(cmdGet);
        formMain.setCommandListener(this);
        
        tfCity = new TextField("City", "Budapest", 30, TextField.ANY);
        formMain.append(tfCity);
        
        Display.getDisplay(this).setCurrent(formMain);
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
        if (c.equals(cmdGet)) {
            new WeatherThread(tfCity.getString()).start();
        }
    }
}
