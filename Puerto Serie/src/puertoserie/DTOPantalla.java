
package puertoserie;

import java.util.ArrayList;

public class DTOPantalla {
    
    int idDispositivo;
    int nroFuncion;
    int direccionInicial;
    int cantidadVariables;
    String puerto;
    String trama;
    ArrayList datos = new ArrayList();

    public String getTrama() {
        return trama;
    }

    public void setTrama(String trama) {
        this.trama = trama;
    }

    public ArrayList getDatos() {
        return datos;
    }

    public void setDatos(ArrayList datos) {
        this.datos = datos;
    }

    public int getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(int idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public int getNroFuncion() {
        return nroFuncion;
    }

    public void setNroFuncion(int nroFuncion) {
        this.nroFuncion = nroFuncion;
    }

    public int getDireccionInicial() {
        return direccionInicial;
    }

    public void setDireccionInicial(int direccionInicial) {
        this.direccionInicial = direccionInicial;
    }

    public int getCantidadVariables() {
        return cantidadVariables;
    }

    public void setCantidadVariables(int cantidadVariables) {
        this.cantidadVariables = cantidadVariables;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }
}
