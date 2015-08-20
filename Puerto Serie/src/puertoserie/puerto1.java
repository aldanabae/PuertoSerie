/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puertoserie;

import giovynet.nativelink.SerialPort;
import giovynet.permissions.Info;
import giovynet.serial.Baud;
import giovynet.serial.Com;
import giovynet.serial.Parameters;
import java.util.List;


public class puerto1 {
    
   public static void main (String[] arg) throws Exception{
        showsInfoAboutGiovynetDriver();// Shows Information about Giovynet Driver
        SerialPort serialPort = new SerialPort();
        List<String> lstFreeSerialPort = serialPort.getFreeSerialPort();//Gets a list of serial ports free 
        if(lstFreeSerialPort.size()>0){//if there are free ports
            Parameters parameters = new Parameters();//Create a parameter object
            parameters.setPort("COM1");//assigns the first port found
            parameters.setBaudRate(Baud._460800);//assigns baud rate
            parameters.setByteSize("8");// assigns byte size
            parameters.setParity("N");// assigns parity
            Com com  = new  Com(parameters);// With the "parameters" creates a "Com"
            System.out.println(com.getPort());
            
            for (int i = 0; i < 10; i++) {//Send and receive data every 800 milliseconds
                com.sendSingleData(41);
                System.out.println("Send 41 (integer)...");
                Thread.sleep(800);//Wait 0.8 seconds
                int  dataReceived =com.receiveSingleCharAsInteger();
                System.out. println("...Data received: "+dataReceived);
                Thread.sleep(800);//Wait 0.8 seconds
            }
            System.out.println("-- End of transmission--");
            
        }else{
        	System.out.println("There is no free serial ports.");
        }   
        
   }
   public static void showsInfoAboutGiovynetDriver(){
       System.out.println("------------------------------------------------------------------------------->");
       System.out.println("Giovynet Driver version "+Info.getVersion());
       System.out.println("Type of distribution: "+Info.getTypeOfDistribution());
       System.out.println("Number of devices allowed: "+Info.getNumDevicesAllowed());
       System.out.println("Distribution permissions: "+Info.getDistributionLicense());
       System.out.println("<-------------------------------------------------------------------------------");
    }  
    
}

    

