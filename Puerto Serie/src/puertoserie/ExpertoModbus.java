
package puertoserie;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExpertoModbus {
    
    ArrayList tramaEnvia;
    byte[] tramaEnviaSinCRC;
    CRC crc;
    byte[] crcGenerado;
    PuertoSerie puertoSerie;
    ArrayList tramaRecibe;
    ArrayList datosPantalla;
    DTOPantalla dto;
    int cantidadDeIntentosDeConexion = 3;
    
    public DTOPantalla funcionTres(DTOPantalla dto){
        
        this.dto = new DTOPantalla();
        this.dto = dto;
             
        int variablesEnviadas = 0;
        int variablesRestantes=dto.getCantidadVariables();
        while(variablesRestantes > 0){
            
            
                tramaEnvia = new ArrayList();
                //CONVIERTE INT RECIBIDOS DE PANTALLA A BYTE
                byte byteIdDispositivo = (byte) (dto.getIdDispositivo() & 0xFF);
                tramaEnvia.add(byteIdDispositivo);

                byte byteNroFuncion = (byte)(dto.getNroFuncion() & 0xFF);
                tramaEnvia.add(byteNroFuncion);
                
            
                byte byteDireccionInicialHigh = (byte) (((dto.getDireccionInicial()+variablesEnviadas) >> 8) & 0xFF);
                tramaEnvia.add(byteDireccionInicialHigh);
                byte byteDireccionInicialLow = (byte) ((dto.getDireccionInicial()+variablesEnviadas) & 0xFF);
                tramaEnvia.add(byteDireccionInicialLow);
            
                if(variablesRestantes > 127){
                    byte byteCantidadHigh = (byte) ((127 >> 8) & 0xFF);
                    tramaEnvia.add(byteCantidadHigh);
                    byte byteCantidadLow = (byte) (127 & 0xFF);
                    tramaEnvia.add(byteCantidadLow);
                    System.out.println(" entro al ifff");
                    // ARMA TRAMA SIN CRC
                    tramaEnviaSinCRC = new byte[] { byteIdDispositivo, byteNroFuncion, byteDireccionInicialHigh, byteDireccionInicialLow, byteCantidadHigh, byteCantidadLow};
                    variablesRestantes = variablesRestantes - 127;
                } else{
                    byte byteCantidadHigh = (byte) ((variablesRestantes >> 8) & 0xFF);
                    tramaEnvia.add(byteCantidadHigh);
                    byte byteCantidadLow = (byte) (variablesRestantes & 0xFF);
                    tramaEnvia.add(byteCantidadLow);
                    
                    // ARMA TRAMA SIN CRC
                    tramaEnviaSinCRC = new byte[] { byteIdDispositivo, byteNroFuncion, byteDireccionInicialHigh, byteDireccionInicialLow, byteCantidadHigh, byteCantidadLow};
                }
                
            
                
                // GENERA CRC
                crc = new CRC();
                crcGenerado = crc.generarCRC(tramaEnviaSinCRC);
                byte byteCRCHigh = crcGenerado[0];
                tramaEnvia.add(byteCRCHigh);
                byte byteCRCLow = crcGenerado[1];
                tramaEnvia.add(byteCRCLow);

                // ENVIA TRAMA
                try {
                    puertoSerie = new PuertoSerie();

                    // CONECTA CON EL PUERTO SERIE
                    puertoSerie.conectar(dto.getPuerto());
                    System.out.println("Utilizando el puerto: \n"+dto.getPuerto()+"\n");

                    // WHILE CANTIDAD DE INTENTOS DE CONEXION
                    int intento = 1;
                    while(intento <= cantidadDeIntentosDeConexion){
                        System.out.println("\nIntento de conexión "+intento+"...");
                            // ENVIA
                                for (int i = 0; i < tramaEnvia.size(); i++) {
                                    puertoSerie.enviar((byte)tramaEnvia.get(i));
                                }
                                // RECIBE
                                tramaRecibe = new ArrayList();
                                for (int i = 0; i < 259; i++) {
                                    byte byteRecibido = puertoSerie.recibir();
                                    int datoRecibido = byteRecibido & 0xFF;
                                    tramaRecibe.add(datoRecibido);
                                }
                            System.out.println(tramaRecibe);
                            if(tramaRecibe.get(0).toString().equals("0")){
                                intento = intento + 1;
                                System.out.println("FALLO");
                            }else{
                                intento = intento + 3;
                                System.out.println("OK");
                            }
                    }

                    // CIERRA CONEXION
                    puertoSerie.desconectar();

                    // VERIFICA CODIGO DE ERROR
                    dto = new DTOPantalla();
                    if((int)tramaRecibe.get(1) != 0x83){ // VERIFICA QUE EL 2DO BYTE NO SEA 83(COD.ERROR)
                        // MUESTRA TRAMA RECIBIDA
                        System.out.println("\nTrama recibida: ");
                        String aux = "";
                        for (int i = 0; i < Integer.parseInt(tramaRecibe.get(2).toString())+5; i++) {
                            System.out.printf("%H ", tramaRecibe.get(i));
                            aux = aux+Integer.toHexString((int)tramaRecibe.get(i))+" ";
                        }
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

                        // MUESTRA CRC GENERADO Y RECIBIDO ------- BORRAR
                        System.out.println("Comparación de CRC...");
                        System.out.println("CRC Generado High: "+Integer.toHexString(byteCRCHigh & 0xFF));
                        System.out.println("CRC Generado Low: "+Integer.toHexString(byteCRCLow & 0xFF));
                        System.out.println("CRC Recibido High: "+Integer.toHexString(byteCrcRecibidoHigh & 0xFF));
                        System.out.println("CRC Recibido Low: "+Integer.toHexString(byteCrcRecibidoLow & 0xFF));

                        // VERIFICA QUE EL CRC GENERADO ES IGUAL AL CRC RECIBIDO
                        if(byteCRCHigh == byteCrcRecibidoHigh && byteCRCLow == byteCrcRecibidoLow){
                            System.out.println("OK");
                            // HIGH + LOW
                            datosPantalla = new ArrayList();
                            for (int i = 0; i < Integer.parseInt(tramaRecibe.get(2).toString()); i++) {
                                int j=i+1;
                                int high = (int)tramaRecibe.get(3+i) << 8;
                                int low = (int)tramaRecibe.get(3+j);
                                int nro = high + low;
                                datosPantalla.add(nro);
                                i++;
                            }
                            dto.setDatos(datosPantalla);
                            dto.setTrama(aux);
                        }else{
                            System.out.println("ERROR");
                            dto.setTrama("CRC Incorrecto");
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
        
        return null;
    }
    
    public DTOPantalla funcionSeis(DTOPantalla dto){
        
        this.dto = new DTOPantalla();
        this.dto = dto;
        
        tramaEnvia = new ArrayList();
        //CONVIERTE INT RECIBIDOS DE PANTALLA A BYTE
        byte byteIdDispositivo = (byte) (dto.getIdDispositivo() & 0xFF);
        tramaEnvia.add(byteIdDispositivo);
        
        byte byteNroFuncion = (byte)(dto.getNroFuncion() & 0xFF);
        tramaEnvia.add(byteNroFuncion);
        
        byte byteDireccionInicialHigh = (byte) ((dto.getDireccionInicial() >> 8) & 0xFF);
        tramaEnvia.add(byteDireccionInicialHigh);
        byte byteDireccionInicialLow = (byte) (dto.getDireccionInicial() & 0xFF);
        tramaEnvia.add(byteDireccionInicialLow);
        
        byte byteCantidadHigh = (byte) ((dto.getCantidadVariables() >> 8) & 0xFF);
        tramaEnvia.add(byteCantidadHigh);
        byte byteCantidadLow = (byte) (dto.getCantidadVariables() & 0xFF);
        tramaEnvia.add(byteCantidadLow);
        
        // ARMA TRAMA SIN CRC
        tramaEnviaSinCRC = new byte[] { byteIdDispositivo, byteNroFuncion, byteDireccionInicialHigh, byteDireccionInicialLow, byteCantidadHigh, byteCantidadLow};
        
        // GENERA CRC
        crc = new CRC();
        crcGenerado = crc.generarCRC(tramaEnviaSinCRC);
        byte byteCRCHigh = crcGenerado[0];
        tramaEnvia.add(byteCRCHigh);
        byte byteCRCLow = crcGenerado[1];
        tramaEnvia.add(byteCRCLow);
        
        // ENVIA TRAMA
        try {
            puertoSerie = new PuertoSerie();
            
            // CONECTA CON EL PUERTO SERIE
            puertoSerie.conectar(dto.getPuerto());
            System.out.println("Utilizando el puerto: \n"+dto.getPuerto()+"\n");
            
            // WHILE CANTIDAD DE INTENTOS DE CONEXION
            int intento = 1;
            while(intento <= cantidadDeIntentosDeConexion){
                System.out.println("\nIntento de conexión "+intento+"...");
                // ENVIA
                for (int i = 0; i < tramaEnvia.size(); i++) {
                    puertoSerie.enviar((byte)tramaEnvia.get(i));
                }

                // RECIBE
                tramaRecibe = new ArrayList();
                for (int i = 0; i < 300; i++) {
                    byte byteRecibido = puertoSerie.recibir();
                    int datoRecibido = byteRecibido & 0xFF;
                    tramaRecibe.add(datoRecibido);
                }
                if(tramaRecibe.get(0).toString().equals("0")){
                    intento = intento + 1;
                    System.out.println("FALLO");
                }else{
                    intento = intento + 3;
                    System.out.println("OK");
                }    
            }
            
            // CIERRA CONEXION
            puertoSerie.desconectar();
            
            // VERIFICA CODIGO DE ERROR
            dto = new DTOPantalla();
            if((int)tramaRecibe.get(1) != 0x83){ // VERIFICA QUE EL 2DO BYTE NO SEA 83(COD.ERROR)
                // MUESTRA TRAMA RECIBIDA
                System.out.println("\nTrama recibida: ");
                String aux = "";
                for (int i = 0; i < Integer.parseInt(tramaRecibe.get(2).toString())+5; i++) {
                    System.out.printf("%H ", tramaRecibe.get(i));
                    aux = aux+Integer.toHexString((int)tramaRecibe.get(i))+" ";
                }
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

                // MUESTRA CRC GENERADO Y RECIBIDO ------- BORRAR
                System.out.println("Comparación de CRC...");
                System.out.println("CRC Generado High: "+Integer.toHexString(byteCRCHigh & 0xFF));
                System.out.println("CRC Generado Low: "+Integer.toHexString(byteCRCLow & 0xFF));
                System.out.println("CRC Recibido High: "+Integer.toHexString(byteCrcRecibidoHigh & 0xFF));
                System.out.println("CRC Recibido Low: "+Integer.toHexString(byteCrcRecibidoLow & 0xFF));
                
                // VERIFICA QUE EL CRC GENERADO ES IGUAL AL CRC RECIBIDO
                if(byteCRCHigh == byteCrcRecibidoHigh && byteCRCLow == byteCrcRecibidoLow){
                    System.out.println("OK");
                    // HIGH + LOW
                    datosPantalla = new ArrayList();
                    for (int i = 0; i < Integer.parseInt(tramaRecibe.get(2).toString()); i++) {
                        int j=i+1;
                        int high = (int)tramaRecibe.get(3+i) << 8;
                        int low = (int)tramaRecibe.get(3+j);
                        int nro = high + low;
                        datosPantalla.add(nro);
                        i++;
                    }
                    dto.setDatos(datosPantalla);
                    dto.setTrama(aux);
                }else{
                    System.out.println("ERROR");
                    dto.setTrama("CRC Incorrecto");
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

