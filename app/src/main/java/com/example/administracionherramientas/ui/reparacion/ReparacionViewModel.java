package com.example.administracionherramientas.ui.reparacion;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.administracionherramientas.model_response.HerramientaApiResponse;
import com.example.administracionherramientas.model_response.ProveedorResponse;
import com.example.administracionherramientas.model_response.UsuarioResponse;
import com.example.administracionherramientas.models.Cliente;
import com.example.administracionherramientas.models.Herramienta;
import com.example.administracionherramientas.models.Obra;
import com.example.administracionherramientas.models.Proveedor;
import com.example.administracionherramientas.models.Usuario;
import com.example.administracionherramientas.models_request.PrestamoRequest;
import com.example.administracionherramientas.models_request.ReparacionRequest;
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

public class ReparacionViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Proveedor>> proveedores = new MutableLiveData<>();
    private final MutableLiveData<List<Herramienta>> herramientas = new MutableLiveData<>();
    private List<Herramienta> allHerramientas = new ArrayList<>(); // Lista para mantener todas las herramientas
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> navegarDashboard = new MutableLiveData<>();
    private final Context context;
    public ReparacionViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<List<Proveedor>> getProveedores() {
        return proveedores;
    }

    public LiveData<List<Herramienta>> getHerramientas() {
        return herramientas;
    }

    public LiveData<String> getError() {
        return error;
    }
    public LiveData<Boolean> getNavegarDashboard() { return navegarDashboard; }
    public void resetNavegacion() { navegarDashboard.setValue(false); }

    public void fetchProveedores(String query) {
        String token = ApiClient.leerToken(context);
        ApiClient.getInstance().getApiClient().getProveedores("Bearer " + token, query)
                .enqueue(new Callback<ProveedorResponse>() {
                    @Override
                    public void onResponse(Call<ProveedorResponse> call, Response<ProveedorResponse> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                            proveedores.postValue(response.body().getData());
                        } else {
                            error.postValue("Error al obtener los proveedores");
                        }
                    }

                    @Override
                    public void onFailure(Call<ProveedorResponse> call, Throwable t) {
                        Log.e("ReparacionLog", "Error al obtener los proveedores", t);
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


    public void guardarReparacion(int idHerramienta, int idProveedor, long fechaEstimadaMillis) {
        String token = ApiClient.leerToken(context);
        if (token == null) {
            error.postValue("Token no disponible");
            return;
        }

        int idUsuarioGenera = ApiClient.leerIdUsuario(context);
        // Formato
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        String fechaEstimadaDevolucion = sdf.format(new Date(fechaEstimadaMillis));

        ReparacionRequest req = new ReparacionRequest(
                idHerramienta,
                idUsuarioGenera,
                3, // idTipoMovimiento = 3 (Reparacion)
                idProveedor,
                fechaEstimadaDevolucion,
                "Envio a reparacion"
        );

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Log.d("Reparacion", gson.toJson(req));



        ApiClient.getInstance().getApiClient().guardarReparacion("Bearer " + token, req)
                .enqueue(new Callback<HerramientaApiResponse<Object>>() {
                    @Override
                    public void onResponse(Call<HerramientaApiResponse<Object>> call, Response<HerramientaApiResponse<Object>> response) {
                        if (response.isSuccessful()) {
                            error.postValue("Reparacion guardada correctamente");
                            //navegacion a dashboard
                            navegarDashboard.postValue(true);


                        } else {
                            String msg = "Error al guardar Reparacion: " + response.message();
                            error.postValue(msg);
                        }
                    }

                    @Override
                    public void onFailure(Call<HerramientaApiResponse<Object>> call, Throwable t) {
                        error.postValue("Fallo en la conexión al guardar Reparacion: " + t.getMessage());
                    }
                });
    }

}