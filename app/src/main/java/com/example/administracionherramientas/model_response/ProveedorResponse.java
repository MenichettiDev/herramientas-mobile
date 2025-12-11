package com.example.administracionherramientas.model_response;

import com.example.administracionherramientas.models.Proveedor;

import java.util.List;

public class ProveedorResponse {
    private List<Proveedor> data;
    private boolean success;
    private String message;
    private List<String> errors;

    public List<Proveedor> getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}
