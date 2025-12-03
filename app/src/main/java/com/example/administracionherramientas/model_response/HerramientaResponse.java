package com.example.administracionherramientas.model_response;

import java.util.List;

public class HerramientaResponse {
    private HerramientaData data;
    private boolean success;
    private String message;
    private List<String> errors;
    public HerramientaResponse() {
    }
    public HerramientaResponse(HerramientaData data, boolean success, String message, List<String> errors) {
        this.data = data;
        this.success = success;
        this.message = message;
        this.errors = errors;
    }

    public HerramientaData getData() {
        return data;
    }

    public void setData(HerramientaData data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
