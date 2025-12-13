package com.example.administracionherramientas.models;

import com.google.gson.annotations.SerializedName;

public class EstadoFisicoHerramienta {
    @SerializedName("idEstadoFisico")
    private int idEstadoFisico;

    @SerializedName("descripcionEstado")
    private String descripcionEstado;

    public EstadoFisicoHerramienta() {
    }

    public int getIdEstadoFisico() {
        return idEstadoFisico;
    }

    public void setIdEstadoFisico(int idEstadoFisico) {
        this.idEstadoFisico = idEstadoFisico;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    @Override
    public String toString() {
        String d = getDescripcionEstado();
        return (d != null) ? d : String.valueOf(getIdEstadoFisico());
    }
}
