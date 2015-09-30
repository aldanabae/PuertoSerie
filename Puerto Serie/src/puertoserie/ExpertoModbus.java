
package puertoserie;

import java.util.ArrayList;
import java.util.StringTokenizer;
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
        
        this.dto = dto;
        datosPantalla = new ArrayList();
        
        int variablesEnviadas = 0;
        int variablesRestantes=dto.getCantidadVariables();
        while(variablesRestantes > 0){
            
                tramaEnvia = new ArrayList();
                //CONVIERTE INT RECIBIDOS DE PANTALLA A BYTE
                byte byteIdDispositivo = (byte) (this.dto.getIdDispositivo() & 0xFF);
                tramaEnvia.add(byteIdDispositivo);

                byte byteNroFuncion = (byte)(this.dto.getNroFuncion() & 0xFF);
                tramaEnvia.add(byteNroFuncion);
            
                byte byteDireccionInicialHigh = (byte) (((this.dto.getDireccionInicial()+variablesEnviadas) >> 8) & 0xFF);
                tramaEnvia.add(byteDireccionInicialHigh);
                byte byteDireccionInicialLow = (byte) ((this.dto.getDireccionInicial()+variablesEnviadas) & 0xFF);
                tramaEnvia.add(byteDireccionInicialLow);
            
                if(variablesRestantes > 125){
                    byte byteCantidadHigh = (byte) ((125 >> 8) & 0xFF);
                    tramaEnvia.add(byteCantidadHigh);
                    byte byteCantidadLow = (byte) (125 & 0xFF);
                    tramaEnvia.add(byteCantidadLow);
                    // ARMA TRAMA SIN CRC
                    tramaEnviaSinCRC = new byte[] { byteIdDispositivo, byteNroFuncion, byteDireccionInicialHigh, byteDireccionInicialLow, byteCantidadHigh, byteCantidadLow};
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
                    puertoSerie.conectar(this.dto.getPuerto());
                    System.out.println("Utilizando el puerto: \n"+this.dto.getPuerto()+"\n");

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
                            //System.out.println(tramaRecibe); // BORRAR
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
        variablesEnviadas = variablesEnviadas + 125;
        variablesRestantes = variablesRestantes - 125;
        }
        return dto;
    }
    
    public DTOPantalla funcionSeis(DTOPantalla dto){
    
        this.dto = dto;
        datosPantalla = new ArrayList();
            
                tramaEnvia = new ArrayList();
                //CONVIERTE INT RECIBIDOS DE PANTALLA A BYTE
                byte byteIdDispositivo = (byte) (this.dto.getIdDispositivo() & 0xFF);
                tramaEnvia.add(byteIdDispositivo);

                byte byteNroFuncion = (byte)(this.dto.getNroFuncion() & 0xFF);
                tramaEnvia.add(byteNroFuncion);
            
                byte byteDireccionInicialHigh = (byte) (((this.dto.getDireccionInicial()) >> 8) & 0xFF);
                tramaEnvia.add(byteDireccionInicialHigh);
                byte byteDireccionInicialLow = (byte) ((this.dto.getDireccionInicial()) & 0xFF);
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
                    puertoSerie.conectar(this.dto.getPuerto());
                    System.out.println("Utilizando el puerto: \n"+this.dto.getPuerto()+"\n");

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
                            //System.out.println(tramaRecibe); // BORRAR
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
                    if((int)tramaRecibe.get(1) != 0x86){ // VERIFICA QUE EL 2DO BYTE NO SEA 86(COD.ERROR)
                        // MUESTRA TRAMA RECIBIDA
                        System.out.println("\nTrama recibida: ");
                        String aux = "";
                        for (int i = 0; i < 6; i++) {
                            System.out.printf("%H ", tramaRecibe.get(i));
                            aux = aux+Integer.toHexString((int)tramaRecibe.get(i))+" ";
                        }
                        System.out.println("\n");

                        // ARMA TRAMA RECIBIDA SIN CRC
                        byte[] tramaRecibeSinCRC = new byte[6];
                        for (int i = 0; i < 6; i++) {
                            tramaRecibeSinCRC[i] = (byte)((int)tramaRecibe.get(i) & 0xFF);
                        }
                        
                        // GENERA CRC DE LA TRAMA RECIBIDA
                        crcGenerado = crc.generarCRC(tramaRecibeSinCRC);
                        byteCRCHigh = crcGenerado[0]; // CRC generado High
                        byteCRCLow = crcGenerado[1]; // CRC Generado Low
                        byte byteCrcRecibidoHigh = (byte)((int)tramaRecibe.get(6));
                        byte byteCrcRecibidoLow = (byte)((int)tramaRecibe.get(7));       

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
                        dto.setTrama("ERROR 0x86");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ExpertoModbus.class.getName()).log(Level.SEVERE, null, ex);
                }
        return dto;
    }     
    
    public DTOPantalla funcionDieciseis(DTOPantalla dto){
    
        this.dto = dto;
        datosPantalla = new ArrayList();
            
                tramaEnvia = new ArrayList();
                
                byte byteIdDispositivo = (byte) (this.dto.getIdDispositivo() & 0xFF);
                tramaEnvia.add(byteIdDispositivo);

                byte byteNroFuncion = (byte)(this.dto.getNroFuncion() & 0xFF);
                tramaEnvia.add(byteNroFuncion);
            
                byte byteDireccionInicialHigh = (byte) (((this.dto.getDireccionInicial()) >> 8) & 0xFF);
                tramaEnvia.add(byteDireccionInicialHigh);
                byte byteDireccionInicialLow = (byte) ((this.dto.getDireccionInicial()) & 0xFF);
                tramaEnvia.add(byteDireccionInicialLow);
                
                // SEPARA LOS VALORES DELIMITADOS POR ","
                StringTokenizer st = new StringTokenizer(this.dto.getVariablesDelimitadas(),",");
                int cantidadvariables = 0;    
                while (st.hasMoreTokens()){
                    st.nextToken();
                    cantidadvariables++;
                }
                         
                System.out.println("Cantidad de variables a enviar: " + cantidadvariables);
                byte byteCantidadvariablesHigh = (byte) (((cantidadvariables) >> 8) & 0xFF);
                tramaEnvia.add(byteCantidadvariablesHigh);
                byte byteCantidadvariablesLow = (byte) (cantidadvariables & 0xFF);
                tramaEnvia.add(byteCantidadvariablesLow);
                
                byte byteCantidadBytesEnvia = (byte) ((cantidadvariables * 2) & 0xFF);
                tramaEnvia.add(byteCantidadBytesEnvia);
                System.out.println("Cantidad de bytes a enviar: " + byteCantidadBytesEnvia);
          
                st = new StringTokenizer(this.dto.getVariablesDelimitadas(),",");
                while (st.hasMoreTokens()){
                    int valor = Integer.parseInt(st.nextToken());
                    System.out.println (valor);
                    byte byteValorHigh = (byte) (((cantidadvariables) >> 8) & 0xFF);
                    tramaEnvia.add(byteValorHigh);
                    byte byteValorLow = (byte) (cantidadvariables & 0xFF);
                    tramaEnvia.add(byteValorLow);
                }

   
                // ARMA TRAMA SIN CRC
                tramaEnviaSinCRC = new byte[] { byteIdDispositivo, byteNroFuncion, byteDireccionInicialHigh, byteDireccionInicialLow};
                
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
                    puertoSerie.conectar(this.dto.getPuerto());
                    System.out.println("Utilizando el puerto: \n"+this.dto.getPuerto()+"\n");

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
                            //System.out.println(tramaRecibe); // BORRAR
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
                    if((int)tramaRecibe.get(1) != 0x86){ // VERIFICA QUE EL 2DO BYTE NO SEA 86(COD.ERROR)
                        // MUESTRA TRAMA RECIBIDA
                        System.out.println("\nTrama recibida: ");
                        String aux = "";
                        for (int i = 0; i < 6; i++) {
                            System.out.printf("%H ", tramaRecibe.get(i));
                            aux = aux+Integer.toHexString((int)tramaRecibe.get(i))+" ";
                        }
                        System.out.println("\n");

                        // ARMA TRAMA RECIBIDA SIN CRC
                        byte[] tramaRecibeSinCRC = new byte[6];
                        for (int i = 0; i < 6; i++) {
                            tramaRecibeSinCRC[i] = (byte)((int)tramaRecibe.get(i) & 0xFF);
                        }
                        
                        // GENERA CRC DE LA TRAMA RECIBIDA
                        crcGenerado = crc.generarCRC(tramaRecibeSinCRC);
                        byteCRCHigh = crcGenerado[0]; // CRC generado High
                        byteCRCLow = crcGenerado[1]; // CRC Generado Low
                        byte byteCrcRecibidoHigh = (byte)((int)tramaRecibe.get(6));
                        byte byteCrcRecibidoLow = (byte)((int)tramaRecibe.get(7));       

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
                        dto.setTrama("ERROR 0x86");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ExpertoModbus.class.getName()).log(Level.SEVERE, null, ex);
                }
        return null;
    }

}   
