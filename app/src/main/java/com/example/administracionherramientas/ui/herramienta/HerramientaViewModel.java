package com.example.administracionherramientas.ui.herramienta;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.administracionherramientas.model_response.HerramientaApiResponse;
import com.example.administracionherramientas.models.EstadoDisponibilidad;
import com.example.administracionherramientas.models.Herramienta;
import com.example.administracionherramientas.model_response.PagedResponse;
import com.example.administracionherramientas.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HerramientaViewModel extends AndroidViewModel {

    private static final String TAG = "hlog";
    private final MutableLiveData<List<EstadoDisponibilidad>> estadosDisponibilidad = new MutableLiveData<>();
    private final MutableLiveData<PagedResponse<Herramienta>> pagedResponse = new MutableLiveData<>();
    private final MutableLiveData<List<Herramienta>> herramientas = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final Context context;

    private int currentPage = 1;
    private static final int PAGE_SIZE = 10;

    public HerramientaViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<List<EstadoDisponibilidad>> getEstadosDisponibilidad() {
        return estadosDisponibilidad;
    }

    public LiveData<PagedResponse<Herramienta>> getPagedResponse() {
        return pagedResponse;
    }

    public LiveData<List<Herramienta>> getHerramientas() {
        return herramientas;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }


    public void cargarEstadosDisponibilidad() {
        String token = ApiClient.leerToken(context);
        isLoading.setValue(true);
        ApiClient.getInstance().getApiClient().getEstadosDisponibilidad("Bearer " + token)
                .enqueue(new Callback<HerramientaApiResponse<List<EstadoDisponibilidad>>>() {
                    @Override
                    public void onResponse(Call<HerramientaApiResponse<List<EstadoDisponibilidad>>> call, Response<HerramientaApiResponse<List<EstadoDisponibilidad>>> response) {
                        isLoading.setValue(false);
                        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                            estadosDisponibilidad.setValue(response.body().getData());
                        } else {
                            errorMessage.setValue("Error al obtener los estados de disponibilidad");
                            Log.e(TAG, "EstadoDisponibilidad error: code=" + response.code() + " body=" + response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<HerramientaApiResponse<List<EstadoDisponibilidad>>> call, Throwable t) {
                        isLoading.setValue(false);
                        errorMessage.setValue(t.getMessage());
                        Log.e(TAG, "Error cargarEstadosDisponibilidad", t);
                    }
                });
    }

    public void buscarHerramientas(String codigo, String nombre, String marca, Integer idEstado) {
        currentPage = 1;
        herramientas.setValue(new ArrayList<>()); // Limpiar lista para nueva búsqueda
        cargarHerramientas(codigo, nombre, marca, idEstado);
    }

    public void cargarMasHerramientas(String codigo, String nombre, String marca, Integer idEstado) {
        currentPage++;
        cargarHerramientas(codigo, nombre, marca, idEstado);
    }

    private void cargarHerramientas(String codigo, String nombre, String marca, Integer idEstado) {
        Log.d(TAG, "Cargando herramientas: codigo=" + codigo + ", nombre=" + nombre + ", marca=" + marca + ", idEstado=" + idEstado + ", pagina=" + currentPage);
        String token = ApiClient.leerToken(context);
        isLoading.setValue(true);
        String url = "Herramienta/paged?page=" + currentPage + "&pageSize=" + PAGE_SIZE;
        if (codigo != null && !codigo.isEmpty()) {
            url += "&codigo=" + codigo;
        }
        if (nombre != null && !nombre.isEmpty()) {
            url += "&nombre=" + nombre;
        }
        if (marca != null && !marca.isEmpty()) {
            url += "&marca=" + marca;
        }
        if (idEstado != null) {
            url += "&idDisponibilidad=" + idEstado;
        }

        Log.d(TAG, "URL: " + url);

        ApiClient.getInstance().getApiClient().getHerramientasPaged(url,"Bearer " + token ).enqueue(new Callback<HerramientaApiResponse<PagedResponse<Herramienta>>>() {
            @Override
            public void onResponse(Call<HerramientaApiResponse<PagedResponse<Herramienta>>> call, Response<HerramientaApiResponse<PagedResponse<Herramienta>>> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Log.d(TAG, "Respuesta de API exitosa.");
                    PagedResponse<Herramienta> data = response.body().getData();
                    pagedResponse.setValue(data); // Actualizar estado de paginación

                    if (data != null && data.getData() != null) {
                        List<Herramienta> currentList = (currentPage == 1) ? new ArrayList<>() : herramientas.getValue();
                        if (currentList == null) {
                            currentList = new ArrayList<>();
                        }
                        currentList.addAll(data.getData());
                        herramientas.setValue(currentList);
                        Log.d(TAG, "Herramientas actualizadas en LiveData. Total: " + currentList.size());
                    } else {
                        Log.w(TAG, "La respuesta no contiene datos de herramientas.");
                    }
                } else {
                    errorMessage.setValue("Error al obtener las herramientas");
                    Log.e(TAG, "Error en onResponse: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<HerramientaApiResponse<PagedResponse<Herramienta>>> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue(t.getMessage());
                Log.e(TAG, "Error en onFailure: ", t);
            }
        });
    }
}
