package com.example.administracionherramientas.models_request;

public class PrestamoRequest {
    public Integer idHerramienta;
    public Integer idUsuarioGenera;
    public Integer idUsuarioResponsable;
    public Integer idTipoMovimiento;
    public Integer idObra;
    public Integer idProveedor; // nullable
    public String fechaEstimadaDevolucion;
    public String estadoHerramientaAlDevolver; // nullable
    public String observaciones;

    public PrestamoRequest(Integer idHerramienta,
                           Integer idUsuarioGenera,
                           Integer idUsuarioResponsable,
                           Integer idTipoMovimiento,
                           Integer idObra,
                           Integer idProveedor,
                           String fechaEstimadaDevolucion,
                           String estadoHerramientaAlDevolver,
                           String observaciones) {
        this.idHerramienta = idHerramienta;
        this.idUsuarioGenera = idUsuarioGenera;
        this.idUsuarioResponsable = idUsuarioResponsable;
        this.idTipoMovimiento = idTipoMovimiento;
        this.idObra = idObra;
        this.idProveedor = idProveedor;
        this.fechaEstimadaDevolucion = fechaEstimadaDevolucion;
        this.estadoHerramientaAlDevolver = estadoHerramientaAlDevolver;
        this.observaciones = observaciones;
    }


}