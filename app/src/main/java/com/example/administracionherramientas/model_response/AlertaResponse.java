package com.example.administracionherramientas.model_response;

import com.example.administracionherramientas.models.Alerta;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AlertaResponse {
    @SerializedName("data")
    private List<Alerta> data;
    private boolean success;
    private String message;

    public List<Alerta> getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
