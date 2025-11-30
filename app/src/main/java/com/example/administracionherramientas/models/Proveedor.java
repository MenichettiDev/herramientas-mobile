package com.example.administracionherramientas.models;

public class Proveedor {
    private int IdProveedor;
    private String NombreProveedor;
    private String Cuit;
    private String Contacto;
    private String Telefono;
    private String Email;
    private String Direccion;
    private String Descripcion;
    private String proveedorcol;
    private boolean Activo;
    private boolean Eliminado;

    public Proveedor() {
    }
    public Proveedor(int idProveedor, String nombreProveedor, String cuit, String contacto, String telefono, String email, String direccion, String descripcion, String proveedorcol, boolean activo, boolean eliminado) {
        IdProveedor = idProveedor;
        NombreProveedor = nombreProveedor;
        Cuit = cuit;
        Contacto = contacto;
        Telefono = telefono;
        Email = email;
        Direccion = direccion;
        Descripcion = descripcion;
        this.proveedorcol = proveedorcol;
        Activo = activo;
        Eliminado = eliminado;
    }

    // Getters and Setters
    public int getIdProveedor() {
        return IdProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        IdProveedor = idProveedor;
    }

    public String getNombreProveedor() {
        return NombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        NombreProveedor = nombreProveedor;
    }

    public String getCuit() {
        return Cuit;
    }

    public void setCuit(String cuit) {
        Cuit = cuit;
    }

    public String getContacto() {
        return Contacto;
    }

    public void setContacto(String contacto) {
        Contacto = contacto;
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

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getProveedorcol() {
        return proveedorcol;
    }

    public void setProveedorcol(String proveedorcol) {
        this.proveedorcol = proveedorcol;
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