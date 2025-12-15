package com.example.administracionherramientas.ui.logout;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.administracionherramientas.request.ApiClient;

public class LogoutViewModel extends ViewModel {
    private MutableLiveData<Boolean> logoutExitoso = new MutableLiveData<>();

    public LiveData<Boolean> getLogoutExitoso() {
        return logoutExitoso;
    }

    public void logout(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");
        if (token != null && !token.isEmpty()) {
            SharedPreferences.Editor editor = sp.edit();
            editor.remove("token");
            editor.apply();
//            ApiClient.setToken(null);
            logoutExitoso.setValue(true);
            Log.d("Logout", "Token eliminado y sesión cerrada.");
        } else {
            logoutExitoso.setValue(false);
            Log.d("Logout", "No se encontró token para cerrar sesión.");
        }
    }
}
