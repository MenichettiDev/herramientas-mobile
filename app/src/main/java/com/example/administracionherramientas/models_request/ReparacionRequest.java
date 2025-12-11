package com.example.administracionherramientas.models_request;

public class ReparacionRequest {
    public Integer idHerramienta;
    public Integer idUsuarioGenera;
    public Integer idTipoMovimiento;
    public Integer idProveedor;
    public String fechaEstimadaDevolucion;
    public String observaciones;

    public ReparacionRequest(Integer idHerramienta,
                           Integer idUsuarioGenera,
                           Integer idTipoMovimiento,
                           Integer idProveedor,
                           String fechaEstimadaDevolucion,
                           String observaciones) {
        this.idHerramienta = idHerramienta;
        this.idUsuarioGenera = idUsuarioGenera;
        this.idTipoMovimiento = idTipoMovimiento;
        this.idProveedor = idProveedor;
        this.fechaEstimadaDevolucion = fechaEstimadaDevolucion;
        this.observaciones = observaciones;
    }
}
