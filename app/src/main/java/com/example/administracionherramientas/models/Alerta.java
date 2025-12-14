package com.example.administracionherramientas.models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class Alerta {
    @SerializedName("idAlerta")
    private int idAlerta;
    @SerializedName("idMovimiento")
    private int idMovimiento;
    @SerializedName("nombreHerramienta")
    private String nombreHerramienta;
    @SerializedName("idTipoAlerta")
    private int idTipoAlerta;
    @SerializedName("nombreTipoAlerta")
    private String nombreTipoAlerta;
    @SerializedName("fechaGeneracion")
    private Date fechaGeneracion;
    @SerializedName("fechaVencimiento")
    private Date fechaVencimiento;
    @SerializedName("comentario")
    private String comentario;
    @SerializedName("idModifica")
    private int idModifica;
    @SerializedName("activo")
    private boolean activo;
    @SerializedName("herramientaNombre")
    private String herramientaNombre;
    @SerializedName("herramientaCodigo")
    private String herramientaCodigo;
    @SerializedName("responsableNombre")
    private String responsableNombre;
    @SerializedName("tipoMovimiento")
    private String tipoMovimiento;
    @SerializedName("usuarioModificaNombre")
    private String usuarioModificaNombre;

    // Getters
    public int getIdAlerta() {
        return idAlerta;
    }

    public String getHerramientaNombre() {
        return herramientaNombre;
    }

    public String getHerramientaCodigo() {
        return herramientaCodigo;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public String getResponsableNombre() {
        return responsableNombre;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public String getNombreTipoAlerta() {
        return nombreTipoAlerta;
    }
}
