
package puertoserie;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Scan {
    
    PuertoSerie a = new PuertoSerie();
    
    public void pedirDatos(int idDispositivo, int nroFuncion, int direccionInicial,
                            int cantidadVariables, String puerto){
        
        ArrayList tramaEnviada = new ArrayList();
        
        //CONVIERTE INT A BYTE
        byte byteIdDispositivo = (byte) (idDispositivo & 0xFF);
        tramaEnviada.add(byteIdDispositivo);
        System.out.println("ID dispositivo en binario: "+Integer.toBinaryString(byteIdDispositivo & 0xFF));
        
        byte byteNroFuncion = (byte)(nroFuncion & 0xFF);
        tramaEnviada.add(byteNroFuncion);
        System.out.println("Nro funcion en binario: "+Integer.toBinaryString(byteNroFuncion & 0xFF));
        
        byte byteDireccionInicialHigh = (byte) ((direccionInicial >> 8) & 0xFF);
        tramaEnviada.add(byteDireccionInicialHigh);
        System.out.println("Direccion inicial High en binario: "+Integer.toBinaryString(byteDireccionInicialHigh & 0xFF));
        byte byteDireccionInicialLow = (byte) (direccionInicial & 0xFF);
        tramaEnviada.add(byteDireccionInicialLow);
        System.out.println("Direccion inicial Low en binario: "+Integer.toBinaryString(byteDireccionInicialLow & 0xFF));
        
        byte byteCantidadHigh = (byte) ((cantidadVariables >> 8) & 0xFF);
        tramaEnviada.add(byteCantidadHigh);
        System.out.println("Longitud High en binario: "+Integer.toBinaryString(byteCantidadHigh & 0xFF));
        byte byteCantidadLow = (byte) (cantidadVariables & 0xFF);
        tramaEnviada.add(byteCantidadLow);
        System.out.println("Longitud Low en binario: "+Integer.toBinaryString(byteCantidadLow & 0xFF));
        
        //ARMA TRAMA SIN CRC
        byte[] tramaSinCRC = new byte[] { byteIdDispositivo, byteNroFuncion, byteDireccionInicialHigh, byteDireccionInicialLow, byteCantidadHigh, byteCantidadLow};
        
        //GENERA CRC
        CRC crc = new CRC();
        byte[] crcGenerado = crc.generarCRC(tramaSinCRC);
                
        byte byteCRCHigh = crcGenerado[0];
        tramaEnviada.add(byteCRCHigh);
        System.out.println("CRC High en binario: "+Integer.toBinaryString(byteCRCHigh & 0xFF));
        byte byteCRCLow = crcGenerado[1];
        tramaEnviada.add(byteCRCLow);
        System.out.println("CRC Low en binario: "+Integer.toBinaryString(byteCRCLow & 0xFF));
        
        //ARMA TRAMA CON CRC
        //byte[] tramaConCRC = new byte[] { byteIdDispositivo, byteNroFuncion, byteDireccionInicialHigh, byteDireccionInicialLow, byteCantidadHigh, byteCantidadLow, byteCRCHigh, byteCRCLow};
        
        //ENVIA TRAMA
        
        try {
            a.configurar(puerto);
            a.enviar(tramaEnviada);
            ArrayList datosRecibidos = a.recibir();
            
            System.out.println("Trama recibida: ");
        
            for (int i = 0; i < datosRecibidos.size(); i++) {
                System.out.printf("%H ", datosRecibidos.get(i));
            }
        } catch (Exception ex) {
            Logger.getLogger(Scan.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
    }
    
}
