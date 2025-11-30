package com.example.administracionherramientas.models;

public class Obra {
    private int IdObra;
    private int idCliente;
    private String Codigo;
    private String NombreObra;
    private String Descripcion;
    private String Ubicacion;
    private String FechaInicio;
    private String FechaFin;
    private boolean Activo;
    private boolean Eliminado;
    public Obra() {
    }
    public Obra(int idObra, int idCliente, String codigo, String nombreObra, String descripcion, String ubicacion, String fechaInicio, String fechaFin, boolean activo, boolean eliminado) {
        IdObra = idObra;
        this.idCliente = idCliente;
        Codigo = codigo;
        NombreObra = nombreObra;
        Descripcion = descripcion;
        Ubicacion = ubicacion;
        FechaInicio = fechaInicio;
        FechaFin = fechaFin;
        Activo = activo;
        Eliminado = eliminado;
    }
    // Getters and Setters

    public int getIdObra() {
        return IdObra;
    }

    public void setIdObra(int idObra) {
        IdObra = idObra;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getNombreObra() {
        return NombreObra;
    }

    public void setNombreObra(String nombreObra) {
        NombreObra = nombreObra;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public String getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        FechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return FechaFin;
    }

    public void setFechaFin(String fechaFin) {
        FechaFin = fechaFin;
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