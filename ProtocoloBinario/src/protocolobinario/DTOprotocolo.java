
package protocolobinario;

import java.util.Date;

public class DTOprotocolo {
    
    int idDispositivo;
    int codFuncion;
    int direccionInicio;
    int cantidadVariables;
    
    Date fechaHora;
    int temperatura;
    int corriente;
    int tension;

    public int getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(int idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public int getCodFuncion() {
        return codFuncion;
    }

    public void setCodFuncion(int codFuncion) {
        this.codFuncion = codFuncion;
    }

    public int getDireccionInicio() {
        return direccionInicio;
    }

    public void setDireccionInicio(int direccionInicio) {
        this.direccionInicio = direccionInicio;
    }

    public int getCantidadVariables() {
        return cantidadVariables;
    }

    public void setCantidadVariables(int cantidadVariables) {
        this.cantidadVariables = cantidadVariables;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public int getCorriente() {
        return corriente;
    }

    public void setCorriente(int corriente) {
        this.corriente = corriente;
    }

    public int getTension() {
        return tension;
    }

    public void setTension(int tension) {
        this.tension = tension;
    }
    
    
    
}
