package com.example.administracionherramientas.models;

public class Usuario {
    private int Id;
    private String Nombre;
    private String Apellido;
    private String Legajo;
    private String dni;
    private String Email;
    private String Telefono;
    private int RolId;
    private boolean AccedeAlSistema;
    private boolean Activo;
    private String Avatar;
    private String FechaModificacion;
    private String FechaRegistro;
    private int IdUsuarioCrea;
    private int IdUsuarioModifica;
    private String PasswordHash;

    public Usuario(){}

    public Usuario(int id, String nombre, String apellido, String legajo, String dni, String email, String telefono, int rolId, boolean accedeAlSistema, boolean activo, String avatar, String fechaModificacion, String fechaRegistro, int idUsuarioCrea, int idUsuarioModifica, String passwordHash) {
        Id = id;
        Nombre = nombre;
        Apellido = apellido;
        Legajo = legajo;
        this.dni = dni;
        Email = email;
        Telefono = telefono;
        RolId = rolId;
        AccedeAlSistema = accedeAlSistema;
        Activo = activo;
        Avatar = avatar;
        FechaModificacion = fechaModificacion;
        FechaRegistro = fechaRegistro;
        IdUsuarioCrea = idUsuarioCrea;
        IdUsuarioModifica = idUsuarioModifica;
        PasswordHash = passwordHash;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getLegajo() {
        return Legajo;
    }

    public void setLegajo(String legajo) {
        Legajo = legajo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public boolean isAccedeAlSistema() {
        return AccedeAlSistema;
    }

    public void setAccedeAlSistema(boolean accedeAlSistema) {
        AccedeAlSistema = accedeAlSistema;
    }

    public int getRolId() {
        return RolId;
    }

    public void setRolId(int rolId) {
        RolId = rolId;
    }

    public boolean isActivo() {
        return Activo;
    }

    public void setActivo(boolean activo) {
        Activo = activo;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getFechaModificacion() {
        return FechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        FechaModificacion = fechaModificacion;
    }

    public String getFechaRegistro() {
        return FechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        FechaRegistro = fechaRegistro;
    }

    public int getIdUsuarioCrea() {
        return IdUsuarioCrea;
    }

    public void setIdUsuarioCrea(int idUsuarioCrea) {
        IdUsuarioCrea = idUsuarioCrea;
    }

    public int getIdUsuarioModifica() {
        return IdUsuarioModifica;
    }

    public void setIdUsuarioModifica(int idUsuarioModifica) {
        IdUsuarioModifica = idUsuarioModifica;
    }

    public String getPasswordHash() {
        return PasswordHash;
    }

    public void setPasswordHash(String passwordHash) {
        PasswordHash = passwordHash;
    }
}
