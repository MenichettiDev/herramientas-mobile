package com.example.administracionherramientas.model_response;

import com.example.administracionherramientas.models.Alerta;
import com.google.gson.annotations.SerializedName;

public class SingleAlertaResponse {
    @SerializedName("data")
    private Alerta data;
    private boolean success;
    private String message;

    public Alerta getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
