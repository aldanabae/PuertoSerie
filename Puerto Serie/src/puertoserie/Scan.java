
package puertoserie;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Scan {
    
    PuertoSerie puertoSerie = new PuertoSerie();
    CRC crc = new CRC();
    
    public ArrayList pedirDatos(int idDispositivo, int nroFuncion, int direccionInicial,
                            int cantidadVariables, String puerto){
        
        ArrayList tramaEnvia = new ArrayList();
        ArrayList tramaRecibe = new ArrayList();
        byte[] crcGenerado;
        
        //CONVIERTE INT RECIBIDOS DE PANTALLA A BYTE
        byte byteIdDispositivo = (byte) (idDispositivo & 0xFF);
        tramaEnvia.add(byteIdDispositivo);
        System.out.println("ID dispositivo en binario: "+Integer.toBinaryString(byteIdDispositivo & 0xFF));
        
        byte byteNroFuncion = (byte)(nroFuncion & 0xFF);
        tramaEnvia.add(byteNroFuncion);
        System.out.println("Nro funcion en binario: "+Integer.toBinaryString(byteNroFuncion & 0xFF));
        
        byte byteDireccionInicialHigh = (byte) ((direccionInicial >> 8) & 0xFF);
        tramaEnvia.add(byteDireccionInicialHigh);
        System.out.println("Direccion inicial High en binario: "+Integer.toBinaryString(byteDireccionInicialHigh & 0xFF));
        byte byteDireccionInicialLow = (byte) (direccionInicial & 0xFF);
        tramaEnvia.add(byteDireccionInicialLow);
        System.out.println("Direccion inicial Low en binario: "+Integer.toBinaryString(byteDireccionInicialLow & 0xFF));
        
        byte byteCantidadHigh = (byte) ((cantidadVariables >> 8) & 0xFF);
        tramaEnvia.add(byteCantidadHigh);
        System.out.println("Longitud High en binario: "+Integer.toBinaryString(byteCantidadHigh & 0xFF));
        byte byteCantidadLow = (byte) (cantidadVariables & 0xFF);
        tramaEnvia.add(byteCantidadLow);
        System.out.println("Longitud Low en binario: "+Integer.toBinaryString(byteCantidadLow & 0xFF));
        
        // ARMA TRAMA SIN CRC
        byte[] tramaEnviaSinCRC = new byte[] { byteIdDispositivo, byteNroFuncion, byteDireccionInicialHigh, byteDireccionInicialLow, byteCantidadHigh, byteCantidadLow};
        
        // GENERA CRC
        crcGenerado = crc.generarCRC(tramaEnviaSinCRC);
        byte byteCRCHigh = crcGenerado[0];
        tramaEnvia.add(byteCRCHigh);
        System.out.println("CRC High en binario: "+Integer.toBinaryString(byteCRCHigh & 0xFF));
        byte byteCRCLow = crcGenerado[1];
        tramaEnvia.add(byteCRCLow);
        System.out.println("CRC Low en binario: "+Integer.toBinaryString(byteCRCLow & 0xFF));
        
        // ENVIA TRAMA
        ArrayList datosPantalla = new ArrayList();
        try {
            
            puertoSerie.configurar(puerto);
            puertoSerie.enviar(tramaEnvia);
            tramaRecibe = puertoSerie.recibir();
            
            // MUESTRA TRAMA RECIBIDA ------ BORRAR
            System.out.println("\nTrama recibida: ");
            for (int i = 0; i < Integer.parseInt(tramaRecibe.get(2).toString())+5; i++) {
                System.out.printf("%H ", tramaRecibe.get(i));
            }
            System.out.println("\n");
            
            // ARMA TRAMA RECIBIDA SIN CRC
            byte[] tramaRecibeSinCRC = new byte[Integer.parseInt(tramaRecibe.get(2).toString())+3];
            for (int i = 0; i < Integer.parseInt(tramaRecibe.get(2).toString())+3; i++) {
                tramaRecibeSinCRC[i] = (byte)tramaRecibe.get(i);
            }
            // GENERA CRC DE LA TRAMA RECIBIDA
            crcGenerado = crc.generarCRC(tramaRecibeSinCRC);
            byteCRCHigh = crcGenerado[0]; // CRC generado High
            byteCRCLow = crcGenerado[1]; // CRC Generado Low
            byte byteCrcRecibidoHigh = (byte)tramaRecibe.get(Integer.parseInt(tramaRecibe.get(2).toString())+3);
            byte byteCrcRecibidoLow = (byte)tramaRecibe.get(Integer.parseInt(tramaRecibe.get(2).toString())+4);       
            
            // VERIFICACION DE CRC GENERADO Y RECIBIDO ------- BORRAR
            System.out.println("COMPARACION DE CRC");
            System.out.println("CRC Generado High: "+Integer.toHexString(byteCRCHigh & 0xFF));
            System.out.println("CRC Generado Low: "+Integer.toHexString(byteCRCLow & 0xFF));
            System.out.println("CRC Recibido High: "+Integer.toHexString(byteCrcRecibidoHigh & 0xFF));
            System.out.println("CRC Recibido Low: "+Integer.toHexString(byteCrcRecibidoLow & 0xFF));
                        
            // VERIFICA QUE EL CRC GENERADO ES IGUAL AL CRC RECIBIDO
            if(byteCRCHigh == byteCrcRecibidoHigh && byteCRCLow == byteCrcRecibidoLow){
                // CONVIERTE BYTES A INT PARA ENVIAR A PANTALLA
                for (int i = 0; i < Integer.parseInt(tramaRecibe.get(2).toString()); i++) {
                    int j=i+1;
                    int high = (byte)tramaRecibe.get(3+i) << 8;
                    int low = (byte)tramaRecibe.get(3+j);
                    int nro = high + low;
                    System.out.println(" ---> "+nro);
                    datosPantalla.add(nro);
                    i++;
                }
            }else{
                System.out.println("CRC INCORRECTO");
            }
        } catch (Exception ex) {
            Logger.getLogger(Scan.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return datosPantalla;
    }
}
