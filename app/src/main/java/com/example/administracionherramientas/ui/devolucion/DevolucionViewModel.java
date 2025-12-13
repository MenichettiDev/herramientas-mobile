package com.example.administracionherramientas.ui.devolucion;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.administracionherramientas.model_response.HerramientaApiResponse;
import com.example.administracionherramientas.model_response.ProveedorResponse;
import com.example.administracionherramientas.model_response.UsuarioResponse;
import com.example.administracionherramientas.models.Cliente;
import com.example.administracionherramientas.models.EstadoFisicoHerramienta;
import com.example.administracionherramientas.models.Herramienta;
import com.example.administracionherramientas.models.Obra;
import com.example.administracionherramientas.models.Proveedor;
import com.example.administracionherramientas.models.Usuario;
import com.example.administracionherramientas.models_request.DevolucionRequest;
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

public class DevolucionViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Usuario>> usuarios = new MutableLiveData<>();
    private final MutableLiveData<List<Proveedor>> proveedores = new MutableLiveData<>();
    private final MutableLiveData<List<Herramienta>> herramientas = new MutableLiveData<>();
    private final MutableLiveData<List<EstadoFisicoHerramienta>> estadoFisicoHerramientas = new MutableLiveData<>();
    private List<Herramienta> allHerramientas = new ArrayList<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> navegarDashboard = new MutableLiveData<>();
    private final Context context;
    private int idDisponibilidad;
    private final MutableLiveData<Boolean> isUsuarioVisible = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isProveedorVisible = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isHerramientaVisible = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isEstadoFisicoVisible = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isButtonVisible = new MutableLiveData<>();

    public DevolucionViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        // Inicializa las vistas como ocultas
        isUsuarioVisible.setValue(false);
        isProveedorVisible.setValue(false);
        isButtonVisible.setValue(false);
        isHerramientaVisible.setValue(false);
        isEstadoFisicoVisible.setValue(false);
    }

    public LiveData<List<Usuario>> getUsuarios() {
        return usuarios;
    }
    public LiveData<List<Proveedor>> getProveedores() {
        return proveedores;
    }


    public LiveData<List<Herramienta>> getHerramientas() {
        return herramientas;
    }
    public LiveData<List<EstadoFisicoHerramienta>> getEstadoFisicoHerramienta() {
        return estadoFisicoHerramientas;
    }

    public LiveData<String> getError() {
        return error;
    }
    public LiveData<Boolean> getNavegarDashboard() { return navegarDashboard; }
    public void resetNavegacion() { navegarDashboard.setValue(false); }
    public LiveData<Boolean> getIsUsuarioVisible() {return isUsuarioVisible;}

    public LiveData<Boolean> getIsProveedorVisible() {return isProveedorVisible;}
    public LiveData<Boolean> getIsHerramientaVisible() {return isHerramientaVisible;}
    public LiveData<Boolean> getIsEstadoFisicoVisible() {return isEstadoFisicoVisible;}
    public LiveData<Boolean> getIsButtonVisible() {return isButtonVisible;}

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

    public void fetchHerramientasDevolucion(int idUsuario) {

            String token = ApiClient.leerToken(context);
            ApiClient.getInstance().getApiClient().getHerramientasPrestadasByUsuario("Bearer " + token, idUsuario)
                    .enqueue(new Callback<HerramientaApiResponse<List<Herramienta>>>() {
                        @Override
                        public void onResponse(Call<HerramientaApiResponse<List<Herramienta>>> call,
                                               Response<HerramientaApiResponse<List<Herramienta>>> response) {
                            if (response.isSuccessful() &&
                                    response.body() != null &&
                                    response.body().getData() != null) {

                                allHerramientas = response.body().getData();
                                herramientas.postValue(allHerramientas);
                            } else {
                                error.setValue("Error al obtener herramientas: respuesta no exitosa");
                            }
                        }

                        @Override
                        public void onFailure(Call<HerramientaApiResponse<List<Herramienta>>> call, Throwable t) {
                            error.setValue("Fallo en la conexión: " + t.getMessage());
                        }
                    });
    }

    public void fetchHerramientasReparacion(int idProveedor) {

            String token = ApiClient.leerToken(context);
            ApiClient.getInstance().getApiClient().getHerramientasPrestadasByProveedor("Bearer " + token, idProveedor)
                    .enqueue(new Callback<HerramientaApiResponse<List<Herramienta>>>() {
                        @Override
                        public void onResponse(Call<HerramientaApiResponse<List<Herramienta>>> call,
                                               Response<HerramientaApiResponse<List<Herramienta>>> response) {
                            if (response.isSuccessful() &&
                                    response.body() != null &&
                                    response.body().getData() != null) {

                                allHerramientas = response.body().getData();
                                herramientas.postValue(allHerramientas);
                            } else {
                                error.setValue("Error al obtener herramientas: respuesta no exitosa");
                            }
                        }

                        @Override
                        public void onFailure(Call<HerramientaApiResponse<List<Herramienta>>> call, Throwable t) {
                            error.setValue("Fallo en la conexión: " + t.getMessage());
                        }
                    });

    }


    public void fetchEstadoFisicoHerramientas() {
        String token = ApiClient.leerToken(context);
        ApiClient.getInstance().getApiClient().getEstadoFisicoHerramienta("Bearer " + token)
                .enqueue(new Callback<HerramientaApiResponse<List<EstadoFisicoHerramienta>>>() {
                    @Override
                    public void onResponse(Call<HerramientaApiResponse<List<EstadoFisicoHerramienta>>> call, Response<HerramientaApiResponse<List<EstadoFisicoHerramienta>>> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                            estadoFisicoHerramientas.postValue(response.body().getData());
                        } else {
                            error.postValue("Error al obtener los estados de las herramientas: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<HerramientaApiResponse<List<EstadoFisicoHerramienta>>> call, Throwable t) {
                        error.postValue("Fallo en la conexión al obtener estados de las herramientas: " + t.getMessage());
                    }
                });
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

    public void guardarDevolucion(int idHerramienta, Integer idUsuario, Integer idProveedor, int estadoHerramientaAlDevolver) {
        String token = ApiClient.leerToken(context);
        if (token == null) {
            error.postValue("Token no disponible");
            return;
        }
        if( idProveedor == -1){
            idProveedor = null;
        }
        if(idUsuario == -1){
            idUsuario = null;
        }

        int idUsuarioGenera = ApiClient.leerIdUsuario(context);

        DevolucionRequest req = new DevolucionRequest(
                idHerramienta,
                idUsuarioGenera,
                idUsuario,
                2,
                idProveedor,
                estadoHerramientaAlDevolver,
                "Devolucion de herramienta"
        );

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Log.d("Devolucion", gson.toJson(req));



        ApiClient.getInstance().getApiClient().guardarDevolucion("Bearer " + token, req)
                .enqueue(new Callback<HerramientaApiResponse<Object>>() {
                    @Override
                    public void onResponse(Call<HerramientaApiResponse<Object>> call, Response<HerramientaApiResponse<Object>> response) {
                        if (response.isSuccessful()) {
                            error.postValue("Devolucion guardada correctamente");
                            //navegacion a dashboard
                            navegarDashboard.postValue(true);


                        } else {
                            String msg = "Error al guardar Devolucion: " + response.message();
                            error.postValue(msg);
                        }
                    }

                    @Override
                    public void onFailure(Call<HerramientaApiResponse<Object>> call, Throwable t) {
                        error.postValue("Fallo en la conexión al guardar Devolucion: " + t.getMessage());
                    }
                });
    }

    public void onDevolucionPrestamoSelected() {
        isUsuarioVisible.setValue(true);
        idDisponibilidad = 2;
        isProveedorVisible.setValue(false);
        isHerramientaVisible.setValue(false);
        isEstadoFisicoVisible.setValue(false);
        isButtonVisible.setValue(false);
    }

    public void onDevolucionReparacionSelected() {
        isUsuarioVisible.setValue(false);
        isProveedorVisible.setValue(true);
        idDisponibilidad = 3;
        isHerramientaVisible.setValue(false);
        isEstadoFisicoVisible.setValue(false);
        isButtonVisible.setValue(false);
    }

    public void onUsuarioSelected(int usuarioId) {
        isHerramientaVisible.setValue(true);
        isEstadoFisicoVisible.setValue(true);
        isButtonVisible.setValue(true);
        fetchHerramientasDevolucion(usuarioId);
        fetchEstadoFisicoHerramientas();
    }

    public void onProveedorSelected(int proveedorId) {
        isHerramientaVisible.setValue(true);
        isEstadoFisicoVisible.setValue(true);
        isButtonVisible.setValue(true);
        fetchHerramientasReparacion(proveedorId);
        fetchEstadoFisicoHerramientas();
    }


}