package com.example.administracionherramientas.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.administracionherramientas.model_response.CountResponse;
import com.example.administracionherramientas.model_response.HerramientaApiResponse;
import com.example.administracionherramientas.model_response.LoginResponse;
import com.example.administracionherramientas.model_response.PagedResponse;
import com.example.administracionherramientas.models.Cliente;
import com.example.administracionherramientas.models.EstadoDisponibilidad;
import com.example.administracionherramientas.models.Herramienta;
import com.example.administracionherramientas.models.Obra;
import com.example.administracionherramientas.models.Proveedor;
import com.example.administracionherramientas.models.Rol;
import com.example.administracionherramientas.models.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public class ApiClient {
    private static final String BASE_URL="http://147.93.32.147:4000/api/";
    private static ApiClient apiClient;
    private final ApiServicio apiServicio;

    private ApiClient() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiServicio = retrofit.create(ApiServicio.class);
    }

    public static ApiClient getInstance() {
        if (apiClient == null) {
            apiClient = new ApiClient();
        }
        return apiClient;
    }

    public ApiServicio getApiClient() {
        return apiServicio;
    }


    // Clase para el cuerpo de la solicitud de login
    public static class LoginRequest {
        final String legajo;
        final String password;

        public LoginRequest(String legajo, String password) {
            this.legajo = legajo;
            this.password = password;
        }
    }

    public interface ApiServicio {

        //Auth
        @POST("auth/login")
        Call<LoginResponse> loginForm(@Body LoginRequest loginRequest);
        //Alertas
        @GET("Alerta/count-alertas-vencidas")
        Call<CountResponse> getCountAlertasVencidas(@Header("Authorization") String token);
        //Herramientas
        @GET("Herramienta/count-herramientas-disponibles")
        Call<CountResponse> getCountHerramientasDisponibles(@Header("Authorization") String token);
        @GET("Herramienta/count-herramientas-en-prestamo")
        Call<CountResponse> getCountHerramientasPrestamo(@Header("Authorization") String token);
        @GET("Herramienta/count-herramientas-en-reparacion")
        Call<CountResponse> getCountHerramientasReparacion(@Header("Authorization") String token);
        @GET
        Call<HerramientaApiResponse<PagedResponse<Herramienta>>> getHerramientasPaged(@Url String url, @Header("Authorization") String token);
        @GET("Herramienta/disponibilidad") //query params disponibilidad multiple
        Call<List<Herramienta>> getHerramientasByDisponibilidad(@Header("Authorization") String token);

        //EstadoDisponibilidad
        @GET("EstadoDisponibilidad")
        Call<HerramientaApiResponse<List<EstadoDisponibilidad>>> getEstadosDisponibilidad(@Header("Authorization") String token);
        //Rol
        @GET("Rol")
        Call<List<Rol>> getRoles(@Header("Authorization") String token);
        //Proveedor
        @GET("Proveedor")
        Call<List<Proveedor>> getProveedores(@Header("Authorization") String token);
        //Obra
        @GET("Obra")
        Call<List<Obra>> getObras(@Header("Authorization") String token);
        //Usuario
        @GET("Usuario")
        Call<List<Usuario>> getUsuarios(@Header("Authorization") String token);
        //Cliente
        @GET("Cliente")
        Call<List<Cliente>> getClientes(@Header("Authorization") String token);

    }

    public static void guardarToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static String leerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        return sp.getString("token", null);
    }
}
