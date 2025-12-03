package com.example.administracionherramientas.models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EstadoDisponibilidad implements Serializable {
    // possible JSON keys for the id
    @SerializedName("idEstadoDisponibilidad")
    private int idEstadoDisponibilidad;

    @SerializedName("descripcionEstado")
    private String descripcionEstado;

    public EstadoDisponibilidad() {
    }

    public int getIdEstadoDisponibilidad() {
        return idEstadoDisponibilidad;
    }

    public void setIdEstadoDisponibilidad(int idEstadoDisponibilidad) {
        this.idEstadoDisponibilidad = idEstadoDisponibilidad;
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
        return (d != null) ? d : String.valueOf(getIdEstadoDisponibilidad());
    }
}
