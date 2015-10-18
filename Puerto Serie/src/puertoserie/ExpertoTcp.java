
package puertoserie;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExpertoTcp {
    
    ArrayList tramaEnvia;
    ArrayList tramaRecibe;
    ArrayList datosPantalla;
    DTOPantalla dto;
    int cantidadDeIntentosDeConexion = 1;
    int nroTransaccion = 1;
    
    public DTOPantalla funcionTres(DTOPantalla dto){
        
        this.dto = dto;
        datosPantalla = new ArrayList();
        
        int variablesEnviadas = 0;
        int variablesRestantes=dto.getCantidadVariables();
        while(variablesRestantes > 0){
            
                tramaEnvia = new ArrayList();
                // ARMA LA TRAMA CON LOS DATOS RECIBIDOS DE LA PANTALLA
                
                // TRANSACCION
                byte byteNroTransaccionLow = (byte) (nroTransaccion & 0xFF);
                tramaEnvia.add(byteNroTransaccionLow);
                byte byteNroTransaccionHigh = (byte) ((nroTransaccion >> 8) & 0xFF);
                tramaEnvia.add(byteNroTransaccionHigh);
                nroTransaccion = nroTransaccion + 1;
                
                // PROTOCOLO
                byte byteProtocoloHigh = (byte) ((0 >> 8) & 0xFF);
                tramaEnvia.add(byteProtocoloHigh);
                byte byteProtocoloLow = (byte) (0 & 0xFF);
                tramaEnvia.add(byteProtocoloLow);
                
                // CANTIDAD BYTES
                byte byteCantidadBytesHigh = (byte) ((6 >> 8) & 0xFF);
                tramaEnvia.add(byteCantidadBytesHigh);
                byte byteCantidadBytesLow = (byte) (6 & 0xFF);
                tramaEnvia.add(byteCantidadBytesLow);
                
                // DISPOSITIVO
                byte byteIdDispositivo = (byte) (this.dto.getIdDispositivo() & 0xFF);
                tramaEnvia.add(byteIdDispositivo);

                // FUNCION
                byte byteNroFuncion = (byte)(this.dto.getNroFuncion() & 0xFF);
                tramaEnvia.add(byteNroFuncion);
            
                // DIRECCION INICIAL
                byte byteDireccionInicialHigh = (byte) (((this.dto.getDireccionInicial()+variablesEnviadas) >> 8) & 0xFF);
                tramaEnvia.add(byteDireccionInicialHigh);
                byte byteDireccionInicialLow = (byte) ((this.dto.getDireccionInicial()+variablesEnviadas) & 0xFF);
                tramaEnvia.add(byteDireccionInicialLow);
            
                if(variablesRestantes > 125){
                    byte byteCantidadHigh = (byte) ((125 >> 8) & 0xFF);
                    tramaEnvia.add(byteCantidadHigh);
                    byte byteCantidadLow = (byte) (125 & 0xFF);
                    tramaEnvia.add(byteCantidadLow);
                } else{
                    byte byteCantidadHigh = (byte) ((variablesRestantes >> 8) & 0xFF);
                    tramaEnvia.add(byteCantidadHigh);
                    byte byteCantidadLow = (byte) (variablesRestantes & 0xFF);
                    tramaEnvia.add(byteCantidadLow);
                }

                // ENVIA TRAMA
                try {
                    enviarTrama("127.0.0.1", 502, tramaEnvia);

                    // VERIFICA CODIGO DE ERROR
                    dto = new DTOPantalla();
                    if((int)tramaRecibe.get(7) != 131){ // VERIFICA QUE EL 2DO BYTE NO SEA 83(COD.ERROR)
                        // MUESTRA TRAMA RECIBIDA
                        System.out.println("\nTrama recibida: ");
                        String aux = "";
                        for (int i = 0; i < tramaRecibe.size(); i++) {
                            System.out.printf("%H ", tramaRecibe.get(i));
                            aux = aux+Integer.toHexString((int)tramaRecibe.get(i))+" ";
                        }
                        System.out.println("\n");

                        // HIGH + LOW
                        for (int i = 0; i < (int)tramaRecibe.get(8); i++) {
                            int j=i+1;
                            int high = (int)tramaRecibe.get(9+i) << 8;
                            int low = (int)tramaRecibe.get(9+j);
                            int nro = high + low;
                            datosPantalla.add(nro);
                            i++;
                        }
                        dto.setDatos(datosPantalla);
                        dto.setTrama(aux);
                    }else{
                        datosPantalla.add("");
                        dto.setTrama("ERROR 0x83");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ExpertoModbus.class.getName()).log(Level.SEVERE, null, ex);
                }
        variablesEnviadas = variablesEnviadas + 125;
        variablesRestantes = variablesRestantes - 125;
        }
        return dto;
    }
    
    public DTOPantalla funcionSeis(DTOPantalla dto){
        
        this.dto = dto;
        datosPantalla = new ArrayList();

        int valor=dto.getCantidadVariables();

            
                tramaEnvia = new ArrayList();
                // ARMA LA TRAMA CON LOS DATOS RECIBIDOS DE LA PANTALLA
                
                // TRANSACCION
                byte byteNroTransaccionLow = (byte) (nroTransaccion & 0xFF);
                tramaEnvia.add(byteNroTransaccionLow);
                byte byteNroTransaccionHigh = (byte) ((nroTransaccion >> 8) & 0xFF);
                tramaEnvia.add(byteNroTransaccionHigh);
                nroTransaccion = nroTransaccion + 1;
                
                // PROTOCOLO
                byte byteProtocoloHigh = (byte) ((0 >> 8) & 0xFF);
                tramaEnvia.add(byteProtocoloHigh);
                byte byteProtocoloLow = (byte) (0 & 0xFF);
                tramaEnvia.add(byteProtocoloLow);
                
                // CANTIDAD BYTES
                byte byteCantidadBytesHigh = (byte) ((6 >> 8) & 0xFF);
                tramaEnvia.add(byteCantidadBytesHigh);
                byte byteCantidadBytesLow = (byte) (6 & 0xFF);
                tramaEnvia.add(byteCantidadBytesLow);
                
                // DISPOSITIVO
                byte byteIdDispositivo = (byte) (this.dto.getIdDispositivo() & 0xFF);
                tramaEnvia.add(byteIdDispositivo);

                // FUNCION
                byte byteNroFuncion = (byte)(this.dto.getNroFuncion() & 0xFF);
                tramaEnvia.add(byteNroFuncion);
            
                // DIRECCION
                byte byteDireccionHigh = (byte) ((this.dto.getDireccionInicial() >> 8) & 0xFF);
                tramaEnvia.add(byteDireccionHigh);
                byte byteDireccionLow = (byte) (this.dto.getDireccionInicial() & 0xFF);
                tramaEnvia.add(byteDireccionLow);
            
                byte byteValorHigh = (byte) ((valor >> 8) & 0xFF);
                tramaEnvia.add(byteValorHigh);
                byte byteValorLow = (byte) (valor & 0xFF);
                tramaEnvia.add(byteValorLow);

                // ENVIA TRAMA
                try {
                    enviarTrama("127.0.0.1", 502, tramaEnvia);

                    // VERIFICA CODIGO DE ERROR
                    dto = new DTOPantalla();
                    if((int)tramaRecibe.get(7) != 131){ // VERIFICA QUE EL 2DO BYTE NO SEA 83(COD.ERROR)
                        // MUESTRA TRAMA RECIBIDA
                        System.out.println("\nTrama recibida: ");
                        String aux = "";
                        for (int i = 0; i < tramaRecibe.size(); i++) {
                            System.out.printf("%H ", tramaRecibe.get(i));
                            aux = aux+Integer.toHexString((int)tramaRecibe.get(i))+" ";
                        }
                        System.out.println("\n");

                        // HIGH + LOW
                        for (int i = 0; i < (int)tramaRecibe.get(8); i++) {
                            int j=i+1;
                            int high = (int)tramaRecibe.get(9+i) << 8;
                            int low = (int)tramaRecibe.get(9+j);
                            int nro = high + low;
                            datosPantalla.add(nro);
                            i++;
                        }
                        dto.setDatos(datosPantalla);
                        dto.setTrama(aux);
                    }else{
                        datosPantalla.add("");
                        dto.setTrama("ERROR 0x83");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ExpertoModbus.class.getName()).log(Level.SEVERE, null, ex);
                }
        return dto;
    }
    
    public DTOPantalla funcionDieciseis(DTOPantalla dto){
        
        this.dto = dto;
        datosPantalla = new ArrayList();

        int valor=dto.getCantidadVariables();

            
                tramaEnvia = new ArrayList();
                // ARMA LA TRAMA CON LOS DATOS RECIBIDOS DE LA PANTALLA
                
                // TRANSACCION
                byte byteNroTransaccionLow = (byte) (nroTransaccion & 0xFF);
                tramaEnvia.add(byteNroTransaccionLow);
                byte byteNroTransaccionHigh = (byte) ((nroTransaccion >> 8) & 0xFF);
                tramaEnvia.add(byteNroTransaccionHigh);
                nroTransaccion = nroTransaccion + 1;
                
                // PROTOCOLO
                byte byteProtocoloHigh = (byte) ((0 >> 8) & 0xFF);
                tramaEnvia.add(byteProtocoloHigh);
                byte byteProtocoloLow = (byte) (0 & 0xFF);
                tramaEnvia.add(byteProtocoloLow);
                
                // CANTIDAD BYTES
                byte byteCantidadBytesHigh = (byte) ((6 >> 8) & 0xFF);
                tramaEnvia.add(byteCantidadBytesHigh);
                byte byteCantidadBytesLow = (byte) (6 & 0xFF);
                tramaEnvia.add(byteCantidadBytesLow);
                
                // DISPOSITIVO
                byte byteIdDispositivo = (byte) (this.dto.getIdDispositivo() & 0xFF);
                tramaEnvia.add(byteIdDispositivo);

                // FUNCION
                byte byteNroFuncion = (byte)(this.dto.getNroFuncion() & 0xFF);
                tramaEnvia.add(byteNroFuncion);
            
                // DIRECCION
                byte byteDireccionHigh = (byte) ((this.dto.getDireccionInicial() >> 8) & 0xFF);
                tramaEnvia.add(byteDireccionHigh);
                byte byteDireccionLow = (byte) (this.dto.getDireccionInicial() & 0xFF);
                tramaEnvia.add(byteDireccionLow);
            
                byte byteValorHigh = (byte) ((valor >> 8) & 0xFF);
                tramaEnvia.add(byteValorHigh);
                byte byteValorLow = (byte) (valor & 0xFF);
                tramaEnvia.add(byteValorLow);

                // ENVIA TRAMA
                try {
                    enviarTrama("127.0.0.1", 502, tramaEnvia);

                    // VERIFICA CODIGO DE ERROR
                    dto = new DTOPantalla();
                    if((int)tramaRecibe.get(7) != 131){ // VERIFICA QUE EL 2DO BYTE NO SEA 83(COD.ERROR)
                        // MUESTRA TRAMA RECIBIDA
                        System.out.println("\nTrama recibida: ");
                        String aux = "";
                        for (int i = 0; i < tramaRecibe.size(); i++) {
                            System.out.printf("%H ", tramaRecibe.get(i));
                            aux = aux+Integer.toHexString((int)tramaRecibe.get(i))+" ";
                        }
                        System.out.println("\n");

                        // HIGH + LOW
                        for (int i = 0; i < (int)tramaRecibe.get(8); i++) {
                            int j=i+1;
                            int high = (int)tramaRecibe.get(9+i) << 8;
                            int low = (int)tramaRecibe.get(9+j);
                            int nro = high + low;
                            datosPantalla.add(nro);
                            i++;
                        }
                        dto.setDatos(datosPantalla);
                        dto.setTrama(aux);
                    }else{
                        datosPantalla.add("");
                        dto.setTrama("ERROR 0x83");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ExpertoModbus.class.getName()).log(Level.SEVERE, null, ex);
                }
        return dto;
    }
    
    public void enviarTrama (String host, int puerto, ArrayList tramaEnvio){
        ArrayList trama = tramaEnvio;
        try{
            Socket socket;
            socket = new Socket(host,puerto);
            DataOutputStream salidaTCP = new DataOutputStream(socket.getOutputStream());
            for (int i = 0; i < trama.size(); i++) {
                salidaTCP.write((byte)trama.get(i) & 0xFF);
            }
            DataInputStream entradaTCP = new DataInputStream(socket.getInputStream());
            tramaRecibe = new ArrayList();
            for (int i = 0; i < 6; i++) {
                int byteRecibido = entradaTCP.readUnsignedByte();
                tramaRecibe.add((byte)byteRecibido & 0xFF);
            }
            for (int i = 0; i < (int)tramaRecibe.get(5); i++) {
                int byteRecibido = entradaTCP.readUnsignedByte();
                tramaRecibe.add((byte)byteRecibido & 0xFF);
            }
        }catch (IOException ex) { 
            Logger.getLogger(ExpertoTcp.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
