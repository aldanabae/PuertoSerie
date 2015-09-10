
package puertoserie;

import giovynet.nativelink.SerialPort;
import giovynet.serial.Baud;
import giovynet.serial.Com;
import giovynet.serial.Parameters;

public class PuertoSerie {
    
    Com com;
    
    public void conectar(String puerto) throws Exception{   
        SerialPort puertoSerie = new SerialPort();
        if(puertoSerie.getStateSerialPortC(puerto).equals("free")){
            Parameters parameters;
            parameters = new Parameters(); //Create a parameter object
            parameters.setPort(puerto);//assigns the first port found
            parameters.setBaudRate(Baud._9600);//assigns baud rate
            parameters.setByteSize("8");// assigns byte size
            parameters.setParity("N");// assigns parity
            com = new Com(parameters);// With the "parameters" creates a "Com"
        }
    }
    
    public void enviar (byte dato) throws Exception{
        com.sendSingleData(dato);
    }
    
    public byte recibir() throws Exception{
        int  datoRecibido = com.receiveSingleCharAsInteger();
        byte byteRecibido = (byte)(datoRecibido);
        return byteRecibido;
    }
    
    public void desconectar() throws Exception{
        com.close();
    }
}
