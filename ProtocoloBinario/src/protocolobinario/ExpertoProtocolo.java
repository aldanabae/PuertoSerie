
package protocolobinario;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExpertoProtocolo {
    
    public String armarTrama(DTOprotocolo dto){
       
        String trama = convertirBinario(dto.getIdDispositivo(), 9) +
                " - " + convertirBinario(dto.getCodFuncion(), 5) +
                " - " + convertirBinario(dto.getDireccionInicio(), 17) +
                " - " + convertirBinario(dto.getCantidadVariables(), 9);
                
        return trama;        
    
    }
    
    public String armarTrama2(DTOprotocolo dto){
        
        Date fechaHora = dto.getFechaHora();
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        int dia = Integer.parseInt(sdf.format(fechaHora));
        sdf = new SimpleDateFormat("MM");
        int mes = Integer.parseInt(sdf.format(fechaHora));
        sdf = new SimpleDateFormat("yyyy");
        int anio = Integer.parseInt(sdf.format(fechaHora));
        sdf = new SimpleDateFormat("HH");
        int hora = Integer.parseInt(sdf.format(fechaHora));
        sdf = new SimpleDateFormat("mm");
        int min = Integer.parseInt(sdf.format(fechaHora));
        System.out.println(dia+" "+mes+" "+anio+" "+hora+" "+min);
            
        String trama = convertirBinario(dto.getIdDispositivo(), 10) +
                " - " + convertirBinario(dia, 5) +
                convertirBinario(mes, 4) +
                convertirBinario(anio, 11) +
                convertirBinario(hora, 4) +
                convertirBinario(min, 6) +
                " - " + convertirBinario(dto.getTemperatura(), 6) +
                " - " + convertirBinario(dto.getCorriente(), 4) +
                " - " + convertirBinario(dto.getTension(), 8);
                
        return trama;
    
    }
    
    public String convertirBinario(int nro, int tamano){
        
        String nroBinario = Integer.toBinaryString(nro);
        
        int cantidadCeros = tamano - nroBinario.length();
        
        if(nroBinario.length() < tamano){
            for (int i = 0; i < cantidadCeros; i++) {
                nroBinario = "0" + nroBinario;
            }
        }
        return nroBinario;
    
    }
    
   
    
}
