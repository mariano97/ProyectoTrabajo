package com.moviles.marioandres.proyectotrabajo.modelo;

/**
 * Created by MarioAndres on 2/12/2016.
 */

public class RegistroCentrosMedicos {

    private String nombrecentro;
    private String direccioncentro;
    private String numeroidentificacion;
    private String numerotelefonico;
    private String niveldelcentro;
    private String estadocentrosmedicos;

    public String getNombrecentro() {
        return nombrecentro;
    }

    public void setNombrecentro(String nombrecentro) {
        this.nombrecentro = nombrecentro;
    }

    public String getDireccioncentro() {
        return direccioncentro;
    }

    public void setDireccioncentro(String direccioncentro) {
        this.direccioncentro = direccioncentro;
    }

    public String getNumeroidentificacion() {
        return numeroidentificacion;
    }

    public void setNumeroidentificacion(String numeroidentificacion) {
        this.numeroidentificacion = numeroidentificacion;
    }

    public String getNumerotelefonico() {
        return numerotelefonico;
    }

    public void setNumerotelefonico(String numerotelefonico) {
        this.numerotelefonico = numerotelefonico;
    }

    public String getNiveldelcentro() {
        return niveldelcentro;
    }

    public void setNiveldelcentro(String niveldelcentro) {
        this.niveldelcentro = niveldelcentro;
    }

    public String getEstadocentrosmedicos() {
        return estadocentrosmedicos;
    }

    public void setEstadocentrosmedicos(String estadocentrosmedicos) {
        this.estadocentrosmedicos = estadocentrosmedicos;
    }
}
