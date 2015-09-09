
package puertoserie;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExpertoModbus {
    
    PuertoSerie puertoSerie = new PuertoSerie();
    CRC crc = new CRC();
    byte[] tramaEnviaSinCRC;
    
    
    public DTOdatosPantalla funcionTres(int idDispositivo, int nroFuncion, int direccionInicial,
                            int cantidadVariables, String puerto){
        
        ArrayList tramaEnvia = new ArrayList();
        ArrayList tramaRecibe = new ArrayList();
        ArrayList datosPantalla = new ArrayList();
        byte[] crcGenerado;
        DTOdatosPantalla dto = new DTOdatosPantalla();
        
        //CONVIERTE INT RECIBIDOS DE PANTALLA A BYTE
        byte byteIdDispositivo = (byte) (idDispositivo & 0xFF);
        tramaEnvia.add(byteIdDispositivo);
        
        byte byteNroFuncion = (byte)(nroFuncion & 0xFF);
        tramaEnvia.add(byteNroFuncion);
        
        byte byteDireccionInicialHigh = (byte) ((direccionInicial >> 8) & 0xFF);
        tramaEnvia.add(byteDireccionInicialHigh);
        byte byteDireccionInicialLow = (byte) (direccionInicial & 0xFF);
        tramaEnvia.add(byteDireccionInicialLow);
        
        byte byteCantidadHigh = (byte) ((cantidadVariables >> 8) & 0xFF);
        tramaEnvia.add(byteCantidadHigh);
        byte byteCantidadLow = (byte) (cantidadVariables & 0xFF);
        tramaEnvia.add(byteCantidadLow);
        
        // ARMA TRAMA SIN CRC
        tramaEnviaSinCRC = new byte[] { byteIdDispositivo, byteNroFuncion, byteDireccionInicialHigh, byteDireccionInicialLow, byteCantidadHigh, byteCantidadLow};
        
        // GENERA CRC
        crcGenerado = crc.generarCRC(tramaEnviaSinCRC);
        byte byteCRCHigh = crcGenerado[0];
        tramaEnvia.add(byteCRCHigh);
        byte byteCRCLow = crcGenerado[1];
        tramaEnvia.add(byteCRCLow);
        
        // ENVIA TRAMA
        try {
            // CONFIGURA EL PUERTO SERIE
            puertoSerie.configurar(puerto);
            
            // ENVIA
            for (int i = 0; i < tramaEnvia.size(); i++) {
                puertoSerie.enviar((byte)tramaEnvia.get(i));
            }
            
            // RECIBE
            for (int i = 0; i < 100; i++) {
                byte byteRecibido = puertoSerie.recibir();
                int datoRecibido = byteRecibido & 0xFF;
                tramaRecibe.add(datoRecibido);
            }
            
            // VERIFICA CODIGO DE ERROR
            if((int)tramaRecibe.get(1) != 0x83){
                // MUESTRA TRAMA RECIBIDA ------ BORRAR
                System.out.println("\nTrama recibida: ");
                String aux = "";
                for (int i = 0; i < Integer.parseInt(tramaRecibe.get(2).toString())+5; i++) {
                    System.out.printf("%H ", tramaRecibe.get(i));
                     aux = aux+Integer.toHexString((int)tramaRecibe.get(i))+" ";
                }
                dto.setTrama(aux);
                System.out.println("\n");
                
                // ARMA TRAMA RECIBIDA SIN CRC
                byte[] tramaRecibeSinCRC = new byte[Integer.parseInt(tramaRecibe.get(2).toString())+3];
                for (int i = 0; i < Integer.parseInt(tramaRecibe.get(2).toString())+3; i++) {
                    tramaRecibeSinCRC[i] = (byte)((int)tramaRecibe.get(i) & 0xFF);
                }
                
                // GENERA CRC DE LA TRAMA RECIBIDA
                crcGenerado = crc.generarCRC(tramaRecibeSinCRC);
                byteCRCHigh = crcGenerado[0]; // CRC generado High
                byteCRCLow = crcGenerado[1]; // CRC Generado Low
                byte byteCrcRecibidoHigh = (byte)((int)tramaRecibe.get(Integer.parseInt(tramaRecibe.get(2).toString())+3));
                byte byteCrcRecibidoLow = (byte)((int)tramaRecibe.get(Integer.parseInt(tramaRecibe.get(2).toString())+4));       

                // VERIFICACION DE CRC GENERADO Y RECIBIDO ------- BORRAR
                System.out.println("COMPARACION DE CRC");
                System.out.println("CRC Generado High: "+Integer.toHexString(byteCRCHigh & 0xFF));
                System.out.println("CRC Generado Low: "+Integer.toHexString(byteCRCLow & 0xFF));
                System.out.println("CRC Recibido High: "+Integer.toHexString(byteCrcRecibidoHigh & 0xFF));
                System.out.println("CRC Recibido Low: "+Integer.toHexString(byteCrcRecibidoLow & 0xFF));
                System.out.println("\n");

                // VERIFICA QUE EL CRC GENERADO ES IGUAL AL CRC RECIBIDO
                if(byteCRCHigh == byteCrcRecibidoHigh && byteCRCLow == byteCrcRecibidoLow){
                    // HIGH + LOW
                    for (int i = 0; i < Integer.parseInt(tramaRecibe.get(2).toString()); i++) {
                        int j=i+1;
                        int high = (int)tramaRecibe.get(3+i) << 8;
                        int low = (int)tramaRecibe.get(3+j);
                        int nro = high + low;
                        datosPantalla.add(nro);
                        i++;
                    }
                    dto.setDatos(datosPantalla);
                }else{
                    System.out.println("CRC INCORRECTO");
                }
            }else{
                datosPantalla.add("");
                dto.setTrama("ERROR 0x83");
            }
        } catch (Exception ex) {
            Logger.getLogger(ExpertoModbus.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dto;
    }
}
