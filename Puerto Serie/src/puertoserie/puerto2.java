
package puertoserie;

import giovynet.nativelink.SerialPort;
import giovynet.permissions.Info;
import giovynet.serial.Baud;
import giovynet.serial.Com;
import giovynet.serial.Parameters;
import java.util.List;

public class puerto2 {
    
   public void main (byte[] trama) throws Exception{
        SerialPort serialPort = new SerialPort();
        List<String> lstFreeSerialPort = serialPort.getFreeSerialPort();//Gets a list of serial ports free 
        if(lstFreeSerialPort.size()>0){//if there are free ports
            Parameters parameters = new Parameters();//Create a parameter object
            parameters.setPort("COM4");//assigns the first port found
            parameters.setBaudRate(Baud._460800);//assigns baud rate
            parameters.setByteSize("8");// assigns byte size
            parameters.setParity("N");// assigns parity
            Com com  = new  Com(parameters);// With the "parameters" creates a "Com"
            System.out.println(com.getPort());
            
            for (int i = 0; i < trama.length; i++) {
                com.sendSingleData(trama[i]);
                System.out.println(trama[i]);
            }
                  
            for (int i = 0; i < 100; i++) {//Send and receive data every 800 milliseconds
                int  dataReceived =com.receiveSingleCharAsInteger();
                System.out. println("Recibido: "+Integer.toHexString(dataReceived));
                Thread.sleep(100);//Wait 0.8 seconds
            }
            System.out.println("Fin de la transmision");
            
        }else{
        	System.out.println("No hay puertos libres.");
        }   
   }
     
    
}

    

