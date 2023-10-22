package com.jcjayodev.contactoscontrol.model;

public class Contacto {
    private int id;
    private int cliente_id;
    private String nombre;
    private String telefono;
    private String email;

    public Contacto(int id, int cliente_id, String nombre, String telefono, String email) {
        this.id = id;
        this.cliente_id = cliente_id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Contacto() {
    }
}
