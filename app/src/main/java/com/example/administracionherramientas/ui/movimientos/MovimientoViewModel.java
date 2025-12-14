package com.example.administracionherramientas.ui.movimientos;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.administracionherramientas.model_response.HerramientaApiResponse;
import com.example.administracionherramientas.model_response.PagedResponse;
import com.example.administracionherramientas.models.Movimiento;
import com.example.administracionherramientas.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovimientoViewModel extends AndroidViewModel {
    private final Context context;
    private final MutableLiveData<PagedResponse<Movimiento>> movimientos;

    public MovimientoViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
        this.movimientos = new MutableLiveData<>();
    }

    public LiveData<PagedResponse<Movimiento>> getMovimientos() {
        return movimientos;
    }

    public void obtenerMovimientos(String nombre, Integer idTipoMovimiento) {
        String token = ApiClient.leerToken(context);

        ApiClient.getInstance().getApiClient().getMovimientos("Bearer " + token, nombre, idTipoMovimiento)

                .enqueue(new Callback<HerramientaApiResponse<PagedResponse<Movimiento>>>() {
                    @Override
                    public void onResponse(Call<HerramientaApiResponse<PagedResponse<Movimiento>>> call, Response<HerramientaApiResponse<PagedResponse<Movimiento>>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d("salida", response.raw().toString());
                            movimientos.postValue(response.body().getData());
                        } else {
                            Log.d("salida", response.raw().message());
                        }
                    }

                    @Override
                    public void onFailure(Call<HerramientaApiResponse<PagedResponse<Movimiento>>> call, Throwable t) {
                        Log.d("salida", t.getMessage());
                    }
                });
    }
}
