package com.example.administracionherramientas.models;

public class Cliente {
    private int IdCliente;
    private String Cuit;
    private String Nombre;
    private String Telefono;
    private String Email;
    private String Direccion;
    private String FechaRegistro;
    private boolean Activo;
    private boolean Eliminado;

    public Cliente() {
    }
    public Cliente(int idCliente, String cuit, String nombre, String telefono, String email, String direccion, String fechaRegistro, boolean activo, boolean eliminado) {
        IdCliente = idCliente;
        Cuit = cuit;
        Nombre = nombre;
        Telefono = telefono;
        Email = email;
        Direccion = direccion;
        FechaRegistro = fechaRegistro;
        Activo = activo;
        Eliminado = eliminado;
    }

    public int getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(int idCliente) {
        IdCliente = idCliente;
    }

    public String getCuit() {
        return Cuit;
    }

    public void setCuit(String cuit) {
        Cuit = cuit;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getFechaRegistro() {
        return FechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        FechaRegistro = fechaRegistro;
    }

    public boolean isActivo() {
        return Activo;
    }

    public void setActivo(boolean activo) {
        Activo = activo;
    }

    public boolean isEliminado() {
        return Eliminado;
    }

    public void setEliminado(boolean eliminado) {
        Eliminado = eliminado;
    }
}