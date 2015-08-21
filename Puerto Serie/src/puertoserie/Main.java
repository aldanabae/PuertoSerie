
package puertoserie;

public class Main {
    
    public static void main (String[] arg) throws Exception{
   
        int idDispositivo=1;
        int funcion=3;
        int direccionInicial=1;
        int cantidadVariables=5;
        int crc;
        puerto2 a=new puerto2();
        byte[] trama = new byte[] { (byte)0x01, (byte)0x03, (byte)0x00, (byte)0x01, (byte)0x00, (byte) 0x03};
        
        CRC generadorCRC = new CRC();
        crc = generadorCRC.generarCRC(trama);
        
        System.out.println("crc: "+crc);
    
        a.main(trama);
    
    }   
}
