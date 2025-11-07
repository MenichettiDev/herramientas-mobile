package com.example.administracionherramientas.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.administracionherramientas.MainActivity;
import com.example.administracionherramientas.model_response.LoginResponse;
import com.example.administracionherramientas.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<String> errorMutable;
    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context = getApplication();

    }


    public LiveData<String> getErrorMutableLiveData() {
        if (errorMutable == null) {
            errorMutable = new MutableLiveData<>();
        }
        return errorMutable;
    }

    public void login(String legajo, String password){
        if (legajo.isEmpty() || password.isEmpty()) {
            errorMutable.setValue("Todos los campos son obligatorios");
            return;
        }

        //Instancia de inmoServicio
        ApiClient.InmoServicio inmoServicio = ApiClient.getInmoServicio();
        
        ApiClient.LoginRequest loginRequest = new ApiClient.LoginRequest(legajo, password);
        Call<LoginResponse> call = inmoServicio.loginForm(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if( response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();
                    ApiClient.guardarToken(context,token);
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(intent);
            }else{
                    Log.d("Error", response.message());
                    Log.d("Error", response.code()+"");
                    errorMutable.setValue("Usuario o contraseña incorrectos");
                }
                }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                errorMutable.setValue(t.getMessage());
            }
        });

    }
}
