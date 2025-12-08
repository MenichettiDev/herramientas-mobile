package com.example.administracionherramientas.ui.prestamo;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.administracionherramientas.model_response.HerramientaApiResponse;
import com.example.administracionherramientas.models.Herramienta;
import com.example.administracionherramientas.models.Usuario;
import com.example.administracionherramientas.model_response.UsuarioResponse;
import com.example.administracionherramientas.request.ApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrestamoViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Usuario>> usuarios = new MutableLiveData<>();
    private final MutableLiveData<List<Herramienta>> herramientas = new MutableLiveData<>();
    private List<Herramienta> allHerramientas = new ArrayList<>(); // Lista para mantener todas las herramientas
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final Context context;

    public PrestamoViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<List<Usuario>> getUsuarios() {
        return usuarios;
    }

    public LiveData<List<Herramienta>> getHerramientas() {
        return herramientas;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void fetchUsuarios(String query) {
        String token = ApiClient.leerToken(context);
        ApiClient.getInstance().getApiClient().getUsuarios("Bearer " + token, query)
                .enqueue(new Callback<UsuarioResponse>() {
                    @Override
                    public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                            usuarios.postValue(response.body().getData().getData());
                        } else {
                            error.postValue("Error al obtener los usuarios");
                        }
                    }

                    @Override
                    public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                        error.postValue("Fallo en la conexión: " + t.getMessage());
                    }
                });
    }

    public void fetchHerramientas(String query) {

        if (allHerramientas.isEmpty()) {
            String token = ApiClient.leerToken(context);
            ApiClient.getInstance().getApiClient().getHerramientasByDisponibilidad("Bearer " + token, 1, "")   // <-- acá va el ID (ej: 1)
                    .enqueue(new Callback<HerramientaApiResponse<List<Herramienta>>>() {
                        @Override
                        public void onResponse(Call<HerramientaApiResponse<List<Herramienta>>> call,
                                               Response<HerramientaApiResponse<List<Herramienta>>> response) {
                            if (response.isSuccessful() &&
                                    response.body() != null &&
                                    response.body().getData() != null) {

                                allHerramientas = response.body().getData();
                                filterHerramientas(query);

                            } else {
                                error.setValue("Error al obtener herramientas: respuesta no exitosa");
                            }
                        }

                        @Override
                        public void onFailure(Call<HerramientaApiResponse<List<Herramienta>>> call, Throwable t) {
                            error.setValue("Fallo en la conexión: " + t.getMessage());
                        }
                    });

        } else {
            filterHerramientas(query);
        }
    }
    private void filterHerramientas(String query) {

        if (query == null || query.isEmpty()) {
            herramientas.setValue(allHerramientas);
            return;
        }

        String q = query.toLowerCase().trim();

        List<Herramienta> filteredList = allHerramientas.stream()
                .filter(h -> {
                    String codigo = h.getCodigo() == null ? "" : h.getCodigo().toLowerCase().trim();
                    String nombre = h.getNombreHerramienta() == null ? "" : h.getNombreHerramienta().toLowerCase().trim();

                    return codigo.contains(q) || nombre.contains(q);
                })
                .collect(Collectors.toList());

        herramientas.setValue(filteredList);
    }



}