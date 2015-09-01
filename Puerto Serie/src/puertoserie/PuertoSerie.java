/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puertoserie;

import giovynet.nativelink.SerialPort;
import giovynet.serial.Baud;
import giovynet.serial.Com;
import giovynet.serial.Parameters;
import java.util.ArrayList;
import java.util.List;

public class PuertoSerie {
    
    Com com;
    
    public void configurar(String puerto) throws Exception {
        SerialPort serialPort = new SerialPort();
        List<String> lstFreeSerialPort = serialPort.getFreeSerialPort();//Gets a list of serial ports free 
        if(lstFreeSerialPort.size()>0){//if there are free ports
            Parameters parameters = new Parameters();//Create a parameter object
            parameters.setPort(puerto);//assigns the first port found
            parameters.setBaudRate(Baud._9600);//assigns baud rate
            parameters.setByteSize("8");// assigns byte size
            parameters.setParity("N");// assigns parity
            com  = new Com(parameters);// With the "parameters" creates a "Com"
        }
    }
    
    public void enviar (ArrayList tramaEnviada) throws Exception{
        for (int i = 0; i < tramaEnviada.size() ; i++) {
            com.sendSingleData((byte)tramaEnviada.get(i));
        }
    }
    
    public ArrayList recibir() throws Exception{
        ArrayList tramaRecibida = new ArrayList();
        for (int i = 0; i < 50; i++) {//100 Send and receive data every 800 milliseconds
            int  datoRecibido = com.receiveSingleCharAsInteger();
            tramaRecibida.add(datoRecibido);
            //Thread.sleep(100);//100  Wait 0.8 seconds
        }
        return tramaRecibida;
    }
}
