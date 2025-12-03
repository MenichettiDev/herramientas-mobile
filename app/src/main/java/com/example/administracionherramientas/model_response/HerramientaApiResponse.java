package com.example.administracionherramientas.model_response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HerramientaApiResponse<T> {

    @SerializedName("data")
    private T data; // T será PagedResponse<Herramienta>

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("errors")
    private List<String> errors;

    // Getters and Setters
    public T getData() {
        return data;
    }

    public void setData(T data) {
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
