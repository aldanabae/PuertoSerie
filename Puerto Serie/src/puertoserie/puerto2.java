
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
            parameters.setBaudRate(Baud._9600);//assigns baud rate
            parameters.setByteSize("8");// assigns byte size
            parameters.setParity("N");// assigns parity
            Com com  = new  Com(parameters);// With the "parameters" creates a "Com"
            System.out.println(com.getPort());
            
            //ENVIA BYTES
            for (int i = 0; i < 8 ; i++) {
                com.sendSingleData(trama[i]);
               // System.out.printf("%02X ", trama[i]);
            }
            
            System.out.println("");   
//            
//            //CANTIDAD DE BYTES A LEER
//            byte[] longitudAUX = new byte[]{trama[5], trama[4]};
//            int longitud = convertirByteAInt(longitudAUX);
//            System.out.println("longitud:      "+longitud);
//            
            for (int i = 0; i < 100; i++) {//Send and receive data every 800 milliseconds
                int  dataReceived =com.receiveSingleCharAsInteger();
                System.out.println("Recibido: "+Integer.toHexString(dataReceived));
                Thread.sleep(100);//Wait 0.8 seconds
            }
            System.out.println("Fin de la transmision");
            
        }else{
        	System.out.println("No hay puertos libres.");
        }   
   }
    
    //CONVIERTE 2 BYTES A INT, RECIBE DE LOW A HIGH
//    public int convertirByteAInt(byte[] b){          
//    int value= 0;
//    for(int i=0;i<b.length;i++){                
//    int n=(b[i]<0?(int)b[i]+256:(int)b[i])<<(8*i);   
//        value+=n;
//    }         
//    return value;       
//}
}

    

