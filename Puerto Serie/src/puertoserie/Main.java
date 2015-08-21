
package puertoserie;

public class Main {
    
    public static void main (String[] arg) throws Exception{
   
        int idDispositivo=2;
        int nroFuncion=3;
        int direccionInicial=1;
        int longitud=3;
        
        byte byteIdDispositivo = (byte) (idDispositivo & 0xFF);
        System.out.println("ID dispositov en binario: "+Integer.toBinaryString(byteIdDispositivo & 0xFF));
        
        byte byteNroFuncion = (byte)(nroFuncion & 0xFF);
        System.out.println("Nro funcion en binario: "+Integer.toBinaryString(byteNroFuncion & 0xFF));
        
        byte bytedireccionInicialHigh = (byte) ((direccionInicial >> 8) & 0xFF);
        System.out.println("Direccion inicial High en binario: "+Integer.toBinaryString(bytedireccionInicialHigh & 0xFF));
        byte bytedireccionInicialLow = (byte) (direccionInicial & 0xFF);
        System.out.println("Direccion inicial Low en binario: "+Integer.toBinaryString(bytedireccionInicialLow & 0xFF));
        
        byte bytelongitudHigh = (byte) ((longitud >> 8) & 0xFF);
        System.out.println("Longitud High en binario: "+Integer.toBinaryString(bytelongitudHigh & 0xFF));
        byte bytelongitudLow = (byte) (longitud & 0xFF);
        System.out.println("Longitud Low en binario: "+Integer.toBinaryString(bytelongitudLow & 0xFF));
        
        //ARMA TRAMA SIN CRC
        byte[] tramaSinCRC = new byte[] { byteIdDispositivo, byteNroFuncion, bytedireccionInicialHigh, bytedireccionInicialLow, bytelongitudHigh, bytelongitudLow};
        
        //GENERA CRC
        CRC crc = new CRC();
        byte[] crcGenerado = crc.generarCRC(tramaSinCRC);
                
        byte byteCRCHigh = crcGenerado[0];
        System.out.println("CRC High en binario: "+Integer.toBinaryString(byteCRCHigh & 0xFF));
        byte byteCRCLow = crcGenerado[1];
        System.out.println("CRC Low en binario: "+Integer.toBinaryString(byteCRCLow & 0xFF));
        
        //ARMA TRAMA CON CRC
        byte[] tramaConCRC = new byte[] { byteIdDispositivo, byteNroFuncion, bytedireccionInicialHigh, bytedireccionInicialLow, bytelongitudHigh, bytelongitudLow, byteCRCHigh, byteCRCLow};
        
        //ENVIA TRAMA
        puerto2 a = new puerto2();
        a.main(tramaConCRC);
         
    }   
}
