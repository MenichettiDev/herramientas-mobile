package com.example.administracionherramientas.ui.prestamo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.example.administracionherramientas.R;
import com.example.administracionherramientas.model_response.HerramientaApiResponse;
import com.example.administracionherramientas.models.Cliente;
import com.example.administracionherramientas.models.Herramienta;
import com.example.administracionherramientas.models.Obra;
import com.example.administracionherramientas.models.Usuario;
import com.example.administracionherramientas.model_response.UsuarioResponse;
import com.example.administracionherramientas.models_request.PrestamoRequest;
import com.example.administracionherramientas.request.ApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrestamoViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Usuario>> usuarios = new MutableLiveData<>();
    private final MutableLiveData<List<Herramienta>> herramientas = new MutableLiveData<>();
    private List<Herramienta> allHerramientas = new ArrayList<>(); // Lista para mantener todas las herramientas
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<List<Cliente>> clientes = new MutableLiveData<>();
    private final MutableLiveData<List<Obra>> obras = new MutableLiveData<>();
    private MutableLiveData<Boolean> navegarDashboard = new MutableLiveData<>();
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

    public LiveData<List<Cliente>> getClientes() {
        return clientes;
    }

    public LiveData<List<Obra>> getObras() {
        return obras;
    }
    public LiveData<Boolean> getNavegarDashboard() { return navegarDashboard; }
    public void resetNavegacion() { navegarDashboard.setValue(false); }

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

    public void fetchClientes(String query) {
        String token = ApiClient.leerToken(context);
        ApiClient.getInstance().getApiClient().getClientes("Bearer " + token, query)
                .enqueue(new Callback<HerramientaApiResponse<List<Cliente>>>() {
                    @Override
                    public void onResponse(Call<HerramientaApiResponse<List<Cliente>>> call, Response<HerramientaApiResponse<List<Cliente>>> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                            clientes.postValue(response.body().getData());
                        } else {
                            error.postValue("Error al obtener los clientes: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<HerramientaApiResponse<List<Cliente>>> call, Throwable t) {
                        error.postValue("Fallo en la conexión al obtener clientes: " + t.getMessage());
                    }
                });
    }

    public void fetchObras(int idCliente, String query) {
        String token = ApiClient.leerToken(context);
        ApiClient.getInstance().getApiClient().getObras("Bearer " + token, idCliente, query)
                .enqueue(new Callback<HerramientaApiResponse<List<Obra>>>() {
                    @Override
                    public void onResponse(Call<HerramientaApiResponse<List<Obra>>> call, Response<HerramientaApiResponse<List<Obra>>> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                            obras.postValue(response.body().getData());
                        } else {
                            error.postValue("Error al obtener las obras: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<HerramientaApiResponse<List<Obra>>> call, Throwable t) {
                        error.postValue("Fallo en la conexión al obtener obras: " + t.getMessage());
                    }
                });
    }

    public void clearObras() {
        obras.postValue(new ArrayList<>());
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


    public void guardarPrestamo(int idHerramienta, int idUsuarioResponsable, int idObra, long fechaEstimadaMillis) {
        String token = ApiClient.leerToken(context);
        if (token == null) {
            error.postValue("Token no disponible");
            return;
        }

        int idUsuarioGenera = ApiClient.leerIdUsuario(context);
        //Formato
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        String fechaEstimadaDevolucion = sdf.format(new Date(fechaEstimadaMillis));

        PrestamoRequest req = new PrestamoRequest(
                idHerramienta,
                idUsuarioGenera,
                idUsuarioResponsable,
                1, // idTipoMovimiento = 1
                idObra,
                null, // idProveedor = null
                fechaEstimadaDevolucion,
                null, // estadoHerramientaAlDevolver = null
                "Prestamo de herramienta"
        );

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Log.d("Prestamo", gson.toJson(req));



        ApiClient.getInstance().getApiClient().guardarPrestamo("Bearer " + token, req)
                .enqueue(new Callback<HerramientaApiResponse<Object>>() {
                    @Override
                    public void onResponse(Call<HerramientaApiResponse<Object>> call, Response<HerramientaApiResponse<Object>> response) {
                        if (response.isSuccessful()) {
                            error.postValue("Prestamo guardado correctamente");
                            //navegacion a dashboard
                            navegarDashboard.postValue(true);


                        } else {
                            String msg = "Error al guardar prestamo: " + response.message();
                            error.postValue(msg);
                        }
                    }

                    @Override
                    public void onFailure(Call<HerramientaApiResponse<Object>> call, Throwable t) {
                        error.postValue("Fallo en la conexión al guardar prestamo: " + t.getMessage());
                    }
                });
    }


}
