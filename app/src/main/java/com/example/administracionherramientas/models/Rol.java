package com.example.administracionherramientas.models;

public class Rol {
    private int Id;
    private String NombreRol;
    public Rol() {
    }
    public Rol(int id, String nombreRol) {
        Id = id;
        NombreRol = nombreRol;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombreRol() {
        return NombreRol;
    }

    public void setNombreRol(String nombreRol) {
        NombreRol = nombreRol;
    }
}
