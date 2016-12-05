package com.moviles.marioandres.proyectotrabajo.modelo;

/**
 * Created by MarioAndres on 3/12/2016.
 */

public class RegistrarPersonal {

    private String nombre;
    private String Apellidos;
    private String tipodocPersonal;
    private String numerodocPersonal;
    private String especializacion;
    private String telefono;
    private String ambulanciapersonal;
    private String estadopersonal;
    private String cargo;

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getTipodocPersonal() {
        return tipodocPersonal;
    }

    public void setTipodocPersonal(String tipodocPersonal) {
        this.tipodocPersonal = tipodocPersonal;
    }

    public String getNumerodocPersonal() {
        return numerodocPersonal;
    }

    public void setNumerodocPersonal(String numerodocPersonal) {
        this.numerodocPersonal = numerodocPersonal;
    }

    public String getEspecializacion() {
        return especializacion;
    }

    public void setEspecializacion(String especializacion) {
        this.especializacion = especializacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAmbulanciapersonal() {
        return ambulanciapersonal;
    }

    public void setAmbulanciapersonal(String ambulanciapersonal) {
        this.ambulanciapersonal = ambulanciapersonal;
    }

    public String getEstadopersonal() {
        return estadopersonal;
    }

    public void setEstadopersonal(String estadopersonal) {
        this.estadopersonal = estadopersonal;
    }
}
