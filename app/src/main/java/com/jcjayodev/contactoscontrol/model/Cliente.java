package com.jcjayodev.contactoscontrol.model;

public class Cliente {
    private int id;
    private String nombre;
    private String domicilio;
    private String codigo_postal;
    private String poblacion;

    public Cliente(int id, String nombre, String domicilio, String codigo_postal, String poblacion) {
        this.id = id;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.codigo_postal = codigo_postal;
        this.poblacion = poblacion;
    }

    public Cliente() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }
}
