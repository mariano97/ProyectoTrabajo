package com.moviles.marioandres.proyectotrabajo.modelo;

/**
 * Created by MarioAndres on 5/12/2016.
 */

public class RegistrarlosIncidentes {

    private String consecutivo;
    private String direccionincidente;
    private String vehiculoasignado;
    private String centromedicoasignado;
    private String diagnosticoincidentes;
    private String procedimeintoincidente;

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getDireccionincidente() {
        return direccionincidente;
    }

    public void setDireccionincidente(String direccionincidente) {
        this.direccionincidente = direccionincidente;
    }

    public String getVehiculoasignado() {
        return vehiculoasignado;
    }

    public void setVehiculoasignado(String vehiculoasignado) {
        this.vehiculoasignado = vehiculoasignado;
    }

    public String getCentromedicoasignado() {
        return centromedicoasignado;
    }

    public void setCentromedicoasignado(String centromedicoasignado) {
        this.centromedicoasignado = centromedicoasignado;
    }

    public String getDiagnosticoincidentes() {
        return diagnosticoincidentes;
    }

    public void setDiagnosticoincidentes(String diagnosticoincidentes) {
        this.diagnosticoincidentes = diagnosticoincidentes;
    }

    public String getProcedimeintoincidente() {
        return procedimeintoincidente;
    }

    public void setProcedimeintoincidente(String procedimeintoincidente) {
        this.procedimeintoincidente = procedimeintoincidente;
    }
}
