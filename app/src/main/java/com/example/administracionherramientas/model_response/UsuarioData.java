package com.example.administracionherramientas.model_response;

import com.example.administracionherramientas.models.Usuario;

import java.util.List;

public class UsuarioData {
    private List<Usuario> data;

    public List<Usuario> getData() {
        return data;
    }

    public void setData(List<Usuario> data) {
        this.data = data;
    }
}