package com.example.administracionherramientas.models;

public class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private String legajo;
    private String dni;
    private String email;
    private String telefono;
    private boolean accedeAlSistema;
    private boolean activo;
    private String avatar;
    private String fechaRegistro;
    private String fechaModificacion;
    private String rolNombre;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isAccedeAlSistema() {
        return accedeAlSistema;
    }

    public void setAccedeAlSistema(boolean accedeAlSistema) {
        this.accedeAlSistema = accedeAlSistema;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (nombre != null && !nombre.equalsIgnoreCase("null")) {
            sb.append(nombre);
        }
        if (apellido != null && !apellido.equalsIgnoreCase("null")) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(apellido);
        }
        if (sb.length() == 0) {
            if (legajo != null) {
                return legajo;
            }
            return ""; // O un valor por defecto si todo es nulo
        }
        return sb.toString().trim();
    }
}
