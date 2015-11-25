/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmsdemo;

import java.util.Date;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.*;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 * @author peter
 */
public class Midlet extends MIDlet {

    private static final String RS_TIME = "RS_Time";
    
    public void startApp() {
        Form formMain = new Form("RMS Demo");
        
        String lastStart = loadLastStartTime();
        
        formMain.append(lastStart);
        
        Display.getDisplay(this).setCurrent(formMain);
        
        saveStartTime();
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
    private void saveStartTime() {
        RecordStore recordStoreTime = null;
        try {
            recordStoreTime = RecordStore.openRecordStore(RS_TIME, true);
            byte[] timeStamp = new Date(System.currentTimeMillis()).toString().getBytes();
            
            if (recordStoreTime.getNumRecords()>0) {
                recordStoreTime.setRecord(1, timeStamp, 0, timeStamp.length);
            } else {
                recordStoreTime.addRecord(
                        timeStamp
                        , 0, timeStamp.length);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (recordStoreTime != null) {
                try {
                    recordStoreTime.closeRecordStore();
                } catch (RecordStoreException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    
    public String loadLastStartTime() {
        RecordStore recordStoreTime = null;
        try {
            recordStoreTime = RecordStore.openRecordStore(RS_TIME, true);
            byte[] timeStamp = recordStoreTime.getRecord(1);

            return new String(timeStamp);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (recordStoreTime != null) {
                try {
                    recordStoreTime.closeRecordStore();
                } catch (RecordStoreException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        return "This is the first time you use the app!";
    }
    
    
    
}
