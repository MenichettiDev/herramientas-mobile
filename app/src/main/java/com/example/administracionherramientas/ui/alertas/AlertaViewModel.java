package com.example.administracionherramientas.ui.alertas;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.administracionherramientas.request.ApiClient;
import com.example.administracionherramientas.models.Alerta;
import com.example.administracionherramientas.model_response.AlertaResponse;
import com.example.administracionherramientas.model_response.SingleAlertaResponse;
import com.example.administracionherramientas.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertaViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Alerta>> alertas = new MutableLiveData<>();
    private final MutableLiveData<Alerta> alertaSeleccionada = new MutableLiveData<>();
    private final Context context;

    public AlertaViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<List<Alerta>> getAlertas() {
        return alertas;
    }

    public LiveData<Alerta> getAlertaSeleccionada() {
        return alertaSeleccionada;
    }


    public void fetchAlertas() {
        String token = ApiClient.leerToken(context);
        ApiClient.getInstance().getApiClient().getAlertas( "Bearer " + token)
                .enqueue(new Callback<AlertaResponse>() {
            @Override
            public void onResponse(Call<AlertaResponse> call, Response<AlertaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    alertas.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<AlertaResponse> call, Throwable t) {
                // Handle failure
            }
        });
    }

    public void fetchAlertaDetalle(int idAlerta) {
        String token = ApiClient.leerToken(context);
        ApiClient.getInstance().getApiClient().getAlertaById("Bearer " + token,idAlerta)
                .enqueue(new Callback<SingleAlertaResponse>() {
            @Override
            public void onResponse(Call<SingleAlertaResponse> call, Response<SingleAlertaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    alertaSeleccionada.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<SingleAlertaResponse> call, Throwable t) {
                // Handle failure
            }
        });
    }
}
