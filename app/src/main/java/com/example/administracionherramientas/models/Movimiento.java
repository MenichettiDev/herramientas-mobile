package com.example.administracionherramientas.models;

import java.util.Date;

public class Movimiento {
    private int idMovimiento;
    private int idHerramienta;
    private int idUsuarioGenera;
    private Integer idUsuarioResponsable;
    private int idTipoMovimiento;
    private Date fecha;
    private Integer idObra;
    private Integer idProveedor;
    private Date fechaEstimadaDevolucion;
    private int estadoHerramientaAlDevolver;
    private String observaciones;
    private String codigoHerramienta;
    private String nombreHerramienta;
    private String nombreUsuarioGenera;
    private String nombreUsuarioResponsable;
    private String tipoMovimiento;
    private String nombreObra;
    private String estadoDevolucion;
    private String nombreProveedor;

    // Getters and setters for all fields

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getIdHerramienta() {
        return idHerramienta;
    }

    public void setIdHerramienta(int idHerramienta) {
        this.idHerramienta = idHerramienta;
    }

    public int getIdUsuarioGenera() {
        return idUsuarioGenera;
    }

    public void setIdUsuarioGenera(int idUsuarioGenera) {
        this.idUsuarioGenera = idUsuarioGenera;
    }

    public Integer getIdUsuarioResponsable() {
        return idUsuarioResponsable;
    }

    public void setIdUsuarioResponsable(Integer idUsuarioResponsable) {
        this.idUsuarioResponsable = idUsuarioResponsable;
    }

    public int getIdTipoMovimiento() {
        return idTipoMovimiento;
    }

    public void setIdTipoMovimiento(int idTipoMovimiento) {
        this.idTipoMovimiento = idTipoMovimiento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getIdObra() {
        return idObra;
    }

    public void setIdObra(Integer idObra) {
        this.idObra = idObra;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Date getFechaEstimadaDevolucion() {
        return fechaEstimadaDevolucion;
    }

    public void setFechaEstimadaDevolucion(Date fechaEstimadaDevolucion) {
        this.fechaEstimadaDevolucion = fechaEstimadaDevolucion;
    }

    public int getEstadoHerramientaAlDevolver() {
        return estadoHerramientaAlDevolver;
    }

    public void setEstadoHerramientaAlDevolver(int estadoHerramientaAlDevolver) {
        this.estadoHerramientaAlDevolver = estadoHerramientaAlDevolver;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getCodigoHerramienta() {
        return codigoHerramienta;
    }

    public void setCodigoHerramienta(String codigoHerramienta) {
        this.codigoHerramienta = codigoHerramienta;
    }

    public String getNombreHerramienta() {
        return nombreHerramienta;
    }

    public void setNombreHerramienta(String nombreHerramienta) {
        this.nombreHerramienta = nombreHerramienta;
    }

    public String getNombreUsuarioGenera() {
        return nombreUsuarioGenera;
    }

    public void setNombreUsuarioGenera(String nombreUsuarioGenera) {
        this.nombreUsuarioGenera = nombreUsuarioGenera;
    }

    public String getNombreUsuarioResponsable() {
        return nombreUsuarioResponsable;
    }

    public void setNombreUsuarioResponsable(String nombreUsuarioResponsable) {
        this.nombreUsuarioResponsable = nombreUsuarioResponsable;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getNombreObra() {
        return nombreObra;
    }

    public void setNombreObra(String nombreObra) {
        this.nombreObra = nombreObra;
    }

    public String getEstadoDevolucion() {
        return estadoDevolucion;
    }

    public void setEstadoDevolucion(String estadoDevolucion) {
        this.estadoDevolucion = estadoDevolucion;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }
}
