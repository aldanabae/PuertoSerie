package serial.firmware;

import giovynet.serial.Baud;
import giovynet.serial.Com;
import giovynet.serial.Parameters;

public class puertoSerial {
	 
	 public static void main(String[] args)throws Exception{
		 
		 Parameters configuracion = new Parameters();
		 
		 configuracion.setPort("COM1");
		 
		 configuracion.setBaudRate(Baud._9600); 
		 /*
		  * esta propiedad maneja la velocidad de las impresoras
		  * */
		 configuracion.setMinDelayWrite(10);
		 
		 Com com1= new Com(configuracion);
		 
		 //para enviar caracteres de escape como ESC/POS
		 char[] codigoEnvio={27,'V',1};
		 
		 com1.sendArrayChar(codigoEnvio);
		 //envio del codigo a la impresora
		 
		 //para envio de cadena de caracteres
		 com1.sendString("Hola Mundo desde FirmWare");
		 
		 //se cierra la comunicación con el puerto
		 com1.close();
	 }
}
