package com.example.administracionherramientas.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.administracionherramientas.model_response.LoginResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class ApiClient {
    public final static String BASE_URL="http://147.93.32.147:4000/";



    public static InmoServicio getInmoServicio(){
        Gson gson = new GsonBuilder().setLenient().create();
         Retrofit retrofit =new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(InmoServicio.class);
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

    public interface InmoServicio{

        @POST("api/auth/login")
        Call<LoginResponse> loginForm(@Body LoginRequest loginRequest);
//
//        @GET("api/Propietarios")
//        Call<Propietario> getPropietario(@Header("Authorization") String token);
//
//        @PUT("api/Propietarios/actualizar")
//        Call<Propietario> editPropietario(@Header("Authorization") String token,
//                                          @Body Propietario propietario);
//
//        @GET("api/Inmuebles")
//        Call<List<Inmueble>> getInmueble(@Header("Authorization") String token);
//
//        @GET("api/Inmuebles/GetContratoVigente")
//        Call<List<Inmueble>> getInmuebleContratoVigente(@Header("Authorization") String token);
//
//        @GET("api/contratos/inmueble/{id}")
//        Call<Contrato> getContratoByInmueble(@Header("Authorization") String token,
//                                             @Path("id") int idInmueble);
//
//        @PUT("api/Inmuebles/actualizar")
//        Call<Inmueble> updateInmueble(@Header("Authorization") String token,
//                                      @Body Inmueble inmueble);
//
//        @Multipart
//        @POST("api/Inmuebles/cargar")
//        Call<Inmueble> CargarInmueble(@Header("Authorization") String token,
//                                      @Part MultipartBody.Part imagen,
//                                      @Part("inmueble") RequestBody inmuebleBody);
//
//        @GET("api/pagos/contrato/{id}")
//        Call<List<Pago>> getPagosByContrato(@Header("Authorization") String token, @retrofit2.http.Path("id") int idContrato);


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
