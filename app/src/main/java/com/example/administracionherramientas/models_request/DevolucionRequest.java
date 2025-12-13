package com.example.administracionherramientas.models_request;

public class DevolucionRequest {
    public Integer idHerramienta;
    public Integer idUsuarioGenera;
    public Integer idUsuarioResponsable;
    public Integer idTipoMovimiento;
    public Integer idProveedor;
    public Integer estadoHerramientaAlDevolver;
    public String observaciones;

    public DevolucionRequest(Integer idHerramienta,
                           Integer idUsuarioGenera,
                           Integer idUsuarioResponsable,
                           Integer idTipoMovimiento,
                           Integer idProveedor,
                             Integer estadoHerramientaAlDevolver,
                           String observaciones) {
        this.idHerramienta = idHerramienta;
        this.idUsuarioGenera = idUsuarioGenera;
        this.idUsuarioResponsable = idUsuarioResponsable;
        this.idTipoMovimiento = idTipoMovimiento;
        this.idProveedor = idProveedor;
        this.estadoHerramientaAlDevolver = estadoHerramientaAlDevolver;
        this.observaciones = observaciones;
    }
}
