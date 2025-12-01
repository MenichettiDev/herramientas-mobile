package com.example.administracionherramientas.ui.dashboard;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.administracionherramientas.model_response.CountResponse;
import com.example.administracionherramientas.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardViewModel extends AndroidViewModel {
    private final MutableLiveData<Integer> disponiblesCount = new MutableLiveData<>();
    private final MutableLiveData<Integer> prestamoCount = new MutableLiveData<>();
    private final MutableLiveData<Integer> reparacionCount = new MutableLiveData<>();
    private final MutableLiveData<Integer> totalCount = new MutableLiveData<>();
    private final Context context;

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<Integer> getDisponiblesCount() {
        return disponiblesCount;
    }

    public LiveData<Integer> getPrestamoCount() {
        return prestamoCount;
    }

    public LiveData<Integer> getReparacionCount() {
        return reparacionCount;
    }

    public LiveData<Integer> getTotalCount() {
        return totalCount;
    }

    public void cargarDatos() {
        String token = ApiClient.leerToken(context);
        ApiClient.InmoServicio inmoService = ApiClient.getInmoServicio();

        inmoService.getCountHerramientasDisponibles("Bearer " + token).enqueue(new Callback<CountResponse>() {
            @Override
            public void onResponse(Call<CountResponse> call, Response<CountResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    disponiblesCount.setValue(response.body().getData());
                    actualizarTotal();
                }
            }

            @Override
            public void onFailure(Call<CountResponse> call, Throwable t) {
                Log.e("DashboardViewModel", "Error al obtener herramientas disponibles", t);
            }
        });

        inmoService.getCountHerramientasPrestamo("Bearer " + token).enqueue(new Callback<CountResponse>() {
            @Override
            public void onResponse(Call<CountResponse> call, Response<CountResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    prestamoCount.setValue(response.body().getData());
                    actualizarTotal();
                }
            }

            @Override
            public void onFailure(Call<CountResponse> call, Throwable t) {
                Log.e("DashboardViewModel", "Error al obtener herramientas en préstamo", t);
            }
        });

        inmoService.getCountHerramientasReparacion("Bearer " + token).enqueue(new Callback<CountResponse>() {
            @Override
            public void onResponse(Call<CountResponse> call, Response<CountResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    reparacionCount.setValue(response.body().getData());
                    actualizarTotal();
                }
            }

            @Override
            public void onFailure(Call<CountResponse> call, Throwable t) {
                Log.e("DashboardViewModel", "Error al obtener herramientas en reparación", t);
            }
        });
    }

    private void actualizarTotal() {
        Integer disponibles = disponiblesCount.getValue();
        Integer prestamo = prestamoCount.getValue();
        Integer reparacion = reparacionCount.getValue();

        if (disponibles != null && prestamo != null && reparacion != null) {
            totalCount.setValue(disponibles + prestamo + reparacion);
        }
    }
}
