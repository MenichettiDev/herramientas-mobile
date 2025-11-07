package com.example.administracionherramientas.models;

public class Herramienta {
    private int IdHerramienta;
    private String Codigo;
    private String NombreHerramienta;
    private int IdFamilia;
    private String Tipo;
    private String Marca;
    private String Serie;
    private String FechaDeIngreso;
    private double CostoDolares;
    private String UbicacionFisica;
    private int IdEstadoFisico;
    private int IdPlanta;
    private String ubicacion;
    private boolean Activo;
    private int IdDisponibilidad;
    private int DiasAlerta;

    public Herramienta() {
    }

    public Herramienta(int idHerramienta, String codigo, String nombreHerramienta, int idFamilia, String tipo, String marca, String serie, String fechaDeIngreso, double costoDolares, String ubicacionFisica, int idEstadoFisico, int idPlanta, String ubicacion, boolean activo, int idDisponibilidad, int diasAlerta) {
        IdHerramienta = idHerramienta;
        Codigo = codigo;
        NombreHerramienta = nombreHerramienta;
        IdFamilia = idFamilia;
        Tipo = tipo;
        Marca = marca;
        Serie = serie;
        FechaDeIngreso = fechaDeIngreso;
        CostoDolares = costoDolares;
        UbicacionFisica = ubicacionFisica;
        IdEstadoFisico = idEstadoFisico;
        IdPlanta = idPlanta;
        this.ubicacion = ubicacion;
        Activo = activo;
        IdDisponibilidad = idDisponibilidad;
        DiasAlerta = diasAlerta;
    }

    public int getIdHerramienta() {
        return IdHerramienta;
    }

    public void setIdHerramienta(int idHerramienta) {
        IdHerramienta = idHerramienta;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getNombreHerramienta() {
        return NombreHerramienta;
    }

    public void setNombreHerramienta(String nombreHerramienta) {
        NombreHerramienta = nombreHerramienta;
    }

    public int getIdFamilia() {
        return IdFamilia;
    }

    public void setIdFamilia(int idFamilia) {
        IdFamilia = idFamilia;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getSerie() {
        return Serie;
    }

    public void setSerie(String serie) {
        Serie = serie;
    }

    public String getFechaDeIngreso() {
        return FechaDeIngreso;
    }

    public void setFechaDeIngreso(String fechaDeIngreso) {
        FechaDeIngreso = fechaDeIngreso;
    }

    public double getCostoDolares() {
        return CostoDolares;
    }

    public void setCostoDolares(double costoDolares) {
        CostoDolares = costoDolares;
    }

    public String getUbicacionFisica() {
        return UbicacionFisica;
    }

    public void setUbicacionFisica(String ubicacionFisica) {
        UbicacionFisica = ubicacionFisica;
    }

    public int getIdEstadoFisico() {
        return IdEstadoFisico;
    }

    public void setIdEstadoFisico(int idEstadoFisico) {
        IdEstadoFisico = idEstadoFisico;
    }

    public int getIdPlanta() {
        return IdPlanta;
    }

    public void setIdPlanta(int idPlanta) {
        IdPlanta = idPlanta;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public boolean isActivo() {
        return Activo;
    }

    public void setActivo(boolean activo) {
        Activo = activo;
    }

    public int getIdDisponibilidad() {
        return IdDisponibilidad;
    }

    public void setIdDisponibilidad(int idDisponibilidad) {
        IdDisponibilidad = idDisponibilidad;
    }

    public int getDiasAlerta() {
        return DiasAlerta;
    }

    public void setDiasAlerta(int diasAlerta) {
        DiasAlerta = diasAlerta;
    }
}